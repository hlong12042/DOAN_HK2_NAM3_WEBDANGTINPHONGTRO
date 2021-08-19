package ptithcm.entity;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
	
public class Ward {
	@Id 
	private int id;
	private String name;
	private String type;
	@ManyToOne @JoinColumn(name = "district_id")
	private District district;
	
	@OneToMany(mappedBy = "ward",fetch = FetchType.EAGER)
	private Collection<DiaChi> diaChi;
	
	
	public Collection<DiaChi> getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(Collection<DiaChi> diaChi) {
		this.diaChi = diaChi;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public District getDistrict() {
		return district;
	}
	public void setDistrict(District district) {
		this.district = district;
	}
	
	
}
