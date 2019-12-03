package dao;

import java.util.List;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import entities.Track;
import entities.Track1;


public class Track1DAO {
	Configuration configObj;
	SessionFactory sessionFactory;
	public Track1DAO() {
		this.configObj = new Configuration();
		configObj.configure("hibernate.cfg.xml");
		this.sessionFactory=HibernateUtil.getSessionFactory();
	}
	
	public void saveTrack(Track1 track1) {
		try {
			Session sessionObj = sessionFactory.openSession();
			Transaction transactionObj = sessionObj.beginTransaction();
			sessionObj.save(track1);
			transactionObj.commit();
			sessionObj.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
	
	public List<Track1> getTracks(){
		Session session=sessionFactory.openSession();
		Query query = session.createQuery("from Track1");
		List<Track1> tracks1 = query.list();
		session.close();
		return tracks1;		
	}
	
	public int updateTrack(Track1 track1) {
		if (track1.getId() <= 0)
			return 0;
		Session session=sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "update Track1 set album = :album, artist = :artist, genre = :genre, id = :id, name = :name, time = :time where id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("album", track1.getAlbum());
		query.setParameter("artist", track1.getArtist());
		query.setParameter("genre", track1.getGenre());
		query.setParameter("id", track1.getId());
		query.setParameter("name", track1.getName());
		query.setParameter("time", track1.getTime());
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
		String hql="delete from Track1 where id= :id";
		Query query=session.createQuery(hql);
		query.setParameter("id", id);
		int rowCount=query.executeUpdate();
		System.out.println("Rows affected: " +rowCount);
		transaction.commit();
		session.close();
		return rowCount;
		
	}
}
