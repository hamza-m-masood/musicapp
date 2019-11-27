package dao;

import java.util.List;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import entities.Track;


public class TrackDAO {
	Configuration configObj;
	SessionFactory sessionFactory;
	public TrackDAO() {
		this.configObj = new Configuration();
		configObj.configure("hibernate.cfg.xml");
		this.sessionFactory = configObj.buildSessionFactory();
	}
	
	public void saveTrack(Track track) {
		try {
			Session sessionObj = sessionFactory.openSession();
			Transaction transactionObj = sessionObj.beginTransaction();
			sessionObj.save(track);
			transactionObj.commit();
			sessionObj.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
	
	public List<Track> getTracks(){
		Session session=sessionFactory.openSession();
		Query query = session.createQuery("from Track");
		List<Track> tracks = query.list();
		session.close();
		return tracks;		
	}
	
	public int updateDepartment(Track track) {
		if (track.getId() <= 0)
			return 0;
		Session session=sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		//FIX THIS
		String hql = "update Track set deptname = :name where id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", track.getId());
		query.setParameter("name", track.getId());
		int rowCount = query.executeUpdate();

		System.out.println("Rows affected: " + rowCount);
		tx.commit();
		session.close();
		return rowCount;
	}
	
	public int deleteTrack(int id) {
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		//FIX THIS
		String hql="delete from Track where id= :id";
		Query query=session.createQuery(hql);
		query.setParameter("id", id);
		int rowCount=query.executeUpdate();
		System.out.println("Rows affected: " +rowCount);
		transaction.commit();
		session.close();
		return rowCount;
		
	}
}
