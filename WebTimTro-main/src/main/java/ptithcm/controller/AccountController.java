package ptithcm.controller;


import java.io.File;
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
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.loader.plan.exec.query.spi.QueryBuildingParameters;
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

import ptithcm.Random.Random;
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
		if (user.getUsername().isEmpty()) //Username trống
			errors.rejectValue("username", "account", 
					"Please enter your username !");
		else if (user.getUsername().contains(" ")) //Chứa khoảng tráng
			errors.rejectValue("username", "account", 
					"Username must not contain space !");
		
		if (user.getPassword().isEmpty()) 
			errors.rejectValue("password", "account", 
					"Please enter your password !");
		else if (user.getPassword().contains(" ")) 
			errors.rejectValue("password", "account", 
					"Password must not contain space !");
		if (!errors.hasErrors()) {
			Session session = factory.getCurrentSession(); //Tạo session để query đến db
			Account account = (Account) session.get(Account.class, user.getUsername());
			if (account!=null) {//get record account tương ứng với Username (username là khóa chính)
				if(account.getPassword().equals(user.getPassword())) {
					httpSession.setAttribute("account", account);//Tạo httpSession cho account
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

	@SuppressWarnings("unchecked")
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
		if(request.getParameter("roles").isEmpty()) 
			errors.rejectValue("email", "Please enter your role !");
		else {
			Session sessions = factory.getCurrentSession();
			Role role = (Role) sessions.get(Role.class, 
					Integer.parseInt(request.getParameter("roles")));
			user.setRole(role);
		}
		if (user.getUsername().isEmpty()) 
			errors.rejectValue("username", "account", 
					"Please enter your username !");
		else if (user.getUsername().contains(" ")) 
			errors.rejectValue("username", "account", 
					"Username must not contain space !");
		if (user.getPassword().isEmpty()) 
			errors.rejectValue("password", "account", 
					"Please enter your password !");
		else if (user.getPassword().contains(" ")) 
			errors.rejectValue("password", "account", 
					"Password must not contain space !");
		else if (!user.getPassword().equals(repassword.trim())) 
			errors.rejectValue("password", "account", 
					"Retype password is not common!");		
		if (user.getHoTen().isEmpty()) 
			errors.rejectValue("hoTen", "account", 
					"Please enter fullname!");
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
			Query query = session.createQuery("FROM Account WHERE username=:username");
			query.setString("username", user.getUsername());
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
	
	
	@SuppressWarnings("unchecked")
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
		
		if(errors.hasErrors()) return "redirect:password.html";
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Account WHERE username=:username AND email=:email");
		query.setString("username", user.getUsername()).setString("email", user.getEmail());
		List<Account> list = (List<Account>) query.list();
		session.clear();
		if (list.isEmpty()) 
			errors.rejectValue("email", "account", "Wrong username or email");
		else {
			String password = Random.RandomString(40, Random.character);
			String body = String.format("<h1>[Quản lí nhà trọ]</h1> "
					+ "<p>Xin chào [%s],</p> "
					+ "<p>Mật khẩu của bạn đã được reset</p>"
					+ "<p>Mật khẩu mới của bạn là:</p>"
					+ "<div style=\"padding: 2px 16px; background-color: yellow; box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);\">\r\n"
					+ "	<div class=\"container\">\r\n"
					+ "		<h2>%s</h2>\r\n"
					+ "	</div>\r\n"
					+ "</div>"
					+ "<p>Hãy đăng nhập lại và đổi lại mật khẩu. "
					+ "Sau khi đăng nhập hãy xóa mail này để đảm bảo an toàn"
					+ "</p>", user.getUsername(), password);
			MimeMessage mail = mailer.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mail);
			try {
				helper.setFrom("hoanglongmap2468@gmail.com");
				helper.setTo(user.getEmail());
				helper.setSubject("QLSV thông báo reset password");
				helper.setText(body, true);
				user = list.get(0);
				user.setPassword(password);
				session = factory.openSession();
				Transaction t = session.beginTransaction();
				try {
					session.update(user);
					t.commit();
					model.addAttribute("success","Password của bạn đã được reset, check mail để lấy password mới");
				} catch (Exception e) {
					t.rollback();
					errors.rejectValue("email", "account", "Password của bạn chưa được reset, hãy báo lại với admin");
					e.printStackTrace();
				}
			} catch (MessagingException e) {
				model.addAttribute("message", "Không thể mail đến email của bạn!");
				e.printStackTrace();
			}			
			mailer.send(mail);
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
		try {	
			hoten=hoten.trim();
			password=password.trim();
			if(hoten.isBlank()) 
				throw new Exception("Please enter a valid name!");		
			if(password.isBlank()) 
				throw new Exception("Please enter your password!");			
			Session session2 = factory.getCurrentSession();
			Account account = (Account) session2.get(Account.class, username);
			session2.clear();
			if(!account.getPassword().equals(password))
				throw new Exception("Wrong password!");
			account.setHoTen(hoten);
			session2 = factory.openSession();
			Transaction t = session2.beginTransaction();
			try {
				session2.update(account);
				t.commit();
				re.addAttribute("success", "Thay đổi của bạn đã được thực hiện!");
			} catch (Exception e) {
				t.rollback();
				re.addFlashAttribute("error", "Lỗi!");
				e.printStackTrace();
			} finally {
				session2.clear();
			}	
		}catch (Exception e) {
			re.addFlashAttribute("error", e);
		}
		return "redirect:../../account.htm";
	}
	@RequestMapping(value="account/doipassword/{username}", method=RequestMethod.POST)
	public String doipassword(@PathVariable("username") String username,
			@RequestParam("oldpassword") String oldpassword,
			@RequestParam("password") String password,
			@RequestParam("repassword") String repassword,
			RedirectAttributes re) {
		try {
			oldpassword=oldpassword.trim();
			password=password.trim();
			repassword=repassword.trim();
			if(oldpassword.isBlank()) 
				throw new Exception("Please enter a valid name!");		
			if(password.isBlank()) 
				throw new Exception("Please enter a valid name!");
			if(!password.equals(repassword)) 
				throw new Exception("Retype Password must be commom!");		
			Session session2 = factory.getCurrentSession();
			Account account = (Account) session2.get(Account.class, username);
			session2.clear();
			if(!account.getPassword().equals(oldpassword))
				throw new Exception("Wrong old password!");
			if(!oldpassword.equals(password)) 
				throw new Exception("Yor password is the same as the old!");
			account.setPassword(repassword);
			session2 = factory.openSession();
			Transaction t = session2.beginTransaction();
			try {
				session2.update(account);
				t.commit();
				re.addFlashAttribute("sucess", "Thay đổi của bạn đã được thực hiện!");
			} catch (Exception e) {
				t.rollback();
				re.addFlashAttribute("error", "Lỗi!");
				e.printStackTrace();
			} finally {
				session2.clear();
			}
		}catch (Exception e) {
			re.addFlashAttribute("error", e);
		}
		return "redirect:../../account.htm";
	}
	@RequestMapping(value="account/doicmnd/{username}", method=RequestMethod.POST)
	public String doicmnd(@PathVariable("username") String username,
			@RequestParam("cmnd") String cmnd,
			@RequestParam("password") String password,
			RedirectAttributes re) {
		try {	
			cmnd=cmnd.trim();
			password=password.trim();
			if (!cmnd.isEmpty()) {
				Pattern VALID_CMND_REGEX = Pattern.compile("\\d",
						Pattern.CASE_INSENSITIVE);
				Matcher matcher = VALID_CMND_REGEX.matcher(cmnd);
				if (!matcher.find()) 
					throw new Exception ("Please enter a valid cmnd!");	
			}else throw new Exception ("Plead enter a valid cmnd!");							 
			if(password.isBlank()) 
				throw new Exception ("Please enter your password!");
			Session session2 = factory.getCurrentSession();
			Account account = (Account) session2.get(Account.class, username);
			session2.clear();
			if(!account.getPassword().equals(password))
				throw new Exception ("Wrong password!");
			account.setCmnd(cmnd);
			session2 = factory.openSession();
			Transaction t = session2.beginTransaction();
			try {
				session2.update(account);
				t.commit();
				re.addFlashAttribute("success", "Thay đổi của bạn đã được thực hiện!");
			} catch (Exception e) {
				t.rollback();
				re.addFlashAttribute("error", "Lỗi!");
				e.printStackTrace();
			} finally {
				session2.clear();
			}
		}catch (Exception e) {
			re.addFlashAttribute("error", e);
		}
		return "redirect:../../account.htm";
	}
	@RequestMapping(value="account/doisdt/{username}", method=RequestMethod.POST)
	public String doisdt(@PathVariable("username") String username,
			@RequestParam("sdt") String sdt,
			@RequestParam("password") String password,
			RedirectAttributes re) {
		try {
			sdt=sdt.trim();
			password=password.trim();
			if (!sdt.isEmpty()) {
				Pattern VALID_PHONE_NUMBER_REGEX = Pattern.compile("(03|05|07|08|09|01[2|6|8|9])"
						+ "+([0-9]{8})\\\\b", Pattern.CASE_INSENSITIVE);
				Matcher matcher = VALID_PHONE_NUMBER_REGEX.matcher(sdt);
				if (!matcher.find()) 
					throw new Exception ("Please enter a valid phone number!");			
			} else  throw new Exception ("Please enter phone number!");		
			if(password.isBlank()) 
				throw new Exception ("Please enter your password!");
			Session session2 = factory.getCurrentSession();
			Account account = (Account) session2.get(Account.class, username);
			session2.clear();
			if(!account.getPassword().equals(password))
				throw new Exception ("Wrong password");
			account.setDienThoai(sdt);
			session2 = factory.openSession();
			Transaction t = session2.beginTransaction();
			try {
				session2.update(account);
				t.commit();
				re.addFlashAttribute("success", "Thay đổi của bạn đã được thực hiện!");
			} catch (Exception e) {
				t.rollback();
				re.addFlashAttribute("error", "Lỗi!");
				e.printStackTrace();
			}
		}catch (Exception e) {
			re.addFlashAttribute("error", e);
		}
		return "redirect:../../account.htm";
	}
	@RequestMapping(value="account/doiemail/{username}", method=RequestMethod.POST)
	public String doiemail(@PathVariable("username") String username,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			RedirectAttributes re) {
		try {
			email=email.trim();
			password=password.trim();
			if (!email.isEmpty()) {
				Pattern VALID_EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+"
						+ "\\\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
				Matcher matcher = VALID_EMAIL_REGEX.matcher(email);
				if (!matcher.find())
					throw new Exception ("Please enter a valid cmnd!");				
			} else throw new Exception ("Please enter cmnd!");
			if(password.isBlank()) 
				throw new Exception ("Please enter your password!");
			Session session2 = factory.getCurrentSession();
			Account account = (Account) session2.get(Account.class, username);
			session2.clear();
			if(!account.getPassword().equals(password))
				throw new Exception ("Wrong password");
			account.setEmail(email);
			session2 = factory.openSession();
			Transaction t = session2.beginTransaction();
			try {
				session2.update(account);
				t.commit();
				re.addFlashAttribute("success", "Thay đổi của bạn đã được thực hiện!");
			} catch (Exception e) {
				t.rollback();
				re.addFlashAttribute("error", "Lỗi!" + e);
			} finally {
				session2.clear();
			}
		}catch(Exception e) {
			re.addFlashAttribute("error", e);
		}
		return "redirect:../../account.htm";
	}
	@RequestMapping(value="account/doirole/{username}", method=RequestMethod.POST)
	public String doiemail(@PathVariable("username") String username,
			RedirectAttributes re, HttpSession session) {
		try {
			if(session.getAttribute("role").equals("3")) 
				throw new Exception ("Bạn không có quyền thực hiện hành động này!");
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
				re.addFlashAttribute("success", "Thay đổi của bạn đã được thực hiện!");
			} catch (Exception e) {
				t.rollback();
				re.addFlashAttribute("error", "Lỗi!" + e);
			} finally {
				session2.clear();
			}
		}catch (Exception e) {
			re.addFlashAttribute("error", e);
		}
		return "redirect:../../account.htm";
	}
	@RequestMapping(value="account/doiavata/{username}", method=RequestMethod.POST)
	public String doiavata(@PathVariable("username") String username, 
			RedirectAttributes re, HttpSession session,
			@RequestParam("avata") MultipartFile avata, @RequestParam("password") String password) {
		try {
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
				String avataPath=context.getRealPath("/resources/images/avatar/"+ username+".png");
				File temp = new File(avataPath); temp.delete();
				avata.transferTo(new File(avataPath));
				re.addFlashAttribute("success", "Thay đổi của bạn đã được thực hiện!");
			}
		}catch (Exception e) {
			re.addFlashAttribute("error", e);
		}
		return "redirect:../../account.htm";
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value="report", method = RequestMethod.POST)
	public String report(HttpSession session, RedirectAttributes re,
			@RequestParam("username") String username,
			@RequestParam("thongbao") String thongbao) {
		Session session2 = factory.getCurrentSession();
		Account account = (Account) session2.get(Account.class, username);
		Query query = session2.createQuery("FROM Account WHERE role.id = 3");
		List<Account> list = (List<Account>) query.list();
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			for (Account admin:list) {
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
