package ptithcm.entity;


import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Province {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String type;
	
	@OneToMany(mappedBy = "province",fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private Collection<District> districts;
	
	@OneToMany(mappedBy = "province",fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private Collection<Truong> truongs;

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

	public Collection<District> getDistricts() {
		return districts;
	}

	public void setDistricts(Collection<District> districts) {
		this.districts = districts;
	}

	public Collection<Truong> getTruongs() {
		return truongs;
	}

	public void setTruongs(Collection<Truong> truongs) {
		this.truongs = truongs;
	}
	
}
