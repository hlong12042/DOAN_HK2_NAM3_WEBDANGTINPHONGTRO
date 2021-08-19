package ptithcm.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.mail.javamail.JavaMailSender;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ptithcm.entity.*;

import ptithcm.service.ProvinceService;

@Transactional
@Controller
@RequestMapping("/khachthue/")
public class KhachThueController {
	@Autowired
	SessionFactory factory;

	@Autowired
	JavaMailSender mailer;

	@Autowired
	ServletContext context;

	List<Object> nhatros;
	Province province;
	District district;
	Ward ward;
//	//Sắp xếp theo điểm đánh giá
//	public void sort(){
//		for(int i=0; i<this.nhatros.size()-1;i++) {
//			NhaTro ntmax = (NhaTro) this.nhatros.get(i);//nhà trọ vị trí max
//			int max=i;//vị trí
//			for(int j=this.nhatros.size()-1; j>i; j--) {
//				NhaTro nhatroj = (NhaTro) this.nhatros.get(j);
//				if(ntmax.getDiem()<nhatroj.getDiem()) {
//					max = j;
//					ntmax=nhatroj;
//				}
//			}
//			NhaTro temp = (NhaTro) this.nhatros.get(i);
//			this.nhatros.set(i, ntmax);
//			this.nhatros.set(max, temp);
//		}
//	}
//	//Sắp xếp theo điểm phù hợp
//	public void sort(KhachThue khachthue){
//		for(int i=0; i<this.nhatros.size()-1;i++) {
//			NhaTro ntmax = (NhaTro) this.nhatros.get(i);//nhà trọ vị trí max
//			int max=i;//vị trí
//			for(int j=this.nhatros.size()-1; j>i; j--) {
//				NhaTro nhatroj = (NhaTro) nhatros.get(j);
//				if(ntmax.getDiem2(khachthue)<nhatroj.getDiem2(khachthue)) {
//					max=j;
//					ntmax=nhatroj;
//				}
//			}
//			NhaTro temp = (NhaTro) this.nhatros.get(i);
//			this.nhatros.set(i, ntmax);
//			this.nhatros.set(max, temp);
//		}
//	}
	public int partition1(int low, int high) {
		NhaTro pivot = (NhaTro) nhatros.get(high);
		int left = low, right = high-1;
		NhaTro temp;
		while (true) {
			while (left<=right) {
				temp = (NhaTro) nhatros.get(left);
				if (temp.getDiem()>pivot.getDiem()) break; 
				left++;
			}
			while (right>=left) {
				temp = (NhaTro) nhatros.get(right);
				if (temp.getDiem()<pivot.getDiem()) break; 
				right--;
			}
			if(left>=right) break;
			temp = (NhaTro) nhatros.get(left);
			nhatros.set(left, nhatros.get(right));
			nhatros.set(right, temp);
			left++; right--;
		}
		temp = (NhaTro) nhatros.get(left);
		nhatros.set(left, nhatros.get(high));
		nhatros.set(high, temp);
		return left;
	}
	public int partition2(int low, int high, HttpSession session) {
		NhaTro pivot = (NhaTro) nhatros.get(high);
		int left = low, right = high-1;
		NhaTro temp;
		Account account = (Account) session.getAttribute("account");
		while (true) {
			while (left<=right) {
				temp = (NhaTro) nhatros.get(left);
				if (temp.getDiem2(account.getKhachThue())>pivot.getDiem2(account.getKhachThue())) break; 
				left++;
			}
			while (right>=left) {
				temp = (NhaTro) nhatros.get(right);
				if (temp.getDiem2(account.getKhachThue())<pivot.getDiem2(account.getKhachThue())) break; 
				right--;
			}
			if(left>=right) break;
			temp = (NhaTro) nhatros.get(left);
			nhatros.set(left, nhatros.get(right));
			nhatros.set(right, temp);
			left++; right--;
		}
		temp = (NhaTro) nhatros.get(left);
		nhatros.set(left, nhatros.get(high));
		nhatros.set(high, temp);
		return left;
	}
	public void quicksort(int low, int high, int chedo, HttpSession session) {
		if(low<high) {
			int pi;
			if (chedo==1) pi = partition1(low, high);
			else pi = partition2(low, high, session);
			quicksort(low, pi-1, chedo, session);
			quicksort(pi+1, high, chedo, session);
		}
	}
	List<Object> getList(String hql) {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery(hql);
		List<Object> list = query.list();
		return list;
	}
	//Kiểm tra kí tự đặc biệt
	public static boolean isContainSpecialWord(String str) {
		Pattern VALID_INPUT_REGEX = Pattern.compile("[$&+,:;=\\\\\\\\?@#|/'<>.^*()%!-]", Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_INPUT_REGEX.matcher(str);
		return matcher.find();
	}
	//Đổ địa chỉ bằng httpsession
	@ModelAttribute("provinces")
	public Object getProvinces(HttpSession session) {
		return session.getAttribute("provinces");
	}
	@ModelAttribute("ques")
	public Object getQues(HttpSession session) {
		return session.getAttribute("provinces");
	}
	// Đổ thông tin account từ session
	@ModelAttribute("user")
	public Account user(HttpSession session) {
		return (Account) session.getAttribute("account");
	}
	// Đổ thông tin thông báo từ session
	@ModelAttribute("thongbaos")
	public <T> Collection<ThongBao> getThongBao(HttpSession session, ModelMap model) {
		Account account = (Account) session.getAttribute("account");
		String tbnhapthongtin = "Hãy nhập thông tin thêm khách hàng để sử dụng một số dịch vụ đặc biệt của trang web";
		String tblichhen = "Hôm nay bạn có một buổi hẹn xem phòng!";
		boolean co=false;
		for (ThongBao tb : account.getThongBao()) {
			if (tb.getThongbao().equals(tbnhapthongtin)) {
				co=true;
				break;
			}
		}
		if (!co) {
			ThongBao thongbao = new ThongBao();
			thongbao.setAccount(account);
			thongbao.setThoigian(new Date());
			thongbao.setThongbao(tbnhapthongtin);
			thongbao.setLink("/khachthue/thongtinthem.htm");
			Session session2 = factory.openSession();
			Transaction t = session2.beginTransaction();
			try {
				session2.save(thongbao);
				t.commit();
			} catch (Exception e) {
				t.rollback();
				model.addAttribute("error", "Lỗi!" + e);
			} finally {
				session2.clear();
			}
		}
		for (LichHen lichhen : account.getKhachThue().getLichHen()) {
			if (lichhen.getThoigian().equals(new Date())) {
				ThongBao thongbao = new ThongBao();
				thongbao.setAccount(account);
				thongbao.setThoigian(new Date());
				thongbao.setThongbao(tblichhen);
				thongbao.setLink("/khachthue/lichhen.htm");
				Session session2 = factory.openSession();
				Transaction t = session2.beginTransaction();
				try {
					session2.save(thongbao);
					t.commit();
				} catch (Exception e) {
					t.rollback();
					model.addAttribute("error", "Lỗi!" + e);
				} finally {
					session2.clear();
				}
				break;
			}
		}
		List<ThongBao> thongbao = (List<ThongBao>) account.getThongBao();
		Collections.sort(thongbao);
		return thongbao;
	}
	@RequestMapping("")
	public String welcome() {
		return "redirect:index.htm";
	}
	@RequestMapping(value = "index")
	public String index(ModelMap model, HttpSession session) {
		String hql = "FROM NhaTro " + "WHERE tinhtrang=1 ";
		this.nhatros = getList(hql);
		if (this.nhatros.isEmpty()) model.addAttribute("error", "Không tìm thấy trang !");
		else quicksort(0, nhatros.size()-1, 1, session);
		model.addAttribute("nhatros", this.nhatros);
		model.addAttribute("page", 1);
		this.province = null;
		this.district = null;
		this.ward = null;
		model.addAttribute("feature", "index");
		return "khachthue/index";
	}
	@RequestMapping(value = "timkiem", params = { "province", "district", "ward" })
	public String timkiem(ModelMap model, 
			@RequestParam("province") String p, @RequestParam("district") String d,
			@RequestParam("ward") String w, HttpSession session) {	
		try {
			String hql = "FROM NhaTro " + "WHERE tinhtrang = 1 "; //Lấy danh sách nhà trọ đã được duyệt
			this.nhatros = getList(hql);
			int ward = Integer.parseInt(w);
			if (ward != 0) {
				for (int i=0; i<this.nhatros.size();) {
					NhaTro nt = (NhaTro) this.nhatros.get(i);
					if(nt.getWardId()!=ward) {
						this.nhatros.remove(i);
					} else i++;
				}
			}
			if (this.nhatros.isEmpty()) model.addAttribute("error", "Không tìm thấy bài đăng !");
			else quicksort(0, nhatros.size()-1, 1, session);
			Session session2 = factory.getCurrentSession();
			this.ward = (Ward) session2.get(Ward.class, ward);
			this.district = this.ward.getDistrict();
			this.province = this.district.getProvince();
			model.addAttribute("nhatros", this.nhatros);
			model.addAttribute("page", 1);
			model.addAttribute("province", this.province);
			model.addAttribute("district", this.district);
			model.addAttribute("ward", this.ward);
			model.addAttribute("feature", "timkiem");
		} catch (Exception e) {
			model.addAttribute("error", "Tìm kiếm thất bại!");
		}
		return "khachthue/index";
	}
	//Lọc thiếu ward
	@RequestMapping(value = "timkiem", params = { "province", "district" })
	public String timkiem(ModelMap model, @RequestParam("province") String p, 
			@RequestParam("district") String d, HttpSession session) {
		try {
			String hql = "FROM NhaTro " + "WHERE tinhtrang = 1 ";
			this.nhatros = getList(hql);
			int district = Integer.parseInt(d);
			if (district != 0) {
				for (int i=0; i<this.nhatros.size();) {
					NhaTro nt = (NhaTro) this.nhatros.get(i);
					if(nt.getDistrictId()!=district) {
						this.nhatros.remove(i);
					} else i++;
				}
			}
			if (this.nhatros.isEmpty()) model.addAttribute("error", "Không tìm thấy bài đăng !");
			else quicksort(0, nhatros.size()-1, 1, session);
			Session session2 = factory.getCurrentSession();
			this.ward = null;
			this.district = (District) session2.get(District.class, district);
			this.province = this.district.getProvince();		
			model.addAttribute("nhatros", this.nhatros);
			model.addAttribute("page", 1);
			model.addAttribute("province", this.province);
			model.addAttribute("district", this.district);
			model.addAttribute("ward", this.ward);
			model.addAttribute("feature", "timkiem");
		} catch (Exception e) {
			model.addAttribute("error", "Tìm kiếm thất bại!");
		}
		return "khachthue/index";
	}
	//Lọc thiếu district
	@RequestMapping(value = "timkiem", params = { "province" })
	public String timkiem(ModelMap model, @RequestParam("province") String p,
			HttpSession session) {
		try {
			String hql = "FROM NhaTro " + "WHERE tinhtrang = 1 ";
			this.nhatros = getList(hql);
			int province = Integer.parseInt(p);
			if (province != 0) {
				for (int i=0; i<this.nhatros.size();) {
					NhaTro nt = (NhaTro) this.nhatros.get(i);
					if(nt.getProvinceId()!=province) {
						this.nhatros.remove(i);
					} else i++;
				}
			}
			if (this.nhatros.isEmpty()) model.addAttribute("error", "Không tìm thấy bài đăng !");
			else quicksort(0, nhatros.size()-1, 1, session);
			Session session2 = factory.getCurrentSession();
			this.province = (Province) session2.get(Province.class, province);
			this.district = null;
			this.ward = null;
			model.addAttribute("nhatros", this.nhatros);
			model.addAttribute("page", 1);
			model.addAttribute("province", this.province);
			model.addAttribute("district", this.district);
			model.addAttribute("ward", this.ward);
			model.addAttribute("feature", "timkiem");
		} catch (Exception e) {
			model.addAttribute("error", "Tìm kiếm thất bại!");
		}
		return "khachthue/index";
	}

	@RequestMapping(value = "timkiem")
	public String timkiem() {
		return "redirect:index.htm";
	}

	@RequestMapping("nhatro/{id}")
	public String nhatro(ModelMap model, @PathVariable("id") int id, HttpSession session) {
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		model.addAttribute("nhatro", nhatro);
		LichHen lichhen = new LichHen();
		model.addAttribute("lichhen", lichhen);
		return "khachthue/nhatro";
	}

	@RequestMapping(value = "timkiem", params = { "page" })
	public String page(ModelMap model, @RequestParam("page") int page) {
		if (this.nhatros.size() + 10 < page * 10) {
			model.addAttribute("error", "Không tìm thấy trang !");
		}
		model.addAttribute("nhatros", this.nhatros);
		model.addAttribute("page", page);
		return "khachthue/index";
	}

	@RequestMapping(value = "loc", method = RequestMethod.POST)
	public String loc(ModelMap model, 
			@RequestParam("diem") String d, @RequestParam("soluot") String sl,
			@RequestParam("songuoi") String sn, @RequestParam("giathue") String gt, 
			RedirectAttributes re) {
		try {
			if (!d.isEmpty()) {
				float diem = Float.parseFloat(d);
				for (int i=0; i<this.nhatros.size();) {
					NhaTro nt = (NhaTro) this.nhatros.get(i);
					if(nt.getDiem()<diem) {
						this.nhatros.remove(i);
					} else i++;
				}
			}
			if (!sl.isEmpty()) {
				int soluot = Integer.parseInt(sl);
				for (int i=0; i<this.nhatros.size();) {
					NhaTro nt = (NhaTro) this.nhatros.get(i);
					if(nt.getSoLuot()<soluot) {
						this.nhatros.remove(i);
					} else i++;
				}
			}
			if (!sn.isEmpty()) {
				int songuoi = Integer.parseInt(sn);
				for (int i=0; i<this.nhatros.size();) {
					NhaTro nt = (NhaTro) this.nhatros.get(i);
					if(nt.getSoNguoiTrenPhong()<songuoi) {
						this.nhatros.remove(i);
					} else i++;
				}
			}
			if(!gt.isEmpty()&&!gt.equals("0")) {
				BigDecimal giathue = new BigDecimal(gt);
				for (int i=0; i<this.nhatros.size();) {
					NhaTro nt = (NhaTro) this.nhatros.get(i);
					if(nt.getTienThue().compareTo(giathue)>0) {
						this.nhatros.remove(i);
					} else i++;
				}
			}
			if (this.nhatros.isEmpty()) model.addAttribute("error", "Không tìm thấy trang !");
			model.addAttribute("nhatros", this.nhatros);
			model.addAttribute("page", 1);
		} catch (Exception e) {
			re.addFlashAttribute("message", "Không thể lọc ! " + e);
			return "redirect:index.htm";
		}
		return "khachthue/index";
	}

	@RequestMapping("tudong")
	public String tudong(ModelMap model, HttpSession session, RedirectAttributes re) {
		if (this.nhatros.isEmpty()) model.addAttribute("error", "Không tìm thấy trang !");
		else quicksort(0, nhatros.size()-1, 2, session);
		model.addAttribute("nhatros", this.nhatros);
		model.addAttribute("page", 1);
		this.province = null;
		this.district = null;
		this.ward = null;
		model.addAttribute("feature", "index");
		return "khachthue/index";
	}

	@RequestMapping(value="datlich", method=RequestMethod.POST)
	public String datlich(ModelMap model, 
			@RequestParam("thoigian") String thoigian,
			@RequestParam("id") int id,
			HttpSession session, RedirectAttributes re) {
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		Account account = (Account) session.getAttribute("account");
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			LichHen lichhen = new LichHen();
			lichhen.setThoigian(new SimpleDateFormat("yyyy-MM-dd").parse(thoigian));
			if (lichhen.getThoigian() == null || lichhen.getThoigian().before(new Date())
					|| lichhen.getThoigian().before(new Date())) {
				session2.clear();
				re.addFlashAttribute("dlerror", "Vui lòng chọn ngày phù hợp !");
				return "redirect:nhatro/" + id + ".htm";
			}
			lichhen.setNhaTro(nhatro);
			lichhen.setKhachThue(account.getKhachThue());
			lichhen.setDongy(false);
			lichhen.setThanhcong(false);
			session2.save(lichhen);
			ThongBao thongbao = new ThongBao();
			thongbao.setAccount(lichhen.getNhaTro().getChuTro().getAccount());
			thongbao.setThoigian(new Date());
			thongbao.setThongbao(session.getAttribute("username") + " đã tạo 1 lịch hẹn với bạn");
			thongbao.setLink("chutro/lichhen");
			session2.save(thongbao);
			t.commit();
			re.addFlashAttribute("dlsuccess", "Thêm lịch hẹn thành công!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("dlerror", "Thêm lịch hẹn thất bại!");
		} finally {
			session2.close();
		}
		return "redirect:nhatro/" + id + ".htm";
	}
	//Phần code thay đổi tương tự như thay đổi thông tin cá nhân
	@RequestMapping(value = "lichhen", method = RequestMethod.GET)
	public String lichhen(ModelMap model, HttpSession session) {
		Account account = (Account) session.getAttribute("account");
		String hql = "FROM LichHen WHERE idkhachthue=" + 
				account.getKhachThue().getId() + 
				" ORDER BY thoigian DESC";
		List<Object> lichhens = getList(hql);
		model.addAttribute("lichhens", lichhens);
		return "khachthue/lichhen";
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
			thongbao.setAccount(lichhen.getNhaTro().getChuTro().getAccount());
			thongbao.setThoigian(new Date());
			thongbao.setThongbao(session.getAttribute("username") + " đã hủy 1 lịch hẹn với bạn");
			thongbao.setLink("chutro/lichhen");
			session2.save(thongbao);
			t.commit();
			re.addFlashAttribute("success", "Đã xóa thành công!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "Không thể xóa lịch hẹn này! " + e);
		} finally {
			session2.close();
		}
		return "redirect:../../lichhen.htm";
	}

	@RequestMapping(value = "thongtinthem", method = RequestMethod.GET)
	public String thongtinthem(ModelMap model, HttpSession session) {
		return "khachthue/thongtinthem";
	}

	@RequestMapping(value = "thongtinthem", method = RequestMethod.POST)
	public String themthongtin(HttpSession session, RedirectAttributes re,
			@RequestParam("truong") int idtruong,
			@RequestParam("province") int idprovince, @RequestParam("que") String que,
			@RequestParam("namsinh") int namsinh, @RequestParam("gioitinh") int gioitinh) {
		Session session2 = factory.getCurrentSession();
		Account account = (Account) session.getAttribute("account");
		try {
			Province province = (Province) session2.get(Province.class, idprovince);
			List<Truong> truongs = (List<Truong>) province.getTruongs();
			boolean co=false;
			Truong truong = new Truong();
			for (Truong tr : truongs) 
				if(tr.getId()==idtruong) {
					co=true; truong=tr;
					break;	
				}
			if(!co) {
				re.addFlashAttribute("error", "Không tìm thấy trường bạn chọn");
				return "redirect:thongtinthem.htm";
			}
			account.getKhachThue().setQueQuan(que);
			account.getKhachThue().setNamSinh(namsinh);
			account.getKhachThue().setGioiTinh(gioitinh==1? true : false);
			account.getKhachThue().setTruong(truong);
			ThongBao thongbao = new ThongBao();
			thongbao.setAccount(account);
			thongbao.setThoigian(new Date());
			thongbao.setThongbao("Đã cập nhật thành công thông tin thêm của bạn");
			thongbao.setLink("/khachthue/thongtinthem.htm");
			session2.clear();
			session2 = factory.openSession();
			Transaction t = session2.beginTransaction();
			try {
				session2.save(thongbao);
				session2.update(account);
				t.commit();
			} catch (Exception e) {
				t.rollback();
				re.addFlashAttribute("error", "Lỗi không thể lưu thay đổi! "
						+ "Hãy báo lỗi với admin");
			} finally {
				session2.close();
			}
		} catch (Exception e) {
			re.addFlashAttribute("error", "Lỗi hệ thống! Hãy báo lỗi với admin");
			return "redirect:thongtinthem.htm";
		}
		return "redirect:thongtinthem.htm";
	}

	@RequestMapping(value = "nhatro/comment", method = RequestMethod.POST)
	public String comment(RedirectAttributes re, HttpSession session, @RequestParam("id") int id,
			@RequestParam("diem") float diem, @RequestParam("comment") String comment) {
		comment = comment.trim();
		if (diem < 0 || 5 < diem) {
			re.addFlashAttribute("error", "Điểm không hợp lệ!");
		}
		if (comment.isBlank()||isContainSpecialWord(comment)) {
			re.addFlashAttribute("error", "Comment không hợp lệ!");
		} else {
			comment=comment.replaceAll("\r\n", "<br>");
			Session session2 = factory.getCurrentSession();
			Account account = (Account) session2.get(Account.class, (String) session.getAttribute("username"));
			for (LichHen lichhen : account.getKhachThue().getLichHen()) {
				if (lichhen.getNhaTro().getId() == id && lichhen.getDongy()) {
					Comment c = new Comment();
					c.setDiem(diem);
					c.setComment(comment);
					c.setKhachthue(account.getKhachThue());
					c.setNhatro(lichhen.getNhaTro());
					c.setThoigian(Calendar.getInstance());
					session2.clear();
					session2 = factory.openSession();
					Transaction t = session2.beginTransaction();
					try {
						session2.save(c);
						t.commit();
					} catch (Exception e) {
						t.rollback();
						re.addFlashAttribute("error", "Lỗi! " + e);
					} finally {
						session2.close();
					}
					return "redirect:" + String.valueOf(id) + ".htm";
				}
			}
			re.addFlashAttribute("error",
					"Dữ liệu ghi nhận bạn chưa xem qua nhà trọ này. Bạn không có quyền thực hiện đánh giá");
		}
		return "redirect:" + String.valueOf(id) + ".htm";
	}
	@RequestMapping(value="nhatro/doicomment", method=RequestMethod.POST)
	public String doicomment(HttpSession session, RedirectAttributes re,
			@RequestParam("id") int id,
			@RequestParam("idc") int idc,
			@RequestParam("diem") float diem,
			@RequestParam("comment") String comment) {
		comment=comment.trim();
		if(!comment.isBlank()) {
			comment=comment.replaceAll("\r\n", "<br>");
		}
		Session session2 = factory.getCurrentSession();
		Comment c = (Comment) session2.get(Comment.class, idc);
		session2.clear();
		if(c.getNhatro().getId()==id) {
			session2 = factory.openSession();
			Transaction t = session2.beginTransaction();
			try {
				c.setComment(comment);
				c.setDiem(diem);
				session2.update(c);
				t.commit();
				re.addFlashAttribute("success", "Thay đổi của bạn đã được lưu");
			} catch (Exception e) {
				t.rollback();
				re.addFlashAttribute("error", "Lỗi server! Hãy báo lỗi với admin");
			} finally {
				session2.clear();
			}
		} else {
			re.addFlashAttribute("error", "Không tìm thấy nhà trọ");
		}
		return "redirect:" + String.valueOf(id) + ".htm";
	}
	@RequestMapping(value="nhatro/xoacomment/{id}", method = RequestMethod.GET)
	public String xoacomment(HttpSession session, RedirectAttributes re,
			@PathVariable("id") int id) {
		Session session2 = factory.getCurrentSession();
		Comment comment = (Comment) session2.get(Comment.class, id);
		Account account = (Account) session.getAttribute("account");
		int idnhatro = comment.getNhatro().getId();
		session2.clear();
		if(account.getKhachThue().getId()==comment.getKhachthue().getId()) {		
			session2 = factory.openSession();
			Transaction t = session2.beginTransaction();
			try {
				session2.delete(comment);
				t.commit();
				re.addFlashAttribute("success", "Đã xóa thành công");
			} catch (Exception e) {
				t.rollback();
				re.addFlashAttribute("error", "Lỗi server");
			} finally {
				session2.clear();
			}
		} else {
			re.addFlashAttribute("error", "Bạn không có quyền này");
		}
		return "redirect:../" + String.valueOf(idnhatro) + ".htm";
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
			thongbao.setAccount(lichhen.getNhaTro().getChuTro().getAccount());
			thongbao.setThoigian(new Date());
			thongbao.setThongbao(lichhen.getKhachThue().getAccount().getUsername()+" đã thay đổi ngày hẹn! Hãy kiểm tra lại lịch hẹn");
			thongbao.setLink("chutro/lichhen.htm");
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
}
