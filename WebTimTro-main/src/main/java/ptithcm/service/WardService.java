package ptithcm.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import ptithcm.entity.Ward;

public class WardService {

	static public List<Ward> findByDistrict(int id, SessionFactory factory) {
		Session session = factory.openSession();
		String hql = "from Ward w where w.district.id=" + id;
		Query query = session.createQuery(hql);
		List<Ward> list = query.list();
		session.close();
		return list;
	}

}
