package ptithcm.entity;

import java.util.Date;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "account")
public class Account {
	@Id
	private String username;
	private String password;
	private String hoTen;
	private String cmnd;
	private String dienThoai;
	private String email;
	@Temporal(TemporalType.DATE)
//	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ngayDangKy;

	@OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
	private Collection<ThongBao> thongBao;

	@OneToOne(mappedBy = "account")
	private ChuTro chuTro;
	
	@OneToOne(mappedBy = "account")
	private KhachThue khachThue;
	
	@ManyToOne 
	@JoinColumn(name = "idrole")
	private Role role;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public String getCmnd() {
		return cmnd;
	}

	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
	}

	public String getDienThoai() {
		return dienThoai;
	}

	public void setDienThoai(String dienThoai) {
		this.dienThoai = dienThoai;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getNgayDangKy() {
		return ngayDangKy;
	}

	public void setNgayDangKy(Date ngayDangKy) {
		this.ngayDangKy = ngayDangKy;
	}

	public Collection<ThongBao> getThongBao() {
		return thongBao;
	}

	public void setThongBao(Collection<ThongBao> thongBao) {
		this.thongBao = thongBao;
	}

	public ChuTro getChuTro() {
		return chuTro;
	}

	public void setChuTro(ChuTro chuTro) {
		this.chuTro = chuTro;
	}

	public KhachThue getKhachThue() {
		return khachThue;
	}

	public void setKhachThue(KhachThue khachThue) {
		this.khachThue = khachThue;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
}
