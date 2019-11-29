package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import entities.Playlist;
import entities.Track;

public class PlaylistDAO {
	Configuration configObj;
	SessionFactory sessionFactory;
	public PlaylistDAO() {
		this.configObj = new Configuration();
		configObj.configure("hibernate.cfg.xml");
		this.sessionFactory = configObj.buildSessionFactory();
	}
	
	public void savePlaylist(Playlist playlist) {
		try {
			Session sessionObj = sessionFactory.openSession();
			Transaction transactionObj = sessionObj.beginTransaction();
			sessionObj.save(playlist);
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
	
	public int updatePlaylist(Playlist playlist) {
		if (playlist.getId() <= 0)
			return 0;
		Session session=sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		//FIX THIS
		String hql = "update Track set deptname = :name where id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", playlist.getId());
		query.setParameter("name", playlist.getId());
		int rowCount = query.executeUpdate();

		System.out.println("Rows affected: " + rowCount);
		tx.commit();
		session.close();
		return rowCount;
	}
	
	public int deletePlaylist(int id) {
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
