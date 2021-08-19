package ptithcm.entity;


import java.util.Calendar;

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
@Table(name="comment")
public class Comment {
	@Id
	@GeneratedValue
	private int id;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
	private Calendar thoigian;
	private String comment;
	private float diem;
	
	@ManyToOne
	@JoinColumn(name="idnhatro")
	private NhaTro nhatro;
	
	@ManyToOne
	@JoinColumn(name="idkhachthue")
	private KhachThue khachthue;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public float getDiem() {
		return diem;
	}

	public void setDiem(float diem) {
		this.diem = diem;
	}

	public NhaTro getNhatro() {
		return nhatro;
	}

	public void setNhatro(NhaTro nhatro) {
		this.nhatro = nhatro;
	}

	public KhachThue getKhachthue() {
		return khachthue;
	}

	public void setKhachthue(KhachThue khachthue) {
		this.khachthue = khachthue;
	}

	public Calendar getThoigian() {
		return thoigian;
	}

	public void setThoigian(Calendar thoigian) {
		this.thoigian = thoigian;
	}
	public String getThoiGian() {
		return thoigian.getTime().toString();
	}
}
