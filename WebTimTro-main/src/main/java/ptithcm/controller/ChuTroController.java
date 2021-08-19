package ptithcm.controller;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ptithcm.entity.Account;
import ptithcm.entity.Comment;
import ptithcm.entity.DiaChi;
import ptithcm.entity.LichHen;
import ptithcm.entity.NhaTro;
import ptithcm.entity.Province;
import ptithcm.entity.ThongBao;
import ptithcm.entity.Ward;
import ptithcm.service.ProvinceService;

@Transactional
@Controller
@RequestMapping("/chutro/")
public class ChuTroController {
	@Autowired
	ServletContext context;
	
	@Autowired
	SessionFactory factory;
	
	@RequestMapping("")
	public String welcome() {
		return "redirect:index.htm";
	}
	
	@ModelAttribute("provinces")
	public Object getProvinces(HttpSession session){
		return session.getAttribute("provinces");
	}
	
	@ModelAttribute("user")
	public Account user(HttpSession session) {
		Session session2 = factory.getCurrentSession();
		return (Account) session2.get(Account.class, (String) session.getAttribute("username"));
	}
	
	List<Object> getList(String hql) {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery(hql);
		List<Object> list = query.list();
		session.clear();
		return list;
	}
	// Đổ thông tin thông báo từ session
	@ModelAttribute("thongbaos")
	public <T> Collection<ThongBao> getThongBao(HttpSession session, ModelMap model) {
		Session session2 = factory.getCurrentSession();
		Account account = (Account) session.getAttribute("account");
		String tblichhen = "Hôm nay bạn có một buổi hẹn xem phòng!";
		for (ThongBao tb : account.getThongBao()) {
			for (LichHen lichhen : account.getChuTro().getLichHen()) {
				if (lichhen.getThoigian().equals(new Date())&&!tb.getThongbao().equals(tblichhen)) {
					ThongBao thongbao = new ThongBao();
					thongbao.setAccount(account);
					thongbao.setThoigian(new Date());
					thongbao.setThongbao(tblichhen);
					thongbao.setLink("/chutro/lichhen.htm");
					Session session3 = factory.openSession();
					Transaction t = session3.beginTransaction();
					try {
						session3.save(thongbao);
						t.commit();
					} catch (Exception e) {
						t.rollback();
						model.addAttribute("error", "Lỗi!" + e);
					}
					break;
				}
			}
		}
		List<ThongBao> thongbao = (List<ThongBao>) account.getThongBao();
		Collections.sort(thongbao);
		return thongbao;
	}
	@RequestMapping("index")
	public String index(HttpSession session, ModelMap model) {
		return "chutro/index";
	}
	@RequestMapping("nhatro/{id}")
	public String nhatro(ModelMap model, @PathVariable("id") int id, HttpSession session) {
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		model.addAttribute("nhatro", nhatro);
		return "chutro/nhatro";
	}
	@RequestMapping("nhatro/xoa/{id}")
	public String xoanhatro(RedirectAttributes re, HttpSession session, @PathVariable("id") int id) {
		if(session.getAttribute("role").equals("2")) {
			re.addFlashAttribute("error", "Bạn không có quyền thực hiện tính năng này");
		}
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		if(nhatro!=null) {
			session2.clear();
			session2 = factory.openSession();
			Transaction t = session2.beginTransaction();
			try {
				session2.delete(nhatro);
				t.commit();
				re.addFlashAttribute("success", "Đã xóa bài đăng số " + String.valueOf(id));
			} catch (Exception e) {
				t.rollback();
				re.addFlashAttribute("error", "Lỗi!" + e);
			}
		}
		else {
			re.addFlashAttribute("error", "Đây không phải bài viết của bạn! ");
		}
		return "redirect:../../index.htm";
	}
	@RequestMapping("nhatro/an/{id}")
	public String annhatro(RedirectAttributes re, HttpSession session, @PathVariable("id") int id) {
		if(session.getAttribute("role").equals("2")) {
			re.addFlashAttribute("error", "Bạn không có quyền thực hiện tính năng này");
		}
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		if(nhatro!=null) {
			session2.clear();
			session2 = factory.openSession();
			Transaction t = session2.beginTransaction();
			try {
				nhatro.setTinhtrang(0);
				session2.update(nhatro);
				t.commit();
				re.addFlashAttribute("success", "Đã ẩn bài đăng số " + String.valueOf(id));
			} catch (Exception e) {
				t.rollback();
				re.addFlashAttribute("error", "Lỗi!" + e);
			}
		} else {
			re.addFlashAttribute("error", "Đây không phải bài viết của bạn! ");
		}
		return "redirect:../"+id+".htm";
	}
	@RequestMapping(value="taobaidang", method = RequestMethod.GET)
	public String taobaidang(ModelMap model, HttpSession session) {
		model.addAttribute("featureid", 0);
		model.addAttribute("featurename", "taobaidang");
		return "chutro/them";
	}
	
	@RequestMapping(value="taobaidang", method=RequestMethod.POST)
	public String taobaidang(RedirectAttributes re, HttpSession session,
			@RequestParam("tieuDe") String tieuDe,
			@RequestParam("diachi") String diachi,
			@RequestParam("ward") int ward,
			@RequestParam("district") int district,
			@RequestParam("province") int province,
			@RequestParam("soPhongChoThue") int soPhongChoThue,
			@RequestParam("soNguoiTrenPhong") int soNguoiTrenPhong,
			@RequestParam("soPhongCoSan") int soPhongCoSan,
			@RequestParam("dienTich") float dienTich,
			@RequestParam("tienCoc") BigDecimal tienCoc,
			@RequestParam("tienThue") BigDecimal tienThue,
			@RequestParam("mota") String mota,
			@RequestParam("anh1") MultipartFile anh1,
			@RequestParam("anh2") MultipartFile anh2) {
		Session session2 = factory.getCurrentSession();
		tieuDe = tieuDe.trim();
		diachi = diachi.trim();
		//Kiểm tra String trống
		if(tieuDe.isBlank()) {
			re.addAttribute("error", "Tiêu đề trống!");
			return "redirect:taobaidang.htm";
		}
		if(diachi.isBlank()) {
			re.addAttribute("error", "Số nhà trống!");
			return "redirect:taobaidang.htm";
		}
		//Kiểm tra địa chỉ
		Ward w = (Ward) session2.get(Ward.class, ward);
		if(w==null) {
			re.addAttribute("error", "Sai địa chỉ!");
			return "redirect:taobaidang.htm";
		} else if(w.getDistrict().getId()!=district) {
			re.addAttribute("error", "Sai địa chỉ!");
			return "redirect:taobaidang.htm";
		} else if(w.getDistrict().getProvince().getId()!=province) {
			re.addAttribute("error", "Sai địa chỉ!");
			return "redirect:taobaidang.htm";
		} else {
			String hql = "FROM DiaChi WHERE diaChi='" + diachi+"'";
			List<Object> list = getList(hql);
			if(!list.isEmpty()) {
				re.addAttribute("error", "Trùng số nhà!");
				return "redirect:taobaidang.htm";
			}
		}
		//Kiểm tra số
		if(soPhongChoThue<0||soNguoiTrenPhong<0||soPhongCoSan<0||dienTich<0||tienCoc.compareTo(new BigDecimal(0))<0||tienThue.compareTo(new BigDecimal(0))<0) {
			re.addAttribute("error", "Không nhập số âm!");
			return "redirect:taobaidang.htm";
		}
		//Kiểm tra ảnh
		if(anh1.isEmpty()||anh2.isEmpty()) {
			re.addFlashAttribute("error", "Thiếu ảnh tiêu đề");
			return "redirect:taobaidang.htm";
		}
		Account account = (Account) session.getAttribute("account");
		if(account.getChuTro()!=null) {
			NhaTro nhatro = new NhaTro();
			nhatro.setChuTro(account.getChuTro());
			DiaChi diaChi = new DiaChi();
			diaChi.setDiaChi(diachi);
			diaChi.setWard(w);
			diaChi.setNhaTro(nhatro);
			nhatro.setDiachi(diaChi);
			nhatro.setTieuDe(tieuDe);
			nhatro.setDienTich(dienTich);
			nhatro.setNgayThem(new Date());
			nhatro.setSoNguoiTrenPhong(soNguoiTrenPhong);
			nhatro.setSoPhongChoThue(soPhongChoThue);
			nhatro.setSoPhongCoSan(soPhongCoSan);
			nhatro.setTienCoc(tienCoc);
			nhatro.setTienThue(tienThue);
			nhatro.setTinhtrang(0);
			nhatro.setMoTa(mota);
			session2.clear();
			session2 = factory.openSession();
			Transaction t = session2.beginTransaction();
			try {
				session2.save(diaChi);
				session2.save(nhatro);
				ThongBao thongbao = new ThongBao();
				thongbao.setAccount(account);
				thongbao.setThoigian(new Date());
				thongbao.setThongbao("Bài viết của bạn đã được lưu thành công! Admin sẽ nhanh chóng duyệt bài viết của bạn");
				thongbao.setLink("chutro/nhatro/" + nhatro.getId() + ".htm");
				session2.save(thongbao);
				t.commit();
				String path = context.getRealPath("/resources/images/nhatro/");
				anh1.transferTo(new File(path+nhatro.getId()+"_1.png"));
				anh2.transferTo(new File(path+nhatro.getId()+"_2.png"));
				re.addFlashAttribute("success", "Bài viết đã lưu thành công!");
			} catch (Exception e) {
				t.rollback();
				re.addFlashAttribute("error", "Lỗi! Bài viết của bạn chưa được lưu! Hãy kiểm tra lại thông tin và gửi lại! Nếu vẫn không được hãy báo với admin!");
				return "redirect:taobaidang.htm";
			} finally {
				session2.clear();
			}
		} else {
			re.addFlashAttribute("error","Bạn không có quyền truy cập hoặc đã hết phiên làm việc");
			return "redirect:../logout.htm";
		}
		return "redirect:index.htm";
	}	
	@RequestMapping(value="nhatro/doidiachi", method=RequestMethod.POST)
	public String doidiachi(HttpSession session, RedirectAttributes re,
			@RequestParam("id") int id,
			@RequestParam("diachi") String diachi,
			@RequestParam("ward") int ward,
			@RequestParam("district") int district,
			@RequestParam("province") int province) {
		
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		//Kiểm tra nhà trọ
		if(nhatro.getChuTro()==null||!nhatro.getChuTro().getAccount().getUsername().equals(session.getAttribute("username"))) {
			re.addFlashAttribute("error", "Bạn không có quyền thực hiện hành động này!");
			return "redirect:../../index.htm";
		}
		//Kiểm tra địa chỉ
		Ward w = (Ward) session2.get(Ward.class, ward);
		if(w==null) {
			re.addAttribute("error", "Sai địa chỉ!");
			return "redirect:" + id + ".htm";
		} else if(w.getDistrict().getId()!=district) {
			re.addAttribute("error", "Sai địa chỉ!");
			return "redirect:" + id + ".htm";
		} else if(w.getDistrict().getProvince().getId()!=province) {
			re.addAttribute("error", "Sai địa chỉ!");
			return "redirect:" + id + ".htm";
		}
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			nhatro.getDiachi().setDiaChi(diachi);
			nhatro.getDiachi().setWard(w);
			session2.update(nhatro);
			t.commit();
			re.addFlashAttribute("success", "Thay đổi thành công!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "Lỗi! Bài viết của bạn chưa được lưu! Hãy kiểm tra lại thông tin và gửi lại! Nếu vẫn không được hãy báo với admin!");
			return "redirect:" + id + ".htm";
		} finally {
			session2.clear();
		}
		return "redirect:" + id + ".htm";
	}
	@RequestMapping(value="nhatro/doidientich", method=RequestMethod.POST)
	public String doidientich(HttpSession session, RedirectAttributes re,
			@RequestParam("id") int id,
			@RequestParam("dienTich") float dienTich) {
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		//Kiểm tra nhà trọ
		if(nhatro.getChuTro()==null||!nhatro.getChuTro().getAccount().getUsername().equals(session.getAttribute("username"))) {
			re.addFlashAttribute("error", "Bạn không có quyền thực hiện hành động này!");
			return "redirect:../../index.htm";
		}
		//Kiểm tra diện tích
		if(dienTich<0) {
			re.addFlashAttribute("error", "Số nhập không hợp lệ");
			return "redirect:" + id + ".htm";
		}
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			nhatro.setDienTich(dienTich);
			session2.update(nhatro);
			t.commit();
			re.addFlashAttribute("success", "Thay đổi thành công!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "Lỗi! Bài viết của bạn chưa được lưu! Hãy kiểm tra lại thông tin và gửi lại! Nếu vẫn không được hãy báo với admin!");
			return "redirect:" + id + ".htm";
		} finally {
			session2.clear();
		}
		return "redirect:" + id + ".htm";
	}
	@RequestMapping(value="nhatro/doitiencoc", method=RequestMethod.POST)
	public String doitiencoc(HttpSession session, RedirectAttributes re,
			@RequestParam("id") int id,
			@RequestParam("tienCoc") BigDecimal tienCoc) {
		
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		//Kiểm tra nhà trọ
		if(nhatro.getChuTro()==null||!nhatro.getChuTro().getAccount().getUsername().equals(session.getAttribute("username"))) {
			re.addFlashAttribute("error", "Bạn không có quyền thực hiện hành động này!");
			return "redirect:../../index.htm";
		}
		//Kiểm tra tiền
		if(tienCoc.compareTo(BigDecimal.ZERO)<0) {
			re.addFlashAttribute("error", "Số nhập không hợp lệ");
			return "redirect:" + id + ".htm";
		}
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			nhatro.setTienCoc(tienCoc);
			session2.update(nhatro);
			t.commit();
			re.addFlashAttribute("success", "Thay đổi thành công!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "Lỗi! Bài viết của bạn chưa được lưu! Hãy kiểm tra lại thông tin và gửi lại! Nếu vẫn không được hãy báo với admin!");
			return "redirect:" + id + ".htm";
		} finally {
			session2.clear();
		}
		return "redirect:" + id + ".htm";
	}
	@RequestMapping(value="nhatro/doitienthue", method=RequestMethod.POST)
	public String doitienthue(HttpSession session, RedirectAttributes re,
			@RequestParam("id") int id,
			@RequestParam("tienThue") BigDecimal tienThue) {
		
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		//Kiểm tra nhà trọ
		if(nhatro.getChuTro()==null||!nhatro.getChuTro().getAccount().getUsername().equals(session.getAttribute("username"))) {
			re.addFlashAttribute("error", "Bạn không có quyền thực hiện hành động này!");
			return "redirect:../../index.htm";
		}
		//Kiểm tra tiền
		if(tienThue.compareTo(BigDecimal.ZERO)<0) {
			re.addFlashAttribute("error", "Số nhập không hợp lệ");
			return "redirect:" + id + ".htm";
		}
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			nhatro.setTienThue(tienThue);
			session2.update(nhatro);
			t.commit();
			re.addFlashAttribute("success", "Thay đổi thành công!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "Lỗi! Bài viết của bạn chưa được lưu! Hãy kiểm tra lại thông tin và gửi lại! Nếu vẫn không được hãy báo với admin!");
			return "redirect:" + id + ".htm";
		} finally {
			session2.clear();
		}
		return "redirect:" + id + ".htm";
	}
	@RequestMapping(value="nhatro/doisonguoitrenphong", method=RequestMethod.POST)
	public String doisonguoitrenphong(HttpSession session, RedirectAttributes re,
			@RequestParam("id") int id,
			@RequestParam("soNguoiTrenPhong") int soNguoiTrenPhong) {
		
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		//Kiểm tra nhà trọ
		if(nhatro.getChuTro()==null||!nhatro.getChuTro().getAccount().getUsername().equals(session.getAttribute("username"))) {
			re.addFlashAttribute("error", "Bạn không có quyền thực hiện hành động này!");
			return "redirect:../../index.htm";
		}
		//Kiểm tra số
		if(soNguoiTrenPhong<0) {
			re.addFlashAttribute("error", "Số nhập không hợp lệ");
			return "redirect:" + id + ".htm";
		}
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			nhatro.setSoNguoiTrenPhong(soNguoiTrenPhong);
			session2.update(nhatro);
			t.commit();
			re.addFlashAttribute("success", "Thay đổi thành công!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "Lỗi! Bài viết của bạn chưa được lưu! Hãy kiểm tra lại thông tin và gửi lại! Nếu vẫn không được hãy báo với admin!");
			return "redirect:" + id + ".htm";
		} finally {
			session2.clear();
		}
		return "redirect:" + id + ".htm";
	}
	@RequestMapping(value="nhatro/doisophongchothue", method=RequestMethod.POST)
	public String doisophongchothue(HttpSession session, RedirectAttributes re,
			@RequestParam("id") int id,
			@RequestParam("soPhongChoThue") int soPhongChoThue) {
		
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		//Kiểm tra nhà trọ
		if(nhatro.getChuTro()==null||!nhatro.getChuTro().getAccount().getUsername().equals(session.getAttribute("username"))) {
			re.addFlashAttribute("error", "Bạn không có quyền thực hiện hành động này!");
			return "redirect:../../index.htm";
		}
		//Kiểm tra số
		if(soPhongChoThue<0) {
			re.addFlashAttribute("error", "Số nhập không hợp lệ");
			return "redirect:" + id + ".htm";
		}
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			nhatro.setSoPhongChoThue(soPhongChoThue);
			session2.update(nhatro);
			t.commit();
			re.addFlashAttribute("success", "Thay đổi thành công!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "Lỗi! Bài viết của bạn chưa được lưu! Hãy kiểm tra lại thông tin và gửi lại! Nếu vẫn không được hãy báo với admin!");
			return "redirect:" + id + ".htm";
		} finally {
			session2.clear();
		}
		return "redirect:" + id + ".htm";
	}
	@RequestMapping(value="nhatro/doisophongcosan", method=RequestMethod.POST)
	public String doisophongcosan(HttpSession session, RedirectAttributes re,
			@RequestParam("id") int id,
			@RequestParam("soPhongCoSan") int soPhongCoSan) {
		
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		//Kiểm tra nhà trọ
		if(nhatro.getChuTro()==null||!nhatro.getChuTro().getAccount().getUsername().equals(session.getAttribute("username"))) {
			re.addFlashAttribute("error", "Bạn không có quyền thực hiện hành động này!");
			return "redirect:../../index.htm";
		}
		//Kiểm tra số
		if(soPhongCoSan<0) {
			re.addFlashAttribute("error", "Số nhập không hợp lệ");
			return "redirect:" + id + ".htm";
		}
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			nhatro.setSoPhongCoSan(soPhongCoSan);
			session2.update(nhatro);
			t.commit();
			re.addFlashAttribute("success", "Thay đổi thành công!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "Lỗi! Bài viết của bạn chưa được lưu! Hãy kiểm tra lại thông tin và gửi lại! Nếu vẫn không được hãy báo với admin!");
			return "redirect:" + id + ".htm";
		} finally {
			session2.clear();
		}
		return "redirect:" + id + ".htm";
	}
	
	@RequestMapping(value="nhatro/doitieude", method=RequestMethod.POST)
	public String doisophongcosan(HttpSession session, RedirectAttributes re,
			@RequestParam("id") int id,
			@RequestParam("tieuDe") String tieuDe) {
		
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		//Kiểm tra nhà trọ
		if(nhatro.getChuTro()==null||!nhatro.getChuTro().getAccount().getUsername().equals(session.getAttribute("username"))) {
			re.addFlashAttribute("error", "Bạn không có quyền thực hiện hành động này!");
			return "redirect:../../index.htm";
		}
		//Kiểm tra số
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			nhatro.setTieuDe(tieuDe);
			session2.update(nhatro);
			t.commit();
			re.addFlashAttribute("success", "Thay đổi thành công!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "Lỗi! Bài viết của bạn chưa được lưu! Hãy kiểm tra lại thông tin và gửi lại! Nếu vẫn không được hãy báo với admin!");
			return "redirect:" + id + ".htm";
		} finally {
			session2.clear();
		}
		return "redirect:" + id + ".htm";
	}
	
	@RequestMapping(value="nhatro/doimota", method=RequestMethod.POST)
	public String doimota(HttpSession session, RedirectAttributes re,
			@RequestParam("id") int id,
			@RequestParam("mota") String mota) {
		
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		//Kiểm tra nhà trọ
		if(nhatro.getChuTro()==null||!nhatro.getChuTro().getAccount().getUsername().equals(session.getAttribute("username"))) {
			re.addFlashAttribute("error", "Bạn không có quyền thực hiện hành động này!");
			return "redirect:../../index.htm";
		}
		//Kiểm tra mota
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			nhatro.setMoTa(mota);
			session2.update(nhatro);
			t.commit();
			re.addFlashAttribute("success", "Thay đổi thành công!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "Lỗi! Bài viết của bạn chưa được lưu! Hãy kiểm tra lại thông tin và gửi lại! Nếu vẫn không được hãy báo với admin!");
			return "redirect:" + id + ".htm";
		} finally {
			session2.clear();
		}
		return "redirect:" + id + ".htm";
	}
	@RequestMapping("lichhen")
	public String lichhen(HttpSession session, ModelMap model) {
		Session session2 = factory.getCurrentSession();
		Account account = (Account) session.getAttribute("account");
		session2.clear();
		String hql = "FROM LichHen WHERE ";
		int dem=0;
		for (NhaTro nhatro:account.getChuTro().getNhaTro()) {
			hql += "idnhatro=" + nhatro.getId();
			dem++;
			if(dem<account.getChuTro().getNhaTro().size()) hql+=" OR ";
		}
		hql += " ORDER BY thoigian DESC";
		List<Object> lichhens = getList(hql);
		model.addAttribute("lichhens", lichhens);
		return "chutro/lichhen";
	}
	
	@RequestMapping("lichhen/huy/{id}")
	public String huy(ModelMap model, HttpSession session, RedirectAttributes re, @PathVariable("id") int id) {
		Session session2 = factory.getCurrentSession();
		LichHen lichhen = (LichHen) session2.get(LichHen.class, id);
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			session2.delete(lichhen);
			ThongBao thongbao = new ThongBao();
			thongbao.setAccount(lichhen.getKhachThue().getAccount());
			thongbao.setThoigian(new Date());
			thongbao.setThongbao(session.getAttribute("username") + " đã hủy 1 lịch hẹn với bạn");
			thongbao.setLink("khachthue/lichhen.htm");
			session2.save(thongbao);
			t.commit();
			re.addFlashAttribute("success", "Đã xóa thành công!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "Không thể xóa lịch hẹn này! Hãy kiểm tra lại thông tin và gửi lại! Nếu vẫn không được hãy báo với admin!" );
		} finally {
			session2.close();
		}
		return "redirect:../../lichhen.htm";
	}
	@RequestMapping(value="lichhen/td", method=RequestMethod.POST)
	public String thaydoi(HttpSession session, RedirectAttributes re, 
			@RequestParam("id") int id,
			@RequestParam("thoigian") String thoigian) {
		Session session2 = factory.getCurrentSession();
		LichHen lichhen = (LichHen) session2.get(LichHen.class, id);
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			lichhen.setThoigian(new SimpleDateFormat("yyyy-MM-dd").parse(thoigian));
			ThongBao thongbao = new ThongBao();
			thongbao.setAccount(lichhen.getKhachThue().getAccount());
			thongbao.setThoigian(new Date());
			thongbao.setThongbao(session.getAttribute("username")+" đã thay đổi ngày hẹn! Hãy kiểm tra lại lịch hẹn");
			thongbao.setLink("khachthue/lichhen.htm");
			session2.update(lichhen);
			session2.save(thongbao);
			t.commit();
			re.addFlashAttribute("success", "Thay đổi đã được lưu! Hãy kiểm tra lại thông tin");
		} catch (Exception e) {
			re.addFlashAttribute("error", "Lỗi! Thông tin thay đổi chưa được lưu! Hãy kiểm tra lại thông tin và gửi lại! Nếu vẫn không được hãy báo với admin!");
			t.rollback();
		} finally {
			session2.clear();
		}
		return "redirect:../lichhen.htm";
	}
	@RequestMapping("lichhen/dongy/{id}")
	public String dongy(ModelMap model, HttpSession session, RedirectAttributes re, @PathVariable("id") int id) {
		Session session2 = factory.getCurrentSession();
		LichHen lichhen = (LichHen) session2.get(LichHen.class, id);
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			lichhen.setDongy(true);
			session2.update(lichhen);
			ThongBao thongbao = new ThongBao();
			thongbao.setAccount(lichhen.getKhachThue().getAccount());
			thongbao.setThoigian(new Date());
			thongbao.setThongbao(session.getAttribute("username") + " đã đồng y với 1 lịch hẹn với bạn");
			thongbao.setLink("khachthue/lichhen.htm");
			session2.save(thongbao);
			t.commit();
			re.addFlashAttribute("success", "Thay đổi đã được lưu!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "Lỗi! Thông tin thay đổi chưa được lưu! Hãy kiểm tra lại thông tin và gửi lại! Nếu vẫn không được hãy báo với admin!");
		} finally {
			session2.close();
		}
		return "redirect:../../lichhen.htm";
	}
	@RequestMapping("lichhen/kodongy/{id}")
	public String kodongy(ModelMap model, HttpSession session, RedirectAttributes re, @PathVariable("id") int id) {
		Session session2 = factory.getCurrentSession();
		LichHen lichhen = (LichHen) session2.get(LichHen.class, id);
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			lichhen.setDongy(false);
			session2.update(lichhen);
			ThongBao thongbao = new ThongBao();
			thongbao.setAccount(lichhen.getKhachThue().getAccount());
			thongbao.setThoigian(new Date());
			thongbao.setThongbao(session.getAttribute("username") + " đã hủy đồng ý với 1 lịch hẹn với bạn");
			thongbao.setLink("khachthue/lichhen.htm");
			session2.save(thongbao);
			t.commit();
			re.addFlashAttribute("success", "Thay đổi đã được lưu!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "Lỗi! Thông tin thay đổi chưa được lưu! Hãy kiểm tra lại thông tin và gửi lại! Nếu vẫn không được hãy báo với admin!");
		} finally {
			session2.close();
		}
		return "redirect:../../lichhen.htm";
	}
	@RequestMapping("lichhen/thanhcong/{id}")
	public String thanhcong(ModelMap model, HttpSession session, RedirectAttributes re, @PathVariable("id") int id) {
		Session session2 = factory.getCurrentSession();
		LichHen lichhen = (LichHen) session2.get(LichHen.class, id);
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			lichhen.setThanhcong(true);
			NhaTro nhatro = lichhen.getNhaTro();
			if(nhatro.getSoPhongCoSan()!=0) {
				nhatro.setSoPhongCoSan(nhatro.getSoPhongCoSan()-1);
				session2.update(nhatro);
			}
			session2.update(lichhen);
			ThongBao thongbao = new ThongBao();
			thongbao.setAccount(lichhen.getKhachThue().getAccount());
			thongbao.setThoigian(new Date());
			thongbao.setThongbao("Bạn đã thành công tìm được trọ. Xin chúc mừng! Hãy để lại đánh giá ở bài đăng");
			thongbao.setLink("khachthue/lichhen.htm");
			session2.save(thongbao);
			t.commit();
			re.addFlashAttribute("success", "Thay đổi đã được lưu!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "Lỗi! Thông tin thay đổi chưa được lưu! Hãy kiểm tra lại thông tin và gửi lại! Nếu vẫn không được hãy báo với admin!");
		} finally {
			session2.close();
		}
		return "redirect:../../lichhen.htm";
	}
	@RequestMapping("lichhen/kothanhcong/{id}")
	public String kothanhcong(ModelMap model, HttpSession session, RedirectAttributes re, @PathVariable("id") int id) {
		Session session2 = factory.getCurrentSession();
		LichHen lichhen = (LichHen) session2.get(LichHen.class, id);
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			lichhen.setThanhcong(false);
			NhaTro nhatro = lichhen.getNhaTro();
			nhatro.setSoPhongCoSan(nhatro.getSoPhongCoSan()+1);
			session2.update(lichhen);
			ThongBao thongbao = new ThongBao();
			thongbao.setAccount(lichhen.getKhachThue().getAccount());
			thongbao.setThoigian(new Date());
			thongbao.setThongbao(session.getAttribute("username") + " đã hủy thành công với 1 lịch hẹn với bạn");
			thongbao.setLink("khachthue/lichhen.htm");
			session2.save(thongbao);
			t.commit();
			re.addFlashAttribute("success", "Thay đổi đã được lưu!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "Lỗi! Thông tin thay đổi chưa được lưu! Hãy kiểm tra lại thông tin và gửi lại! Nếu vẫn không được hãy báo với admin!");
		} finally {
			session2.close();
		}
		return "redirect:../../lichhen.htm";
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
			if(datetemp.get(Calendar.MONTH)==0) row.add("12/" + String.valueOf(datetemp.get(Calendar.YEAR)));
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
	@RequestMapping(value="thongkes", params = {"begin","end"}, method=RequestMethod.GET)
	public String thongke(HttpSession session, ModelMap model,
			@RequestParam("begin") String begin, @RequestParam("end") String end) {
		try {
			Calendar dau = new GregorianCalendar(Integer.parseInt(begin.split("-")[0]), Integer.parseInt(begin.split("-")[1]), Integer.parseInt(begin.split("-")[2]));
			Calendar cuoi = new GregorianCalendar(Integer.parseInt(end.split("-")[0]), Integer.parseInt(end.split("-")[1]), Integer.parseInt(end.split("-")[2]));
			Calendar dau1 = new GregorianCalendar(Integer.parseInt(begin.split("-")[0]), Integer.parseInt(begin.split("-")[1]), Integer.parseInt(begin.split("-")[2]));
			Calendar cuoi1 = new GregorianCalendar(Integer.parseInt(end.split("-")[0]), Integer.parseInt(end.split("-")[1]), Integer.parseInt(end.split("-")[2]));
			//kt ngày trc có > ngày sau ko
			if (dau.equals(cuoi)||dau.after(cuoi)) throw new ParseException(end, 0);			
			model.addAttribute("data", thongkediem((Account) session.getAttribute("account"), dau, cuoi));		
			model.addAttribute("pie", thongketinhtrang((Account) session.getAttribute("account"), dau1, cuoi1));
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			model.addAttribute("error", "Ngày không hợp lệ!");
		} catch (Exception e) {
			model.addAttribute("error", "Lỗi");
		} finally {
			model.addAttribute("begin", begin);
			model.addAttribute("end", end);
			return "chutro/thongke";
		}
	}
	@RequestMapping(value="thongke", method = RequestMethod.GET)
	public String thongke() {
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		return "redirect:thongkes.htm?begin=2021-01-01&end="+date;
	}
}
