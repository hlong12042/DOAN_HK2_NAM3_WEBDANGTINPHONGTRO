package ptithcm.entity;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role {
	@Id
	@GeneratedValue
	private int id;
	private String name;

	@OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
	private Collection<Account> accounts;

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

	public Collection<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Collection<Account> accounts) {
		this.accounts = accounts;
	}

	public Role(int id, String name, Collection<Account> accounts) {
		super();
		this.id = id;
		this.name = name;
		this.accounts = accounts;
	}

	public Role() {
		super();
	}
	
}
