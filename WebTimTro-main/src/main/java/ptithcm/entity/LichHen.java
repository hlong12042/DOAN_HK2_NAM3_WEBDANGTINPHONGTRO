package ptithcm.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "lichhen")
public class LichHen {
	@Id
	@GeneratedValue
	private int id;
	@ManyToOne
	@JoinColumn(name = "idkhachthue")
	private KhachThue khachThue;
	@ManyToOne
	@JoinColumn(name = "idnhatro")
	private NhaTro nhaTro;
	
	@Temporal(TemporalType.DATE)
//	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date thoigian;
	private Boolean dongy;
	private Boolean thanhcong;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public KhachThue getKhachThue() {
		return khachThue;
	}

	public void setKhachThue(KhachThue khachThue) {
		this.khachThue = khachThue;
	}

	public NhaTro getNhaTro() {
		return nhaTro;
	}

	public void setNhaTro(NhaTro nhaTro) {
		this.nhaTro = nhaTro;
	}

	public Date getThoigian() {
		return thoigian;
	}
	
	public Boolean getDongy() {
		return dongy;
	}

	public void setDongy(Boolean dongy) {
		this.dongy = dongy;
	}

	public Boolean getThanhcong() {
		return thanhcong;
	}

	public void setThanhcong(Boolean thanhcong) {
		this.thanhcong = thanhcong;
	}

	public void setThoigian(Date thoigian) {
		this.thoigian = thoigian;
	}
	
}
