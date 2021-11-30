package ptithcm.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.websocket.server.PathParam;

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

import ptithcm.entity.Account;
import ptithcm.entity.ChuTro;
import ptithcm.entity.Comment;
import ptithcm.entity.KhachThue;
import ptithcm.entity.LichHen;
import ptithcm.entity.NhaTro;
import ptithcm.entity.Province;
import ptithcm.entity.Role;
import ptithcm.entity.ThongBao;
import ptithcm.entity.Truong;
import ptithcm.entity.Ward;

@Transactional
@Controller
@RequestMapping("/admin/")

public class AdminController {
	
	@Autowired
	SessionFactory factory;
	@Autowired
	ServletContext context;
	public static boolean isContainSpecialWord(String str) {
		Pattern VALID_INPUT_REGEX = Pattern.compile("[$&+,:;=\\\\\\\\?@#|/'<>.^*()%!-]",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_INPUT_REGEX.matcher(str);
		return matcher.find();
	}
	@SuppressWarnings("unchecked")
	List<Object> getList(String hql) {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery(hql);
		List<Object> list = query.list();
		return list;
	}
	@ModelAttribute("accounts")
	public List<Object> getAccounts() {
		String hql = "from Account";
		List<Object> list = getList(hql);
		return list;
	}
	@ModelAttribute("thongbaoadmin")
	public List<Object> getThongBaos(HttpSession httpSession) {
		Account currentUser = (Account) httpSession.getAttribute("account");
		String hql = String.format("from ThongBao where account='%s'", currentUser.getUsername());
		List<Object> list = getList(hql);
		return list;
	}
	@ModelAttribute("chutros")
	public List<Object> getChuTros() {
		String hql = "from ChuTro";
		List<Object> list = getList(hql);
		return list;
	}
	@ModelAttribute("khachthues")
	public List<Object> getKhachThues() {
		String hql = "from KhachThue";
		List<Object> list = getList(hql);
		return list;
	}
	@RequestMapping("")
	public String welcome() {
		return "redirect:/admin/index.htm";
	}
	@RequestMapping("index")
	public String index() {
		return "redirect:/admin/account.htm";
	}
	// Account manager
	@RequestMapping("account")
	public String viewaccount() {
		return "admin/accounttable";
	}
	@RequestMapping(value = "editaccount/{username}", method = RequestMethod.GET)
	public String editaccount(ModelMap modelMap, @PathVariable("username") String username) {
		Session session = factory.getCurrentSession();
		Account account = (Account) session.get(Account.class, username);
		modelMap.addAttribute("account", account);
		modelMap.addAttribute("action", "edit");
		return "admin/accountform";
	}
	@RequestMapping(value = "editaccount", method = RequestMethod.POST)
	public String editaccountpost(@ModelAttribute("account") Account account, RedirectAttributes re,
			BindingResult errors, ModelMap model, @RequestParam("photo") MultipartFile photo) {
		account.setPassword(account.getPassword().trim().split(",")[0]);
		account.setCmnd(account.getCmnd().trim());
		account.setHoTen(account.getHoTen().trim());
		account.setEmail(account.getEmail());
		account.setDienThoai(account.getDienThoai().trim());
		if (account.getPassword().isEmpty()) {
			errors.rejectValue("password", "account", "Hãy nhập mật khẩu !");
		} else if (account.getPassword().contains(" ")) {
			errors.rejectValue("password", "account", "Mật khẩu không được chứa khoảng trắng !");
		}
		if (account.getCmnd().isEmpty()) {
			errors.rejectValue("cmnd", "account", "Hãy nhập CMND !");
		} else if (account.getPassword().contains(" ")) {
			errors.rejectValue("cmnd", "account", "CMND không được chứa khoảng trắng !");
		}
		if (account.getHoTen().isEmpty()) {
			errors.rejectValue("hoTen", "account", "Hãy nhập họ tên !");
		}
		if (!account.getEmail().isEmpty()) {
			Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(account.getEmail());
			if (!matcher.find()) {
				errors.rejectValue("email", "account", "Email không hợp lệ !");
			}
		} else {
			errors.rejectValue("email", "account", "Hãy nhập email !");
		}
		if (!account.getDienThoai().isEmpty()) {
			Pattern VALID_PHONE_NUMBER_REGEX = Pattern.compile("(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\\b",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = VALID_PHONE_NUMBER_REGEX.matcher(account.getDienThoai());
			Pattern VALID_ID_NUMBER_REGEX = Pattern.compile("([0-9]{9,12})\\b", Pattern.CASE_INSENSITIVE);
			Matcher matcher2 = VALID_ID_NUMBER_REGEX.matcher(account.getDienThoai());
			if (!matcher.find() || !matcher2.find()) {
				errors.rejectValue("dienThoai", "account", "Số điện thoại không hợp lệ !");
			}
		} else {
			errors.rejectValue("dienThoai", "account", "Hãy nhập số điện thoại !");
		}
		if (!errors.hasErrors()) {
			Session session2 = factory.openSession();
			Account oldAccount = (Account) session2.get(Account.class, account.getUsername());
			System.out.println(oldAccount.getUsername());
			oldAccount.setPassword(account.getPassword());
			oldAccount.setHoTen(account.getHoTen());
			oldAccount.setCmnd(account.getCmnd());
			oldAccount.setDienThoai(account.getDienThoai());
			oldAccount.setEmail(account.getEmail());
			Transaction t = session2.beginTransaction();
			try {
				if (photo.getOriginalFilename().isEmpty()) {
				} else if (!(photo.getContentType().contains("jpeg") || photo.getContentType().contains("png"))) {
					model.addAttribute("message", "File ảnh không đúng định dạng !");
					model.addAttribute("account", account);
					model.addAttribute("action", "edit");
					return "admin/accountform";
				} else {
					try {
						String photoPath = context.getRealPath("resources/images/avatar/" + account.getUsername() + ".png");
						photo.transferTo(new File(photoPath));
					} catch (Exception e) {
						re.addFlashAttribute("message", "Save file error: " + e);
						return "redirect:/admin/addaccount.htm";
					}
				}
				session2.update(oldAccount);
				t.commit();
				re.addFlashAttribute("message", "Thành công");
				return "redirect:/admin/editaccount/" + account.getUsername() + ".htm";
			} catch (Exception e) {
				t.rollback();
				re.addFlashAttribute("message", "Thất bại: " + e);
				return "redirect:/admin/editaccount/" + account.getUsername() + ".htm";
			} finally {
				session2.close();
			}
		}
		model.addAttribute("account", account);
		model.addAttribute("action", "edit");
		return "admin/accountform";
	}
	@RequestMapping(value = "addaccount", method = RequestMethod.GET)
	public String addaccount(ModelMap modelMap) {
		modelMap.addAttribute("account", new Account());
		modelMap.addAttribute("action", "add");
		return "admin/accountform";
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "addaccount", method = RequestMethod.POST)
	public String addaccountpost(@ModelAttribute("account") Account account, RedirectAttributes re,
			BindingResult errors, ModelMap model, HttpServletRequest request,
			@RequestParam("photo") MultipartFile photo) {
		account.setUsername(account.getUsername().trim());
		account.setPassword(account.getPassword().trim());
		account.setCmnd(account.getCmnd().trim());
		account.setHoTen(account.getHoTen().trim());
		account.setEmail(account.getEmail());
		account.setDienThoai(account.getDienThoai().trim());
		account.setNgayDangKy(new Date());
		if (request.getParameter("roles").isEmpty()) {
			errors.rejectValue("email", "Hãy chọn loại tài khoản !");
		} else {
			Session sessions = factory.getCurrentSession();
			Role role = (Role) sessions.get(Role.class, Integer.parseInt(request.getParameter("roles")));
			account.setRole(role);
		}
		if (account.getUsername().isEmpty()) {
			errors.rejectValue("username", "account", "Hãy nhập username !");
		} else if (account.getUsername().contains(" ")) {
			errors.rejectValue("username", "account", "Username không được chứa khoảng trắng !");
		}
		if (account.getPassword().isEmpty()) {
			errors.rejectValue("password", "account", "Hãy nhập mật khẩu !");
		} else if (account.getPassword().contains(" ")) {
			errors.rejectValue("password", "account", "Mật khẩu không được chứa khoảng trắng !");
		}
		if (account.getCmnd().isEmpty()) {
			errors.rejectValue("cmnd", "account", "Hãy nhập CMND !");
		} else if (account.getPassword().contains(" ")) {
			errors.rejectValue("cmnd", "account", "CMND không được chứa khoảng trắng !");
		}
		if (account.getHoTen().isEmpty()) {
			errors.rejectValue("hoTen", "account", "Hãy nhập họ tên !");
		}
		if (!account.getEmail().isEmpty()) {
			Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(account.getEmail());
			if (!matcher.find()) {
				errors.rejectValue("email", "account", "Email không hợp lệ !");
			}
		} else {
			errors.rejectValue("email", "account", "Hãy nhập email !");
		}

		if (!account.getDienThoai().isEmpty()) {
			Pattern VALID_PHONE_NUMBER_REGEX = Pattern.compile("(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\\b",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = VALID_PHONE_NUMBER_REGEX.matcher(account.getDienThoai());
			Pattern VALID_ID_NUMBER_REGEX = Pattern.compile("([0-9]{9,12})\\b", Pattern.CASE_INSENSITIVE);
			Matcher matcher2 = VALID_ID_NUMBER_REGEX.matcher(account.getDienThoai());
			if (!matcher.find() || !matcher2.find()) {
				errors.rejectValue("dienThoai", "account", "Số điện thoại không hợp lệ !");
			}
		} else {
			errors.rejectValue("dienThoai", "account", "Hãy nhập số điện thoại !");
		}

		if (!errors.hasErrors()) {
			Session session = factory.getCurrentSession();
			String hql = String.format("from Account where username='%s'", account.getUsername());
			Query query = session.createQuery(hql);
			List<Account> list = query.list();
			if (list.isEmpty()) {
				Session session2 = factory.openSession();
				Transaction t = session2.beginTransaction();
				try {
					if (photo.getOriginalFilename().isEmpty()) {
						Path from = Paths.get(context.getRealPath("/resources/images/avatar/user-default.png"));
						Path to = Paths
								.get(context.getRealPath("/resources/images/avatar/" + account.getUsername() + ".png"));
						Files.copy(from, to);
					} else if (!(photo.getContentType().contains("jpeg") || photo.getContentType().contains("png"))) {
						re.addFlashAttribute("message", "File ảnh không đúng định dạng !");
					} else {
						try {
							String photoPath = context
									.getRealPath("resources/images/avatar/" + account.getUsername() + ".png");
							photo.transferTo(new File(photoPath));
						} catch (Exception e) {
							re.addFlashAttribute("message", "Save file error: " + e);
							return "redirect:/admin/addaccount.htm";
						}
					}

					switch (Integer.parseInt(request.getParameter("roles"))) {
					case 2: {
						KhachThue khachThue = new KhachThue();
						khachThue.setAccount(account);
						khachThue.setNamSinh(2000);
						account.setKhachThue(khachThue);
						session2.save(account);
						session2.save(khachThue);
						break;
					}
					case 1: {
						ChuTro chuTro = new ChuTro();
						chuTro.setAccount(account);
						account.setChuTro(chuTro);
						session2.save(account);
						session2.save(chuTro);
						break;
					}
					}
					t.commit();
					re.addFlashAttribute("message", "Tài khoản đã được tạo thành công");
					return "redirect:/admin/account.htm";
				} catch (Exception e) {
					t.rollback();
					model.addAttribute("message", "Tạo tài khoản không thành công!" + e);
				} finally {
					session2.close();
				}

			} else {
				errors.rejectValue("username", "account", "This username is available !");
			}
		}
		model.addAttribute("account", account);
		model.addAttribute("action", "add");
		return "admin/accountform";
	}

	@RequestMapping("deleteaccount/{username}")
	public String deleteaccount(HttpSession httpSession, RedirectAttributes re,
			@PathVariable("username") String username) {
		Account currentaAccount = (Account) httpSession.getAttribute("account");
		if (!currentaAccount.getUsername().equals(username)) {
			Session session = factory.openSession();
			Transaction t = session.beginTransaction();
			Account account = (Account) session.get(Account.class, username);
			try {
				if (account.getRole().getId() == 1) {
					session.delete(account.getChuTro());
				} else if (account.getRole().getId() == 2) {
					session.delete(account.getKhachThue());
				}
				session.delete(account);
				t.commit();
				File avatar = new File(
						context.getRealPath("resources/images/avatar/" + account.getUsername() + ".png"));
				if (avatar.delete()) {
					System.out.println("Deleted the file: " + avatar.getName());
				} else {
					System.out.println("Failed to delete the file.");
				}
				re.addFlashAttribute("message", "Đã xóa " + username);
			} catch (Exception e) {
				t.rollback();
				re.addFlashAttribute("message", "Không thể xóa !\n" + e);
			} finally {
				session.close();
			}
		} else {
			re.addFlashAttribute("message", "Không thể xóa chính bạn !");
		}
		return "redirect:/admin/account.htm";
	}

	// Chu tro
	@RequestMapping("chutro")
	public String viewchutro() {
		return "admin/chutrotable";
	}
	// NhaTro
	@RequestMapping(value = "nhatro", params = "chu")
	public String viewnhatro(@PathParam("chu") int chu, ModelMap model) {
		String hql = "";
		if (chu == -1) {
			hql = "from NhaTro";
		} else {
			hql = "from NhaTro where chuTro.id='" + chu + "'";
		}
		List<Object> list = getList(hql);
		model.addAttribute("nhatros", list);
		model.addAttribute("chu", chu);
		return "admin/nhatrotable";
	}
	@RequestMapping(value = "approve/{id}", params = "chu")
	public String approve(ModelMap model, @PathParam("chu") int chu, @PathVariable("id") int id,
			RedirectAttributes re) {
		Session session = factory.openSession();
		NhaTro nhaTro = (NhaTro) session.get(NhaTro.class, id);
		nhaTro.setTinhtrang(1);
		nhaTro.setNgayThem(new Date());
		Transaction t = session.beginTransaction();
		try {
			session.update(nhaTro);
			t.commit();
			re.addFlashAttribute("message", "Đã duyệt!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("message", "Lỗi rồi: " + e);
		} finally {
			session.close();
		}
		model.addAttribute("action", "add");
		model.addAttribute("chu", chu);
		return "redirect:/admin/nhatro.htm?chu=" + chu;
	}

	@RequestMapping(value = "refuse/{id}", params = "chu")
	public String refuse(ModelMap model, @PathParam("chu") int chu, @PathVariable("id") int id, RedirectAttributes re) {
		Session session = factory.openSession();
		NhaTro nhaTro = (NhaTro) session.get(NhaTro.class, id);
		nhaTro.setTinhtrang(-1);
		Transaction t = session.beginTransaction();
		try {
			session.update(nhaTro);
			t.commit();
			re.addFlashAttribute("message", "Đã thay đổi!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("message", "Lỗi rồi: " + e);
		} finally {
			session.close();
		}
		model.addAttribute("action", "add");
		model.addAttribute("chu", chu);
		return "redirect:/admin/nhatro.htm?chu=" + chu;
	}

	@RequestMapping(value = "editnhatro/{id}", params = "chu", method = RequestMethod.GET)
	public String editnhatro(ModelMap modelMap, @PathVariable("id") int id, @PathParam("chu") int chu,
			HttpSession httpSession) {
		Session session = factory.getCurrentSession();
		NhaTro nhaTro = (NhaTro) session.get(NhaTro.class, id);
		modelMap.addAttribute("nhatro", nhaTro);
		modelMap.addAttribute("action", "edit");
		modelMap.addAttribute("provinces", httpSession.getAttribute("provinces"));
		modelMap.addAttribute("chu", chu);
		return "admin/nhatroform";
	}
	@RequestMapping(value = "editnhatro/{id}", params = { "chu" }, method = RequestMethod.POST)
	public String editnhatro(@ModelAttribute("nhatro") NhaTro nhaTro, RedirectAttributes re, BindingResult errors,
			ModelMap model, @RequestParam("photo1") MultipartFile photo1, @RequestParam("photo2") MultipartFile photo2,
			@PathParam("chu") int chu) {
		nhaTro.setTieuDe(nhaTro.getTieuDe().trim());
		nhaTro.setMoTa(nhaTro.getMoTa().trim());
		nhaTro.getDiachi().setDiaChi(nhaTro.getDiachi().getDiaChi().trim());
		if (nhaTro.getTieuDe().isEmpty()) {
			errors.rejectValue("tieuDe", "nhatro", "Hãy nhập tiêu đề !");
		}
		if (nhaTro.getMoTa().isEmpty()) {
			errors.rejectValue("moTa", "nhatro", "Hãy nhập mô tả !");
		}
		if (nhaTro.getDiachi().getDiaChi().isEmpty()) {
			errors.rejectValue("diaChi", "nhatro", "Hãy nhập địa chỉ !");
		}
		if (!errors.hasErrors()) {
			Session session = factory.openSession();
			NhaTro oldNhaTro = (NhaTro) session.get(NhaTro.class, nhaTro.getId());
			oldNhaTro.setTieuDe(nhaTro.getTieuDe());
			oldNhaTro.setTinhtrang(nhaTro.getTinhtrang());
			oldNhaTro.setSoPhongChoThue(nhaTro.getSoPhongChoThue());
			oldNhaTro.setSoPhongCoSan(nhaTro.getSoPhongCoSan());
			oldNhaTro.setSoNguoiTrenPhong(nhaTro.getSoNguoiTrenPhong());
			oldNhaTro.setDienTich(nhaTro.getDienTich());
			oldNhaTro.setTienThue(nhaTro.getTienThue());
			oldNhaTro.setTienCoc(nhaTro.getTienCoc());
			oldNhaTro.setMoTa(nhaTro.getMoTa());
			oldNhaTro.getDiachi().setDiaChi(nhaTro.getDiachi().getDiaChi());
			oldNhaTro.getDiachi().setWard((Ward) session.get(Ward.class, nhaTro.getDiachi().getWard().getId()));
			oldNhaTro.setNgayThem(new Date());
			Transaction t = session.beginTransaction();
			try {
				if (photo1.getOriginalFilename().isEmpty()) {
				} else if (!(photo1.getContentType().contains("jpeg") || photo1.getContentType().contains("png"))) {
					model.addAttribute("message", "File ảnh không đúng định dạng !");
					model.addAttribute("nhatro", nhaTro);
					model.addAttribute("chu", chu);
					model.addAttribute("action", "edit");
					return "admin/nhatroform";
				} else {
					try {
						String photoPath = context
								.getRealPath("resources/images/nhatro/" + oldNhaTro.getId() + "_1.png");
						photo1.transferTo(new File(photoPath));
					} catch (Exception e) {
						model.addAttribute("message", "Save file error: " + e);
						model.addAttribute("nhatro", nhaTro);
						model.addAttribute("chu", chu);
						model.addAttribute("action", "edit");
						return "admin/nhatroform";
					}
				}
				if (photo2.getOriginalFilename().isEmpty()) {

				} else if (!(photo2.getContentType().contains("jpeg") || photo2.getContentType().contains("png"))) {
					model.addAttribute("message", "File ảnh không đúng định dạng !");
					model.addAttribute("nhatro", nhaTro);
					model.addAttribute("chu", chu);
					model.addAttribute("action", "edit");
					return "admin/nhatroform";
				} else {
					try {
						String photoPath = context
								.getRealPath("resources/images/nhatro/" + oldNhaTro.getId() + "_2.png");
						photo2.transferTo(new File(photoPath));
					} catch (Exception e) {
						model.addAttribute("message", "Save file error: " + e);
						model.addAttribute("nhatro", nhaTro);
						model.addAttribute("chu", chu);
						model.addAttribute("action", "edit");
						return "admin/nhatroform";
					}
				}
				session.update(oldNhaTro);
				t.commit();
				re.addFlashAttribute("message", "Thành công");
				return "redirect:/admin/editnhatro/" + oldNhaTro.getId() + ".htm?chu=" + chu;
			} catch (Exception e) {
				t.rollback();
				re.addFlashAttribute("message", "Thất bại: " + e);
				return "redirect:/admin/editnhatro/" + oldNhaTro.getId() + ".htm?chu=" + chu;
			} finally {
				session.close();
			}
		}
		model.addAttribute("nhatro", nhaTro);
		model.addAttribute("action", "edit");
		return "admin/nhatroform";
	}

	// Khach thue
	@RequestMapping("khachthue")
	public String viewkhachthue() {
		return "admin/khachthuetable";
	}

	@RequestMapping(value = "editkhachthue/{id}", method = RequestMethod.GET)
	public String editkhach(ModelMap modelMap, @PathVariable("id") int id) {
		Session session = factory.getCurrentSession();
		KhachThue khachThue = (KhachThue) session.get(KhachThue.class, id);
		modelMap.addAttribute("khachthue", khachThue);
		modelMap.addAttribute("action", "edit");
		return "admin/khachthueform";
	}
	
	@RequestMapping(value = "editkhachthue/{id}", method = RequestMethod.POST)
	public String editkhach(@ModelAttribute("khachthue") KhachThue khachThue, RedirectAttributes re,
			BindingResult errors, ModelMap model, @PathVariable("id") int id) {
		khachThue.getTruong().setTen(khachThue.getTruong().getTen().trim());
		khachThue.setQueQuan(khachThue.getQueQuan().trim());
//		if (khachThue.getNamSinh()==null) {
//			errors.rejectValue("password", "account", "Hãy nhập mật khẩu !");
//		} 
		if (!errors.hasErrors()) {

			Session session = factory.openSession();
			KhachThue oldKhachThue = (KhachThue) session.get(KhachThue.class, khachThue.getId());
			oldKhachThue.getTruong().setTen(khachThue.getTruong().getTen());
			oldKhachThue.setNamSinh(khachThue.getNamSinh());
			oldKhachThue.setGioiTinh(khachThue.isGioiTinh());
			oldKhachThue.setQueQuan(khachThue.getQueQuan());
			Transaction t = session.beginTransaction();
			try {
				session.update(oldKhachThue);
				t.commit();
				re.addFlashAttribute("message", "Thành công");
				return "redirect:/admin/editkhachthue/" + oldKhachThue.getId() + ".htm";
			} catch (Exception e) {
				t.rollback();
				re.addFlashAttribute("message", "Thất bại: " + e);
				return "redirect:/admin/editkhachthue/" + oldKhachThue.getId() + ".htm";
			} finally {
				session.close();
			}
		}
		model.addAttribute("khachthue", khachThue);
		model.addAttribute("action", "edit");
		return "admin/khachthueform";
	}

	// Thong bao

	@RequestMapping(value = "thongbao", params = "user")
	public String viewnhatro(@PathParam("user") String user, ModelMap model) {
		String hql = "";
		if (user.equals("-1") || user.equals("")) {
			hql = "from ThongBao";
		} else {
			hql = "from ThongBao where account.username='" + user + "'";
		}
		List<Object> list = getList(hql);
		model.addAttribute("thongbaos", list);
		model.addAttribute("user", user);
		return "admin/thongbaotable";
	}

	@RequestMapping(value = "editthongbao/{id}", params = "user", method = RequestMethod.GET)
	public String editthongbao(ModelMap modelMap, @PathVariable("id") int id, @PathParam("user") String user) {
		Session session = factory.getCurrentSession();
		ThongBao thongBao = (ThongBao) session.get(ThongBao.class, id);
		modelMap.addAttribute("thongbao", thongBao);
		modelMap.addAttribute("action", "edit");
		modelMap.addAttribute("user", user);
		return "admin/thongbaoform";
	}

	@RequestMapping(value = "editthongbao/{id}", params = { "user" }, method = RequestMethod.POST)
	public String editthongbao(@ModelAttribute("thongbao") ThongBao thongBao, RedirectAttributes re,
			BindingResult errors, ModelMap model, @PathParam("user") String user) {
		thongBao.setThongbao(thongBao.getThongbao().trim());

		if (thongBao.getThongbao().isEmpty()) {
			errors.rejectValue("thongbao", "thongbao", "Hãy nhập nội dung !");
		}

		if (!errors.hasErrors()) {

			Session session = factory.openSession();
			ThongBao oldThongBao = (ThongBao) session.get(ThongBao.class, thongBao.getId());
			oldThongBao.setAccount(thongBao.getAccount());
			oldThongBao.setThoigian(thongBao.getThoigian());
			oldThongBao.setThongbao(thongBao.getThongbao());
			oldThongBao.setLink(thongBao.getLink());

			Transaction t = session.beginTransaction();
			try {
				session.update(oldThongBao);
				t.commit();
				re.addFlashAttribute("message", "Thành công");
				return "redirect:/admin/thongbao.htm?user=" + user;

			} catch (Exception e) {
				t.rollback();
				re.addFlashAttribute("message", "Thất bại: " + e);
				return "redirect:/admin/editthongbao/" + oldThongBao.getId() + ".htm?user=" + user;
			} finally {
				session.close();
			}
		}
		model.addAttribute("thongbao", thongBao);
		model.addAttribute("action", "edit");
		return "admin/thongbaoform";
	}

	@RequestMapping(value = "addthongbao", params = "user", method = RequestMethod.GET)
	public String editthongbao(ModelMap modelMap, @PathParam("user") String user) {
		modelMap.addAttribute("thongbao", new ThongBao());
		modelMap.addAttribute("action", "add");
		modelMap.addAttribute("user", user);
		return "admin/thongbaoform";
	}

	@RequestMapping(value = "addthongbao/{id}", params = { "user" }, method = RequestMethod.POST)
	public String addthongbao(@ModelAttribute("thongbao") ThongBao thongBao, RedirectAttributes re,
			BindingResult errors, ModelMap model, @PathParam("user") String user) {
		thongBao.setThongbao(thongBao.getThongbao().trim());

		if (thongBao.getThongbao().isEmpty()) {
			errors.rejectValue("thongbao", "thongbao", "Hãy nhập nội dung !");
		}

		if (!errors.hasErrors()) {

			Session session = factory.openSession();
			Transaction t = session.beginTransaction();
			try {
				session.save(thongBao);
				t.commit();
				re.addFlashAttribute("message", "Thành công");
				return "redirect:/admin/thongbao.htm?user=" + user;
			} catch (Exception e) {
				t.rollback();
				re.addFlashAttribute("message", "Thất bại: " + e);
				return "redirect:/admin/thongbao.htm?user=" + user;
			} finally {
				session.close();
			}
		}
		model.addAttribute("thongbao", thongBao);
		model.addAttribute("action", "add");
		return "admin/thongbaoform";
	}
	@RequestMapping(value = "deletethongbao/{id}", params = { "user" })
	public String deletethongbao(RedirectAttributes re, @PathVariable("id") int id, @PathParam("user") String user) {

		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		ThongBao thongBao = (ThongBao) session.get(ThongBao.class, id);
		try {
			session.delete(thongBao);
			t.commit();
			re.addFlashAttribute("message", "Đã xóa thông báo " + id);
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("message", "Không thể xóa !\n" + e);
		} finally {
			session.close();
		}

		return "redirect:/admin/thongbao.htm?user=" + user;
	}
	//so tài khoản được tạo
	public List<Object> thongketaikhoan (Calendar dau, Calendar cuoi) throws Exception{
		List<Object> data = new ArrayList<>();
		//Đếm số tháng
		int nam = cuoi.get(Calendar.YEAR) - dau.get(Calendar.YEAR);
		int thang = cuoi.get(Calendar.MONTH) - dau.get(Calendar.MONTH);
		int cot = nam*12 + thang + 1;
		List<String> head = new ArrayList<>();
		head.add("Thời gian"); head.add("Tổng"); head.add("Khách thuê"); head.add("Chủ trọ");
		data.add(head);
		String hql = String.format("FROM Account ORDER BY ngayDangKy ASC");
		Calendar datetemp = dau;
		List<Object> listaccount = getList(hql);
		for (int i=0; i<cot; i++){	
			List<Object> row = new ArrayList<Object>();
			row.add(String.valueOf(datetemp.get(Calendar.MONTH)) + "/" + String.valueOf(datetemp.get(Calendar.YEAR)));
			int count=0, khachthue=0, chutro=0;
			for (Object o:listaccount) {
				Account account = (Account) o;
				if(account.getNgayDangKy().before(datetemp.getTime())||account.getNgayDangKy().equals(datetemp.getTime())) {
					count++;
					if(account.getRole().getId()==1) chutro++;
					else khachthue++;
				}else break;
			}
			row.add(count); row.add(khachthue); row.add(chutro);
			data.add(row);
			datetemp.add(Calendar.MONTH, 1);
		}
		return data;
	}
	//số nhà trọ (bài đăng) được thêm
	public List<Object> thongkenhatro (Calendar dau, Calendar cuoi) throws Exception{
		List<Object> data = new ArrayList<>();
		//Đếm số tháng
		int nam = cuoi.get(Calendar.YEAR) - dau.get(Calendar.YEAR);
		int thang = cuoi.get(Calendar.MONTH) - dau.get(Calendar.MONTH);
		int cot = nam*12 + thang + 1;
		List<String> head = new ArrayList<>();
		head.add("Thời gian"); head.add("Nhà Trọ");
		data.add(head);
		String hql = String.format("FROM NhaTro ORDER BY ngayThem ASC");
		Calendar datetemp = dau;
		List<Object> listnhatro = getList(hql);
		for (int i=0; i<cot; i++){	
			List<Object> row = new ArrayList<Object>();
			if (datetemp.get(Calendar.MONTH)==0) row.add("12/" + String.valueOf(datetemp.get(Calendar.YEAR)));
			else row.add(String.valueOf(datetemp.get(Calendar.MONTH)) + "/" + String.valueOf(datetemp.get(Calendar.YEAR)));
			int count=0;
			for (Object o:listnhatro) {
				NhaTro nhatro = (NhaTro) o;
				if(nhatro.getNgayThem().before(datetemp.getTime())||nhatro.getNgayThem().equals(datetemp.getTime())) {
					count++;
				}else break;
			}
			row.add(count);
			data.add(row);
			datetemp.add(Calendar.MONTH, 1);
		}
		return data;
	}
	public static List<Object> thongkediem(Account account, Calendar dau, Calendar cuoi) throws Exception{
		//Đếm số tháng
		int nam = cuoi.get(Calendar.YEAR) - dau.get(Calendar.YEAR);
		int thang = cuoi.get(Calendar.MONTH) - dau.get(Calendar.MONTH);
		int cot = nam*12 + thang + 1;
		//data[] có dạng ["Thời gian", "Nhà trọ [1]", "Nhà trọ [2]",...],
		//				["1/2021", 4.6, 3.0,...],
		//				["2/2021", 4.3, 2.5,...],...
		List<Object> data= new ArrayList<Object>();
		List<Object> head = new ArrayList<Object>();
		head.add("Thời gian");
		for (NhaTro nt:account.getChuTro().getNhaTro()) {
			head.add("Nhà trọ: [" + String.valueOf(nt.getId()) + "]");
		}
		data.add(head);
		Calendar datetemp = dau;
		int dem=0;
		for (int i=0; i<cot; i++) {//i là dòng	
			List<Object> row = new ArrayList<Object>();
			if (datetemp.get(Calendar.MONTH)==0) row.add("12/" + String.valueOf(datetemp.get(Calendar.YEAR)));
			else row.add(String.valueOf(datetemp.get(Calendar.MONTH)) + "/" + String.valueOf(datetemp.get(Calendar.YEAR)));
			for (NhaTro nt:account.getChuTro().getNhaTro()) {
				//tính điểm cho biểu đồ đường
				dem=1;//mẫu số
				float diem=5;
				for (Comment comment:nt.getComment()) {
					if(comment.getThoigian().before(datetemp)||comment.getThoigian().equals(datetemp)) {
						diem+=comment.getDiem();											
						dem++;
					}//cho tới thời điểm được chọn diem = tổng điểm/mẫu số
				}
				row.add(diem/dem);
			}
			data.add(row);
			datetemp.add(Calendar.MONTH, 1);
		}
		return data;
	}
	public static List<Object> thongketinhtrang(Account account, Calendar dau, Calendar cuoi) throws Exception{
		List<Object> data = new ArrayList<Object>();
		int dem=0;
		for (NhaTro nt:account.getChuTro().getNhaTro()) {
			List<Object> dong = new ArrayList<>();
			dong.add("Nhà trọ [" + String.valueOf(nt.getId())+"]");
			//					nhà trọ, dy, cdy, tc, tb
			//data[] có dạng ["Nhà trọ [1]", 1, 1, 1, 1],
			//				["Nhà trọ [2]", 1, 1, 1, 1],...
			int dy=0, cdy=0, tc=0, tb=0;
			boolean co=false;
			for (LichHen lh:nt.getLichHen()) {
				if(lh.getThoigian().before(cuoi.getTime())&&lh.getThoigian().after(dau.getTime())) {
					if(lh.getDongy()) dy++;
					else if(lh.getThoigian().before(new Date())) tb++;
					else cdy++;
					if(lh.getThanhcong()) tc++;
					dong.add(dy); dong.add(tc); dong.add(tb); dong.add(cdy);dong.add(dem); dem++;
					co=true;
				}
			}
			if (co) data.add(dong);
		}
		return data;
	}
	@RequestMapping(value="thongke", method=RequestMethod.GET)
	public String thongke() {
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		return "redirect:thongke.htm?begin=2021-01-01&end="+date;
	}
	@SuppressWarnings("finally")
	@RequestMapping(value="thongke", params={"begin","end"}, method = RequestMethod.GET)
	public String thongke(ModelMap model, 
		@RequestParam("begin") String begin, @RequestParam("end") String end){
		try{
			Calendar dau = new GregorianCalendar(Integer.parseInt(begin.split("-")[0]), Integer.parseInt(begin.split("-")[1]), Integer.parseInt(begin.split("-")[2]));
			Calendar cuoi = new GregorianCalendar(Integer.parseInt(end.split("-")[0]), Integer.parseInt(end.split("-")[1]), Integer.parseInt(end.split("-")[2]));
			Calendar dau1 = new GregorianCalendar(Integer.parseInt(begin.split("-")[0]), Integer.parseInt(begin.split("-")[1]), Integer.parseInt(begin.split("-")[2]));
			Calendar cuoi1 = new GregorianCalendar(Integer.parseInt(end.split("-")[0]), Integer.parseInt(end.split("-")[1]), Integer.parseInt(end.split("-")[2]));
			//kt ngày trc có > ngày sau ko
		if (dau.equals(cuoi)||dau.after(cuoi)) throw new ParseException(end, 0);
		model.addAttribute("taikhoans", thongketaikhoan(dau, cuoi));
		model.addAttribute("nhatros", thongkenhatro(dau1, cuoi1));
		} catch (ParseException e) {
			model.addAttribute("error", "Ngày không hợp lệ!");
		} catch (Exception e){
			model.addAttribute("error", "Lỗi! " + e);
		} finally {
			model.addAttribute("begin", begin);
			model.addAttribute("end", end);
			return "admin/thongke";
		}
	}
	@SuppressWarnings("finally")
	@RequestMapping(value="thongkechutro", params= {"chutro", "begin", "end"}, method = RequestMethod.GET)
	public String thongkechutro(ModelMap model, @RequestParam("chutro") int id,
		@RequestParam("begin") String begin, @RequestParam("end") String end) {
		Session session = factory.getCurrentSession();
		try{
			ChuTro chutro = (ChuTro) session.get(ChuTro.class, id);
			Calendar dau = new GregorianCalendar(Integer.parseInt(begin.split("-")[0]), Integer.parseInt(begin.split("-")[1]), Integer.parseInt(begin.split("-")[2]));
			Calendar cuoi = new GregorianCalendar(Integer.parseInt(end.split("-")[0]), Integer.parseInt(end.split("-")[1]), Integer.parseInt(end.split("-")[2]));
			Calendar dau1 = new GregorianCalendar(Integer.parseInt(begin.split("-")[0]), Integer.parseInt(begin.split("-")[1]), Integer.parseInt(begin.split("-")[2]));
			Calendar cuoi1 = new GregorianCalendar(Integer.parseInt(end.split("-")[0]), Integer.parseInt(end.split("-")[1]), Integer.parseInt(end.split("-")[2]));
			//kt ngày trc có > ngày sau ko
		if (dau.equals(cuoi)||dau.after(cuoi)) throw new ParseException(end, 0);
		model.addAttribute("data", thongkediem(chutro.getAccount(), dau, cuoi));		
		model.addAttribute("pie", thongketinhtrang(chutro.getAccount(), dau1, cuoi1));
		} catch (ParseException e) {
			model.addAttribute("error", "Ngày không hợp lệ!");
		} catch (Exception e){
			model.addAttribute("error", "Lỗi! " + e);
		} finally {
			session.clear();
			model.addAttribute("begin", begin);
			model.addAttribute("end", end);
			model.addAttribute("chutro", id);
			return "admin/thongkechutro";
		}
	}
	@RequestMapping(value="thongkechutro", params={"chutro"}, method=RequestMethod.GET)
	public String thongkechutro(@RequestParam("chutro") String id) {
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		return "redirect:thongkechutro.htm?chutro="+id+"&begin=2021-01-01&end="+date;
	}
	@RequestMapping(value="thongkechutro", method=RequestMethod.GET)
	public String thongkechutro() {
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String hql = "FROM ChuTro";
		List<Object> list = getList(hql);
		ChuTro chutro = (ChuTro) list.get(0);
		return "redirect:thongkechutro.htm?chutro="+chutro.getId()+"&begin=2021-01-01&end="+date;
	}
	@RequestMapping(value="truong", method = RequestMethod.GET)
	public String truong(ModelMap model, HttpSession session) {
		String hql = "FROM Truong";
		model.addAttribute("truongs", getList(hql));
		model.addAttribute("provinces", session.getAttribute("provinces"));
		return "admin/truong";
	}
	@SuppressWarnings("finally")
	@RequestMapping(value="truong/themtruong", method = RequestMethod.POST)
	public String themtruong(RedirectAttributes re, 
			@RequestParam("ten") String ten, @RequestParam("idprovince") int idprovince) {
		if(isContainSpecialWord(ten)) {
			re.addFlashAttribute("error", "Bạn đang muốn hack trang web à?");
			return "redirect:../../logout.htm";
		}
		Session session2 = factory.getCurrentSession();
		Province province = (Province) session2.get(Province.class, idprovince);
		if(province==null) {
			re.addFlashAttribute("error", "Không tìm thấy tỉnh bạn chọn!");
			session2.clear();
			return "redirect:../truong.htm";
		}
		String hql="FROM Truong WHERE ten='" + ten +"'";
		List<Object> finds = getList(hql);
		if(!finds.isEmpty()) {
			re.addFlashAttribute("error", "Trùng tên trường!");
			session2.clear();
			return "redirect:../truong.htm";
		}
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			Truong truong = new Truong();
			truong.setTen(ten);
			truong.setProvince(province);
			session2.save(truong);
			t.commit();
			re.addFlashAttribute("success", "Thêm thành công! ");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "Lỗi! " + e);
		} finally {
			session2.clear();
			return "redirect:../truong.htm";
		}
	}
	@SuppressWarnings("finally")
	@RequestMapping(value="truong/chinhsuatruong", method = RequestMethod.POST)
	public String chinhsuatruong(RedirectAttributes re, @RequestParam("idtruong") int idtruong, 
			@RequestParam("ten") String ten, @RequestParam("idprovince") int idprovince) {
		if(isContainSpecialWord(ten)) {
			re.addFlashAttribute("error", "Bạn đang muốn hack trang web à?");
			return "redirect:../../logout.htm";
		}
		Session session2 = factory.getCurrentSession();
		Province province = (Province) session2.get(Province.class, idprovince);
		Truong truong = (Truong) session2.get(Truong.class, idtruong);
		if(province==null) {
			re.addFlashAttribute("error", "Không tìm thấy tỉnh/thành phố bạn chọn!");
			session2.clear();
			return "redirect:../truong.htm";
		}
		if(truong==null) {
			re.addFlashAttribute("error", "Không tìm thấy trường bạn chọn!");
			session2.clear();
			return "redirect:../truong.htm";
		}
		String hql="FROM Truong WHERE ten='" + ten +"' AND NOT id=" + String.valueOf(idtruong);
		List<Object> finds = getList(hql);
		if(!finds.isEmpty()) {
			re.addFlashAttribute("error", "Trùng tên trường!");
			session2.clear();
			return "redirect:../truong.htm";
		}
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			truong.setProvince(province);
			truong.setTen(ten);
			session2.update(truong);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "Lỗi! " + e);
		} finally {
			session2.clear();
			return "redirect:../truong.htm";
		}
	}	
}
