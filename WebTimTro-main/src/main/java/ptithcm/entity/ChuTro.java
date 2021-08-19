package ptithcm.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "chutro")
public class ChuTro {
	@Id @GeneratedValue
	private int id;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fkusername", referencedColumnName = "username")
    private Account account;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "chuTro")
	private Collection<NhaTro> nhaTro;

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

	public Collection<NhaTro> getNhaTro() {
		return nhaTro;
	}

	public void setNhaTro(Collection<NhaTro> nhaTro) {
		this.nhaTro = nhaTro;
	}
	
	public int getSobai() {
		return this.nhaTro.size();
	}
	
	public int getSobaidang() {
		int dem=0;
		for (NhaTro nt:this.nhaTro) {
			if(nt.getTinhtrang()==1) dem++;
		}
		return dem;
	}
	
	public int getSobaivipham() {
		int dem=0;
		for (NhaTro nt:this.nhaTro) {
			if(nt.getTinhtrang()==-1) dem++;
		}
		return dem;
	}
	
	public int getSoluotxem() {
		int dem=0;
		for (NhaTro nt:this.nhaTro) {
			for (LichHen lh:nt.getLichHen()) {
				if(lh.getDongy()) dem++;
			}
		}
		return dem;
	}
	
	public int getThanhcong() {
		int dem=0;
		for (NhaTro nt:this.nhaTro) {
			for (LichHen lh:nt.getLichHen()) {
				if(lh.getThanhcong()) dem++;
			}
		}
		return dem;
	}
	
	public int getCho() {
		int dem=0;
		for (NhaTro nt:this.nhaTro) {
			for (LichHen lh:nt.getLichHen()) {
				if(!lh.getDongy()) dem++;
			}
		}
		return dem;
	}
	public List<LichHen> getLichHen(){
		List<LichHen> lichHen = new ArrayList<>();
		for (NhaTro nhatro:this.nhaTro) {
			for (LichHen l:nhatro.getLichHen()) {
				lichHen.add(l);
			}
		}
		return lichHen;
	}
	//Trung bình điểm nhà trọ
	public float getDiem() {
		float diem=0;
		if(this.nhaTro.size()==0) return 5;
		for(NhaTro nt:this.nhaTro) {
			diem+=nt.getDiem();
		}
		return diem/this.nhaTro.size();
	}
	public int getSLNhaTro() {
		return nhaTro.size();
	}
}
