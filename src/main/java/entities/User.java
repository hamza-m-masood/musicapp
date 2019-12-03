package entities;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "user")
@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;
	String libraryPersistentId;
	String name;
	String password;
	@OneToMany
	List<Track> tracks;
	@OneToMany
	List<Playlist> playlists;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String libraryPersistentId, String name, String password, List<Track> tracks,
			List<Playlist> playlists) {
		super();
		this.libraryPersistentId = libraryPersistentId;
		this.name = name;
		this.password = password;
		this.tracks = tracks;
		this.playlists = playlists;
	}
	
	/**
	 * @return the libraryPersistentId
	 */
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
	 * @return the tracks
	 */

    @XmlElement
	public List<Track> getTracks() {
		return tracks;
	}
	/**
	 * @param tracks the tracks to set
	 */
	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}
	/**
	 * @return the playlists
	 */
    @XmlElement
	public List<Playlist> getPlaylists() {
		return playlists;
	}
	/**
	 * @param playlists the playlists to set
	 */
	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}
	
	
	
}
