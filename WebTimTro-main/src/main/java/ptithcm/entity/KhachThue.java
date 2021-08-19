package ptithcm.entity;

import java.util.Collection;

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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "khachthue")
public class KhachThue{
	@Id @GeneratedValue
	private int id;
	@ManyToOne @JoinColumn(name = "idtruong")
	private Truong truong;
	private int namSinh;
	private boolean gioiTinh;
	private String queQuan;
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fkusername", referencedColumnName = "username")
    private Account account;
	
	@OneToMany(mappedBy = "khachThue", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private Collection<LichHen> lichHen;
	
	@OneToMany(mappedBy = "khachthue", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private Collection<Comment> comment;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Truong getTruong() {
		return truong;
	}

	public void setTruong(Truong truong) {
		this.truong = truong;
	}

	public int getNamSinh() {
		return namSinh;
	}

	public void setNamSinh(int namSinh) {
		this.namSinh = namSinh;
	}

	public boolean isGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getQueQuan() {
		return queQuan;
	}

	public void setQueQuan(String queQuan) {
		this.queQuan = queQuan;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Collection<LichHen> getLichHen() {
		return lichHen;
	}

	public void setLichHen(Collection<LichHen> lichHen) {
		this.lichHen = lichHen;
	}

	public float getDiem(KhachThue khachthue) {
		float diem = 0;
		if(khachthue.getNamSinh()-2 < this.getNamSinh() && this.getNamSinh() < khachthue.getNamSinh()+2) diem+=1;
		if(khachthue.getTruong()==this.getTruong()) diem+=1;
		if(khachthue.getQueQuan()==this.getQueQuan()) diem+=1;
		if(khachthue.isGioiTinh()==this.isGioiTinh()) diem+=1;
		return diem;
	}
}
