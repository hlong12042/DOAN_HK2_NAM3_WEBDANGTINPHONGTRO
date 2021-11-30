package ptithcm.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ptithcm.entity.District;
import ptithcm.entity.LichHen;
import ptithcm.entity.NhaTro;
import ptithcm.entity.Province;
import ptithcm.entity.Ward;
import ptithcm.service.ProvinceService;

@Transactional
@Controller
public class HomeController {
	@Autowired
	SessionFactory factory;
	@Autowired
	ServletContext context;
	
	List<Object> nhatros;
	Province province;
	District district;
	Ward ward;
	
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
	
	public void quicksort(int low, int high) {
		if(low<high) {
			int pi = partition1(low, high);
			quicksort(low, pi-1);
			quicksort(pi+1, high);
			
		}
	}
	
	@RequestMapping("")
	public String welcome(HttpSession session) {
		return "redirect:index.htm";
	}
	
	@SuppressWarnings("unchecked")
	@ModelAttribute("provinces")
	public List<Province> getProvinces(HttpSession session){
		if (session.getAttribute("provinces")==null) {
			session.setAttribute("provinces", ProvinceService.findAll(factory));
		}
		return (List<Province>) session.getAttribute("provinces");
	}
		
	@SuppressWarnings("unchecked")
	List<Object> getList(String hql) {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery(hql);
		List<Object> list = query.list();
		return list;
	}
	@RequestMapping("index")
	public String index(ModelMap model, HttpSession httpSession) {
		String hql="FROM NhaTro "
				+ "WHERE tinhtrang = 1 ";
		this.nhatros = getList(hql);
		if(this.nhatros.isEmpty()) model.addAttribute("error", "Không tìm thấy trang !");
		else quicksort(0, nhatros.size()-1);
		model.addAttribute("nhatros", this.nhatros);
		model.addAttribute("page", 1);
		this.province = null;
		this.district = null;
		this.ward = null;
		model.addAttribute("feature","index");
		return "index";
	}
	@RequestMapping(value="timkiem", params = {"province","district","ward"})
	public String timkiem(ModelMap model, 
			@RequestParam("province") String p, 
			@RequestParam("district") String d, 
			@RequestParam("ward") String w) {
		String hql = "FROM NhaTro "
				+ "WHERE tinhtrang = 1 ";
		this.nhatros = getList(hql);
		try {
			int province = Integer.parseInt(p);
			int district = Integer.parseInt(d);
			int ward = Integer.parseInt(w);
			if(ward!=0) {
				for (int i=0; i<this.nhatros.size();) {
					NhaTro nt = (NhaTro) this.nhatros.get(i);
					if(nt.getWardId()!=ward) {
						this.nhatros.remove(i);
					} else i++;
				}
			}
			if(this.nhatros.isEmpty()) {
				model.addAttribute("error", "Không tìm thấy trang !");
			} else {
			quicksort(0, nhatros.size()-1);
			Session session = factory.getCurrentSession();
			this.province = (Province) session.get(Province.class, province);
			this.district = (District) session.get(District.class, district);
			this.ward = (Ward) session.get(Ward.class, ward);
			model.addAttribute("nhatros", this.nhatros);
			model.addAttribute("page", 1);
			model.addAttribute("province", this.province);
			model.addAttribute("district", this.district);
			model.addAttribute("ward", this.ward);
			model.addAttribute("feature","timkiem");
		}
		} catch (Exception e) {
			model.addAttribute("message","Tìm kiếm thất bại!");
		}
		return "index";
	}
		
	@RequestMapping(value="timkiem", params = {"province","district"})
	public String timkiem(ModelMap model,
			@RequestParam("province") String p, 
			@RequestParam("district") String d) {
		String hql = "FROM NhaTro "
				+ "WHERE tinhtrang = 1 ";
		this.nhatros = getList(hql);
		try {
			int province = Integer.parseInt(p);
			int district = Integer.parseInt(d);
			if(district!=0) {
				for (int i=0; i<this.nhatros.size();) {
					NhaTro nt = (NhaTro) this.nhatros.get(i);
					if(nt.getDistrictId()!=district) {
						this.nhatros.remove(i);
					} else i++;
				}
			}
			if(this.nhatros.isEmpty()) {
				model.addAttribute("error", "Không tìm thấy trang !");
			}else {
				quicksort(0, nhatros.size()-1);
				Session session = factory.getCurrentSession();
				this.province = (Province) session.get(Province.class, province);
				this.district = (District) session.get(District.class, district);
				this.ward = null;
				model.addAttribute("nhatros", this.nhatros);
				model.addAttribute("page", 1);
				model.addAttribute("province", this.province);
				model.addAttribute("district", this.district);
				model.addAttribute("ward", this.ward);
				model.addAttribute("feature","timkiem");
			}
		} catch(Exception e) {
			model.addAttribute("message", "Tìm kiếm thất bại!");
		}
		return "index";
	}
		
	
	@RequestMapping(value="timkiem", params = {"province"})
	public String timkiem(ModelMap model,
			@RequestParam("province") String p) {
		String hql = "FROM NhaTro "
				+ "WHERE tinhtrang = 1 ";
		this.nhatros = getList(hql);
		try {
			int province = Integer.parseInt(p);
			if(province!=0) {
				for (int i=0; i<this.nhatros.size();) {
					NhaTro nt = (NhaTro) this.nhatros.get(i);
					if(nt.getProvinceId()!=province) {
						this.nhatros.remove(i);
					} else i++;
				}
			}
			if(this.nhatros.isEmpty()) {
				model.addAttribute("error", "Không tìm thấy trang !");
			}else {
				quicksort(0, nhatros.size()-1);
				Session session = factory.getCurrentSession();
				this.province = (Province) session.get(Province.class, province);
				this.district = null;
				this.ward = null;
				model.addAttribute("nhatros", this.nhatros);
				model.addAttribute("page", 1);
				model.addAttribute("province", this.province);
				model.addAttribute("district", this.district);
				model.addAttribute("ward", this.ward);
				model.addAttribute("feature","timkiem");
			}
			} catch (Exception e) {
				model.addAttribute("message", "Tìm kiếm thất bại!");
			}
		return "index";
	}
	@RequestMapping(value="timkiem")
	public String timkiem() {
		return "redirect:index.htm";
	}
	@RequestMapping("nhatro/{id}")
	public String nhatro(ModelMap model, @PathVariable("id") int id, HttpSession session) {
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		model.addAttribute("nhatro",nhatro);
		LichHen lichhen = new LichHen();
		model.addAttribute("lichhen", lichhen);
		return "nhatro";
	}
	@RequestMapping(value="timkiem",params={"page"})
	public String page(ModelMap model, @RequestParam("page") int page) {
		model.addAttribute("nhatros", this.nhatros);
		model.addAttribute("page", page);
		return "index";
	}
	@RequestMapping(value="loc", method=RequestMethod.POST)
	public String loc(ModelMap model, 
			@RequestParam("diem") String d, @RequestParam("soluot") String sl,
			@RequestParam("songuoi") String sn, @RequestParam("giathue") String gt,
			RedirectAttributes re) {
		try {
			if(!d.isEmpty()) {
				float diem = Float.parseFloat(d);
				for (int i=0; i<this.nhatros.size();) {
					NhaTro nt = (NhaTro) this.nhatros.get(i);
					if(nt.getDiem()<diem) {
						this.nhatros.remove(i);
					} else i++;
				}
			}
			if(!sl.isEmpty()) {
				int soluot = Integer.parseInt(sl);
				for (int i=0; i<this.nhatros.size();) {
					NhaTro nt = (NhaTro) this.nhatros.get(i);
					if(nt.getSoLuot()<soluot) {
						this.nhatros.remove(i);
					} else i++;
				}
			}
			if(!sn.isEmpty()) {
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
			model.addAttribute("nhatros", nhatros);
			model.addAttribute("page", 1);
		}catch(Exception e) {
			re.addFlashAttribute("message", "Không thể lọc ! " + e);
			return "redirect:index.htm";
		}
		return "index";
	}
}
