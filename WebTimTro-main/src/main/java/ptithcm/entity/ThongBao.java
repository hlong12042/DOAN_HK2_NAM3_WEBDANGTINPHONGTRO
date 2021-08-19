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
@Table(name = "thongbao")
public class ThongBao implements Comparable<ThongBao>{
	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne@JoinColumn(name = "account")
	private Account account;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date thoigian;
	private String link;
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	private String thongbao;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Date getThoigian() {
		return thoigian;
	}
	public void setThoigian(Date date) {
		this.thoigian = date;
	}
	public String getThongbao() {
		return thongbao;
	}
	public void setThongbao(String thongbao) {
		this.thongbao = thongbao;
	}
	
	
	public ThongBao() {
		super();
	}
	public ThongBao(int id, Account account, Date thoigian, String link, String thongbao) {
		super();
		this.id = id;
		this.account = account;
		this.thoigian = thoigian;
		this.link = link;
		this.thongbao = thongbao;
	}
	@Override
	public int compareTo(ThongBao o) {
		// TODO Auto-generated method stub
		return o.getThoigian().compareTo(this.getThoigian());
	}
	
}
