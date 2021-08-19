package ptithcm.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "nhatro")
public class NhaTro {
	@Id @GeneratedValue
	private int id;
	
	@ManyToOne @JoinColumn(name = "idchutro")
	private ChuTro chuTro;
	
	private String tieuDe;
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "iddiachi", referencedColumnName = "id")
    private DiaChi diachi;
	
	private int soPhongChoThue;
	private int soNguoiTrenPhong;
	private int soPhongCoSan;
	private float dienTich;
	private BigDecimal tienCoc;
	private BigDecimal tienThue;
	private String moTa;
	private int tinhtrang;
	@Temporal(TemporalType.DATE)
//	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ngayThem;
	
	@OneToMany(mappedBy = "nhaTro", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private Collection<LichHen> lichHen;
	
	@OneToMany(mappedBy = "nhatro", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private Collection<Comment> comment;
	
	
	public Collection<LichHen> getLichHen() {
		return lichHen;
	}
	public void setLichHen(Collection<LichHen> lichHen) {
		this.lichHen = lichHen;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ChuTro getChuTro() {
		return chuTro;
	}
	public void setChuTro(ChuTro chuTro) {
		this.chuTro = chuTro;
	}
	public String getTieuDe() {
		return tieuDe;
	}
	public void setTieuDe(String tieuDe) {
		this.tieuDe = tieuDe;
	}
	public DiaChi getDiachi() {
		return diachi;
	}
	public void setDiachi(DiaChi diachi) {
		this.diachi = diachi;
	}
	public int getSoPhongChoThue() {
		return soPhongChoThue;
	}
	public void setSoPhongChoThue(int soPhongChoThue) {
		this.soPhongChoThue = soPhongChoThue;
	}
	public int getSoNguoiTrenPhong() {
		return soNguoiTrenPhong;
	}
	public void setSoNguoiTrenPhong(int soNguoiTrenPhong) {
		this.soNguoiTrenPhong = soNguoiTrenPhong;
	}
	public int getSoPhongCoSan() {
		return soPhongCoSan;
	}
	public void setSoPhongCoSan(int soPhongCoSan) {
		this.soPhongCoSan = soPhongCoSan;
	}
	public float getDienTich() {
		return dienTich;
	}
	public void setDienTich(float dienTich) {
		this.dienTich = dienTich;
	}
	
	public BigDecimal getTienCoc() {
		return tienCoc;
	}
	public void setTienCoc(BigDecimal tienCoc) {
		this.tienCoc = tienCoc;
	}
	public BigDecimal getTienThue() {
		return tienThue;
	}
	public void setTienThue(BigDecimal tienThue) {
		this.tienThue = tienThue;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	//Mỗi nhà trọ có sẵn 5 điểm (điểm tối đa)
	//Điểm = trung bình (tổng điểm trong comment + 5)
	public float getDiem() {
		float diem=0;
		if(!this.getComment().isEmpty()) {
			for (Comment comment:this.getComment()) {
				diem+=comment.getDiem();
			}
		}
		diem=(diem+5)/(this.getComment().size()+1);
		return diem;
	}
	public Date getNgayThem() {
		return ngayThem;
	}
	public void setNgayThem(Date ngayThem) {
		this.ngayThem = ngayThem;
	}
	public int getTinhtrang() {
		return tinhtrang;
	}
	public void setTinhtrang(int tinhtrang) {
		this.tinhtrang = tinhtrang;
	}
	public int getSoLuot() {
		int soLuot = 0;
		for (LichHen lan:this.getLichHen()) {
			if(lan.getThanhcong()) soLuot++;
		}
		return soLuot;
	}
	public String getProvinceName() {
		return this.getDiachi().getWard().getDistrict().getProvince().getName();
	}
	public String getDistrictName() {
		return this.getDiachi().getWard().getDistrict().getName();
	}
	public String getWardName() {
		return this.getDiachi().getWard().getName();
	}
	public int getProvinceId() {
		return this.getDiachi().getWard().getDistrict().getProvince().getId();
	}
	public int getDistrictId() {
		return this.getDiachi().getWard().getDistrict().getId();
	}
	public int getWardId() {
		return this.getDiachi().getWard().getId();
	}
	public Collection<Comment> getComment() {
		return comment;
	}
	public void setComment(Collection<Comment> comment) {
		this.comment = comment;
	}
	//Tính điểm dựa vào độ phù hợp thông tin
	public float getDiem2(KhachThue khachthue) {
		float diem=this.getDiem();
		for (LichHen lichhen:this.getLichHen()) {
			if(lichhen.getThanhcong()) diem+= khachthue.getDiem(lichhen.getKhachThue());
		}
		return diem;
	}
	public List<Object> getGioitinh(){
		int nam=0, nu=0;
		for (LichHen lh:this.lichHen) {
			if(lh.getThanhcong()) {
				if(lh.getKhachThue().isGioiTinh()) nu++;
				else nam++;
			}
		}
		List<Object> temp = new ArrayList<Object>();
		if (nam>nu) {
			temp.add(false); temp.add(nam);
		} else {
			temp.add(true); temp.add(nu);
		}
		return temp;
	}
}
