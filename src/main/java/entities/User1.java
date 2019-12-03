package entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "user")
@Entity
public class User1 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String libraryPersistentId;
	String name;
	String password;
	@OneToMany
	List<Track1> tracks1;
	@OneToMany
	List<Playlist1> playlists1;

	public User1() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User1(String libraryPersistentId, String name, String password, List<Track1> tracks1,
			List<Playlist1> playlists1) {
		super();
		this.libraryPersistentId = libraryPersistentId;
		this.name = name;
		this.password = password;
		this.tracks1 = tracks1;
		this.playlists1 = playlists1;
	}

	/**
	 * @return the libraryPersistentId
	 */
	@XmlElement
	public String getLibraryPersistentId() {
		return libraryPersistentId;
	}

	/**
	 * @param libraryPersistentId the libraryPersistentId to set
	 */
	public void setLibraryPersistentId(String libraryPersistentId) {
		this.libraryPersistentId = libraryPersistentId;
	}

	/**
	 * @return the id
	 */
	@XmlElement
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	@XmlElement
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the password
	 */
	@XmlElement
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;

	}

	/**
	 * @return the tracks1
	 */
	@XmlElement
	public List<Track1> getTracks1() {
		return tracks1;
	}

	/**
	 * @param tracks1 the tracks1 to set
	 */
	public void setTracks1(List<Track1> tracks1) {
		this.tracks1 = tracks1;
	}

	/**
	 * @return the playlists1
	 */
	@XmlElement
	public List<Playlist1> getPlaylists1() {
		return playlists1;
	}

	/**
	 * @param playlists1 the playlists1 to set
	 */
	public void setPlaylists1(List<Playlist1> playlists1) {
		this.playlists1 = playlists1;
	}
}
