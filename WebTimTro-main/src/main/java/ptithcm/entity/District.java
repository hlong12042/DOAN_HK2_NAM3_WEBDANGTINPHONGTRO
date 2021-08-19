package ptithcm.entity;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class District {
	@Id 
	private int id;
	private String name;
	private String type;
	@ManyToOne @JoinColumn(name = "province_id")
	private Province province;
	
	@OneToMany(mappedBy = "district",fetch = FetchType.EAGER)
	private Collection<Ward> wards;

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

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public Collection<Ward> getWards() {
		return wards;
	}

	public void setWards(Collection<Ward> wards) {
		this.wards = wards;
	}
	
	
}
