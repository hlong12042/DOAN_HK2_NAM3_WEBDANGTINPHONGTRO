package ptithcm.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import ptithcm.entity.District;

public class DistrictService {
	static public List<District> findByProvince(int id, SessionFactory factory) {
		Session session = factory.openSession();
		String hql = "from District d where d.province.id=" + id;
		Query query = session.createQuery(hql);
		List<District> list = query.list();
		session.close();
		return list;
	}
}
