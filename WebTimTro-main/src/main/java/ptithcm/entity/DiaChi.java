package ptithcm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity @Table(name = "diachi")
public class DiaChi {
	@Id @GeneratedValue
	private int id;
	private String diaChi;
	
	@OneToOne(mappedBy = "diachi")
	private NhaTro nhaTro;
	
	@ManyToOne @JoinColumn(name = "idward")
	private Ward ward;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public NhaTro getNhaTro() {
		return nhaTro;
	}

	public void setNhaTro(NhaTro nhaTro) {
		this.nhaTro = nhaTro;
	}

	public Ward getWard() {
		return ward;
	}

	public void setWard(Ward ward) {
		this.ward = ward;
	}

	
	
}
