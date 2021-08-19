package ptithcm.controller;


import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ptithcm.entity.*;
import ptithcm.service.ProvinceService;

@Transactional
@Controller
public class AccountController {
	@Autowired
	SessionFactory factory;

	@Autowired
	JavaMailSender mailer;
	
	@Autowired
	ServletContext context;

	List<Object> getList(String hql) {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery(hql);
		List<Object> list = query.list();
		return list;
	}
	
	public static boolean isContainSpecialWord(String str) {
		Pattern VALID_INPUT_REGEX = Pattern.compile("[$&+,:;=\\\\\\\\?@#|/'<>.^*()%!-]",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_INPUT_REGEX.matcher(str);
		return matcher.find();
	}
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(ModelMap model, HttpSession session) {
		model.addAttribute("account", new Account());
		if (session.getAttribute("provinces")==null) {
			session.setAttribute("provinces", ProvinceService.findAll(factory));
		}
		return "login";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(ModelMap model, 
			@ModelAttribute("account") Account user, 
			BindingResult errors,
			HttpSession httpSession) {
		user.setUsername(user.getUsername().trim());
		user.setPassword(user.getPassword().trim());
		if (user.getUsername().isEmpty()) {
			errors.rejectValue("username", "account", 
					"Please enter your username !");
		} else if (user.getUsername().contains(" ")) {
			errors.rejectValue("username", "account", 
					"Username must not contain space !");
		} else if (isContainSpecialWord(user.getUsername())) {
			errors.rejectValue("username", "account", 
					"Username must not contain speacial character !");
		}
		if (user.getPassword().isEmpty()) {
			errors.rejectValue("password", "account", 
					"Please enter your password !");
		} else if (user.getPassword().contains(" ")) {
			errors.rejectValue("password", "account", 
					"Password must not contain space !");
		} else if (isContainSpecialWord(user.getPassword())) {
			errors.rejectValue("password", "account", 
					"Password must not contain speacial character !");
		}
		if (!errors.hasErrors()) {
			Session session = factory.getCurrentSession();
			Account account = (Account) session.get(Account.class, user.getUsername());
			if (account!=null) {
				if(account.getPassword().equals(user.getPassword())) {
					httpSession.setAttribute("username", account.getUsername());
					httpSession.setAttribute("account", account);
					if (account.getChuTro()!=null) {
						httpSession.setAttribute("role", 1);
						return "redirect:/chutro/index.htm";
					} else if (account.getKhachThue()!=null) {
						httpSession.setAttribute("role", 2);
						return "redirect:/khachthue/index.htm";
					} else {
						httpSession.setAttribute("role", 3);
						return "redirect:/admin/index.htm";
					}
				} else {
					errors.rejectValue("username", "account", "Username or password is incorrect !");
					return "login";
				}
			} else {
				errors.rejectValue("username", "account", "Username or password is incorrect !");
				return "login";
			}
		}
		return "login";
	}

	@RequestMapping("logout")
	public String logout(ModelMap model, HttpSession session) {
		session.setAttribute("username", null);
		session.setAttribute("role", null);
		session.setAttribute("account", null);
		return "redirect:index.htm";
	}

	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String register(ModelMap model) {
		model.addAttribute("account", new Account());
		return "register";
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String register(ModelMap model, RedirectAttributes re ,
			@RequestParam("repassword") String repassword,
			@ModelAttribute("account") Account user,
			BindingResult errors, HttpSession httpSession, 
			HttpServletRequest request) {
		user.setUsername(user.getUsername().trim());
		user.setPassword(user.getPassword().trim());
		user.setCmnd(user.getCmnd().trim());
		user.setEmail(user.getEmail());
		user.setDienThoai(user.getDienThoai().trim());
		user.setNgayDangKy(new Date());
		if(request.getParameter("roles").isEmpty()) {
			errors.rejectValue("email", "Please enter your role !");
		}else {
			Session sessions = factory.getCurrentSession();
			Role role = (Role) sessions.get(Role.class, 
					Integer.parseInt(request.getParameter("roles")));
			user.setRole(role);
		}
		if (user.getUsername().isEmpty()) {
			errors.rejectValue("username", "account", 
					"Please enter your username !");
		} else if (user.getUsername().contains(" ")) {
			errors.rejectValue("username", "account", 
					"Username must not contain space !");
		} else if (isContainSpecialWord(user.getUsername())) {
			errors.rejectValue("username", "account", 
					"Username must not contain speacial character !");
		}
		if (user.getPassword().isEmpty()) {
			errors.rejectValue("password", "account", 
					"Please enter your password !");
		} else if (user.getPassword().contains(" ")) {
			errors.rejectValue("password", "account", 
					"Password must not contain space !");
		} else if (!user.getPassword().equals(repassword.trim())) {
			errors.rejectValue("password", "account", 
					"Retype password is not common!");
		}
		if (isContainSpecialWord(user.getPassword())) {
			errors.rejectValue("password", "account", 
					"Password must not contain speacial character !");
		}
		if (user.getHoTen().isEmpty()) {
			errors.rejectValue("hoTen", "account", 
					"Please enter fullname!");
		} else if (isContainSpecialWord(user.getHoTen())) {
			errors.rejectValue("hoTen", "account", 
					"Fullname must not contain speacial character !");
		}
		if (!user.getCmnd().isEmpty()) {
			Pattern VALID_CMND_REGEX = Pattern.compile("\\d",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = VALID_CMND_REGEX.matcher(user.getCmnd());
			if (!matcher.find()) {
				errors.rejectValue("cmnd", "account", "Please enter a valid cmnd!");
			}
		} else {
			errors.rejectValue("cmnd", "account", "Please enter cmnd!");
		} 
		if (!user.getEmail().isEmpty()) {
			Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^"
					+ "[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
			Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(user.getEmail());
			if (!matcher.find()) errors.rejectValue("email", "account", 
									"Please enter a valid email !");
		} else errors.rejectValue("email", "account", 
					"Please enter your email !");
		if (!user.getDienThoai().isEmpty()) {
			Pattern VALID_ID_NUMBER_REGEX = Pattern.compile("([0-9]{9,12})\\b",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = VALID_ID_NUMBER_REGEX.matcher(user.getDienThoai());
			if (!matcher.find()) errors.rejectValue("dienThoai", "account", 
									"Please enter a valid phone number !");
		} else errors.rejectValue("dienThoai", "account", 
					"Please enter your phone number !");
		if (!errors.hasErrors()) {
			Session session = factory.getCurrentSession();
			String hql = String.format("from Account where username='%s'", user.getUsername());
			Query query = session.createQuery(hql);
			List<Account> list = query.list();
			if (list.isEmpty()) {				
				Session session2 = factory.openSession();
				Transaction t = session2.beginTransaction();
				try {
					Path from = Paths.get(context.getRealPath("resources/images/avatar/"
							+ "user-default.png"));
					Path to = Paths.get(context.getRealPath("resources/images/avatar/" 
							+ user.getUsername() + ".png"));
					Files.copy(from, to);
					switch(Integer.parseInt(request.getParameter("roles"))){
					case 2: {
						KhachThue khachThue = new KhachThue();
						khachThue.setAccount(user);
						user.setKhachThue(khachThue);
						khachThue.setNamSinh(2000);
						session2.save(user);
						session2.save(khachThue);
						break;
					}
					case 1: {
						ChuTro chuTro = new ChuTro();
						chuTro.setAccount(user);
						user.setChuTro(chuTro);
						session2.save(user);
						session2.save(chuTro);
						break;
					}
					}
					t.commit();
					re.addFlashAttribute("success", 
							"Tài khoản đã được tạo thành công");
					httpSession.setAttribute("account", user);
					return "redirect:/login.htm";
				} catch (Exception e) {
					t.rollback();
					re.addFlashAttribute("message", 
							"Tạo tài khoản không thành công!");
					return "redirect:/register.htm";
				} finally {
					session2.close();
				}
			} else 	errors.rejectValue("username", "account", 
						"This username is available !");
		}
		return "register";
	}

	@RequestMapping("password")
	public String password(ModelMap model) {
		model.addAttribute("account", new Account());
		return "password";
	}
	@RequestMapping(value = "password", method = RequestMethod.POST)
	public String forgotpassword(ModelMap model, @ModelAttribute("account") Account user, BindingResult errors) {
		user.setUsername(user.getUsername().trim());
		user.setEmail(user.getEmail().trim());
		if (user.getUsername().isEmpty()) {
			errors.rejectValue("username", "account", "Please enter your username !");
		} else if (user.getUsername().contains(" ")) {
			errors.rejectValue("username", "account", "Username must not contain space !");
		}
		if (!user.getEmail().isEmpty()) {
			Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(user.getEmail());
			if (!matcher.find()) {
				errors.rejectValue("email", "account", "Please enter a valid email !");
			}
		} else {
			errors.rejectValue("email", "account", "Please enter your email !");
		}
		
		if(!errors.hasErrors()) {
		String hql = String.format("from Account where username = '%s' and email='%s'", user.getUsername(), user.getEmail());
		List<Object> list = getList(hql);
		if (list.isEmpty()) {
			errors.rejectValue("email", "account", "No account have this email!");
			return "password";
		} else {
			try {
				String body = "This is your account infomation: \n";
				for (int i = 0; i < list.size(); i++) {
					Account u = (Account) list.get(0);

					body += "Username: " + u.getUsername() + "\nEmail: " + u.getEmail() + "\nPassword: "
							+ u.getPassword() + "\n\n";
				}
//				System.out.println(body);
				// String from = "XGear - PC & Laptop Gaming";
				MimeMessage mail = mailer.createMimeMessage();

				MimeMessageHelper helper = new MimeMessageHelper(mail);
				// helper.setFrom(from, from);
				helper.setTo(user.getEmail());
				// helper.setReplyTo(from,from);
				helper.setSubject("Forgot Password");
				helper.setText(body, true);

				mailer.send(mail);
			} catch (Exception e) {
				model.addAttribute("message", e);
			}
			model.addAttribute("message", "We have sent the password to your email. ");
		}
	}
		return "password";
	}
	@RequestMapping(value="account", method=RequestMethod.GET)
	public String update(ModelMap model, HttpSession session) {
		model.addAttribute("user", session.getAttribute("account"));
		return "account/account";
	}
	@RequestMapping(value="account/doihoten/{username}", method=RequestMethod.POST)
	public String doihoten(@PathVariable("username") String username,
			@RequestParam("hoten") String hoten,
			@RequestParam("password") String password, 
			RedirectAttributes re) {
		hoten=hoten.trim();
		password=password.trim();
		if(hoten.isBlank()) {
			re.addFlashAttribute("error", "Please enter a valid name!");
		} else if (isContainSpecialWord(hoten)) {
			re.addFlashAttribute("error", "Fullname must not contain speacial character !");
		}
		if(password.isBlank()) {
			re.addFlashAttribute("error", "Please enter your password!");
		} else if (isContainSpecialWord(password)) {
			re.addFlashAttribute("error", "Password must not contain speacial character !");
		} else {
			Session session2 = factory.getCurrentSession();
			Account account = (Account) session2.get(Account.class, username);
			session2.clear();
			if(account.getPassword().equals(password)) {
				account.setHoTen(hoten);
				session2 = factory.openSession();
				Transaction t = session2.beginTransaction();
				try {
					session2.update(account);
					t.commit();
					re.addAttribute("success", "Thay đổi của bạn đã được thực hiện!");
				} catch (Exception e) {
					t.rollback();
					re.addFlashAttribute("error", "Lỗi!" + e);
				} finally {
					session2.clear();
				}
			} else {
				re.addFlashAttribute("error", "Your password is not correct!");
			}
		}
		return "redirect:../../account.htm";
	}
	@RequestMapping(value="account/doipassword/{username}", method=RequestMethod.POST)
	public String doipassword(@PathVariable("username") String username,
			@RequestParam("oldpassword") String oldpassword,
			@RequestParam("password") String password,
			@RequestParam("repassword") String repassword,
			RedirectAttributes re) {
		oldpassword=oldpassword.trim();
		password=password.trim();
		repassword=repassword.trim();
		if(oldpassword.isBlank()) {
			re.addFlashAttribute("error", "Please enter a valid name!");
		} else if (isContainSpecialWord(oldpassword)) {
			re.addFlashAttribute("error", "Fullname must not contain speacial character !");
		}
		if(password.isBlank()) {
			re.addFlashAttribute("error", "Please enter a valid name!");
		} else if (isContainSpecialWord(oldpassword)) {
			re.addFlashAttribute("error", "Fullname must not contain speacial character !");
		}
		if(!password.equals(repassword)) {
			re.addFlashAttribute("error", "Retype Password must be commom!");
		}
		if(oldpassword.equals(password)) {
			re.addFlashAttribute("error", "Yor password is the same as the old");
		}else {
			Session session2 = factory.getCurrentSession();
			Account account = (Account) session2.get(Account.class, username);
			session2.clear();
			if(account.getPassword().equals(oldpassword)) {
				account.setPassword(repassword);
				session2 = factory.openSession();
				Transaction t = session2.beginTransaction();
				try {
					session2.update(account);
					t.commit();
					re.addAttribute("sucess", "Thay đổi của bạn đã được thực hiện!");
				} catch (Exception e) {
					t.rollback();
					re.addFlashAttribute("error", "Lỗi!" + e);
				} finally {
					session2.clear();
				}
			} else {
				re.addFlashAttribute("error", "Your old password is not correct!");
			}
		}
		return "redirect:../../account.htm";
	}
	@RequestMapping(value="account/doicmnd/{username}", method=RequestMethod.POST)
	public String doicmnd(@PathVariable("username") String username,
			@RequestParam("cmnd") String cmnd,
			@RequestParam("password") String password,
			RedirectAttributes re) {
		cmnd=cmnd.trim();
		password=password.trim();
		if (!cmnd.isEmpty()) {
			Pattern VALID_CMND_REGEX = Pattern.compile("\\d",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = VALID_CMND_REGEX.matcher(cmnd);
			if (!matcher.find()) {
				re.addFlashAttribute("error", "Please enter a valid cmnd!");
			}
		} else {
			re.addFlashAttribute("error", "Please enter cmnd!");
		} 
		if(password.isBlank()) {
			re.addFlashAttribute("error", "Please enter your password!");
		} else if (isContainSpecialWord(password)) {
			re.addFlashAttribute("error", "Password must not contain speacial character !");
		}else {
			Session session2 = factory.getCurrentSession();
			Account account = (Account) session2.get(Account.class, username);
			session2.clear();
			if(account.getPassword().equals(password)) {
				account.setCmnd(cmnd);
				session2 = factory.openSession();
				Transaction t = session2.beginTransaction();
				try {
					session2.update(account);
					t.commit();
					re.addAttribute("success", "Thay đổi của bạn đã được thực hiện!");
				} catch (Exception e) {
					t.rollback();
					re.addFlashAttribute("error", "Lỗi!" + e);
				} finally {
					session2.clear();
				}
			} else {
				re.addFlashAttribute("error", "Your password is not correct!");
			}
		}
		return "redirect:../../account.htm";
	}
	@RequestMapping(value="account/doisdt/{username}", method=RequestMethod.POST)
	public String doisdt(@PathVariable("username") String username,
			@RequestParam("sdt") String sdt,
			@RequestParam("password") String password,
			RedirectAttributes re) {
		sdt=sdt.trim();
		password=password.trim();
		if (!sdt.isEmpty()) {
			Pattern VALID_PHONE_NUMBER_REGEX = Pattern.compile("(03|05|07|08|09|01[2|6|8|9])"
					+ "+([0-9]{8})\\\\b", Pattern.CASE_INSENSITIVE);
			Matcher matcher = VALID_PHONE_NUMBER_REGEX.matcher(sdt);
			if (!matcher.find()) {
				re.addFlashAttribute("error", "Please enter a valid phone number!");
			}
		} else {
			re.addFlashAttribute("error", "Please enter phone number!");
		} 
		if(password.isBlank()) {
			re.addFlashAttribute("error", "Please enter your password!");
		} else if (isContainSpecialWord(password)) {
			re.addFlashAttribute("error", "Password must not contain speacial character !");
		}else{
			Session session2 = factory.getCurrentSession();
			Account account = (Account) session2.get(Account.class, username);
			session2.clear();
			if(account.getPassword().equals(password)) {
				account.setDienThoai(sdt);
				session2 = factory.openSession();
				Transaction t = session2.beginTransaction();
				try {
					session2.update(account);
					t.commit();
					re.addAttribute("success", "Thay đổi của bạn đã được thực hiện!");
				} catch (Exception e) {
					t.rollback();
					re.addFlashAttribute("error", "Lỗi!" + e);
				}
			} else {
				re.addFlashAttribute("error", "Your password is not correct!");
			}
		}
		return "redirect:../../account.htm";
	}
	@RequestMapping(value="account/doiemail/{username}", method=RequestMethod.POST)
	public String doiemail(@PathVariable("username") String username,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			RedirectAttributes re) {
		email=email.trim();
		password=password.trim();
		if (!email.isEmpty()) {
			Pattern VALID_EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+"
					+ "\\\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
			Matcher matcher = VALID_EMAIL_REGEX.matcher(email);
			if (!matcher.find()) {
				re.addFlashAttribute("error", "Please enter a valid cmnd!");
			}
		} else {
			re.addFlashAttribute("error", "Please enter cmnd!");
		} 
		if(password.isBlank()) {
			re.addFlashAttribute("error", "Please enter your password!");
		} else if (isContainSpecialWord(password)) {
			re.addFlashAttribute("error", "Password must not contain speacial character !");
		} else{
			Session session2 = factory.getCurrentSession();
			Account account = (Account) session2.get(Account.class, username);
			session2.clear();
			if(account.getPassword().equals(password)) {
				account.setEmail(email);
				session2 = factory.openSession();
				Transaction t = session2.beginTransaction();
				try {
					session2.update(account);
					t.commit();
					re.addAttribute("success", "Thay đổi của bạn đã được thực hiện!");
				} catch (Exception e) {
					t.rollback();
					re.addFlashAttribute("error", "Lỗi!" + e);
				} finally {
					session2.clear();
				}
			} else {
				re.addFlashAttribute("error", "Your password is not correct!");
			}
		}
		return "redirect:../../account.htm";
	}
	@RequestMapping(value="account/doirole/{username}", method=RequestMethod.POST)
	public String doiemail(@PathVariable("username") String username,
			RedirectAttributes re, HttpSession session) {
		if(session.getAttribute("role").equals("3")) {
			Session session2 = factory.getCurrentSession();
			Account account = (Account) session2.get(Account.class, username);
			session2.clear();
			Role role = (Role) session2.get(Role.class, account.getRole().getId()==1?2:1);
			account.setRole(role);
			session2 = factory.openSession();
			Transaction t = session2.beginTransaction();
			try {
				session2.update(account);
				t.commit();
				re.addAttribute("success", "Thay đổi của bạn đã được thực hiện!");
			} catch (Exception e) {
				t.rollback();
				re.addFlashAttribute("error", "Lỗi!" + e);
			} finally {
				session2.clear();
			}
		}else {
			re.addFlashAttribute("error", "Bạn không có quyền thực hiện hành động này");
		}
		return "redirect:../../account.htm";
	}
	@RequestMapping(value="account/doiavata/{username}", method=RequestMethod.POST)
	public String doiavata(@PathVariable("username") String username, 
			RedirectAttributes re, HttpSession session,
			@RequestParam("avata") MultipartFile avata, @RequestParam("password") String password) {
		if(!avata.isEmpty()) {
			if (!(avata.getContentType().contains("jpeg") || avata.getContentType().contains("png"))) {
				re.addFlashAttribute("error", "File ảnh không đúng định dạng !");
				return "redirect:../" + username  + ".htm";
			}
			Account account = (Account) session.getAttribute("account");
			if(!account.getPassword().equals(password)) {
				re.addFlashAttribute("error", "Mật khẩu không đúng !");
				return "redirect:../" + username  + ".htm";
			}
			try {
				String avataPath=context.getRealPath("/resources/images/avatar/"+ username+".png");
				File temp = new File(avataPath); temp.delete();
				avata.transferTo(new File(avataPath));
				re.addFlashAttribute("success", "Thay đổi của bạn đã được thực hiện!");
			}catch(Exception e) {
				re.addFlashAttribute("error", "Lỗi!" + e);
			}
		}
		return "redirect:../../account.htm";
	}
	@RequestMapping(value="report", method = RequestMethod.POST)
	public String report(HttpSession session, RedirectAttributes re,
			@RequestParam("username") String username,
			@RequestParam("thongbao") String thongbao) {
		Session session2 = factory.getCurrentSession();
		Account account = (Account) session2.get(Account.class, username);
		String hql = "FROM Account WHERE role.id = 3";
		List <Object> list = getList(hql);
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			for (Object o:list) {
				Account admin = (Account) o;
				thongbao = account.getUsername() + " báo lỗi: " + thongbao;
				ThongBao tb = new ThongBao();
				tb.setAccount(admin);
				tb.setThoigian(new Date());
				tb.setThongbao(thongbao);
				session2.save(tb);
			}
			t.commit();
			re.addFlashAttribute("success", "Báo cáo của bạn đã được gửi");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "Lỗi chức năng");
		} finally {
			session2.clear();
		}
		if (account.getChuTro()!=null) return "redirect:chutro/index.htm";
		else if(account.getKhachThue()!=null) return "redirect:khachthue/index.htm";
		return "redirect:index.htm";
	}
	@RequestMapping(value="account/chutro", params= {"username"})
	public String chutro(ModelMap model, @RequestParam("username") String username) {
		Session session = factory.getCurrentSession();
		Account account = (Account) session.get(Account.class, username);
		model.addAttribute("user", account);
		return "account/chutro";
	}
	@RequestMapping(value="account/khachthue", params= {"username"})
	public String khachthue(ModelMap model, @RequestParam("username") String username) {
		Session session = factory.getCurrentSession();
		Account account = (Account) session.get(Account.class, username);
		model.addAttribute("user", account);
		return "account/khachthue";
	}
	@RequestMapping(value="account/nhatro/{id}")
	public String khachthue(RedirectAttributes re, HttpSession session,
			@PathVariable("id") int id) {
		Account account = (Account) session.getAttribute("account");
		if(account.getRole().getId()==1) return "redirect:/chutro/nhatro2.htm?id="+String.valueOf(id);
		return "redirect:/khachthue/nhatro/" + String.valueOf(id) + ".htm";
	}
	@RequestMapping(value="chutro/nhatro2.htm", params= {"id"})
	public String nhatro2(ModelMap model, @RequestParam("id") int id) {
		Session session = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session.get(NhaTro.class, id);
		session.clear();
		model.addAttribute("nhatro", nhatro);
		return "chutro/nhatro2";
	}
}
