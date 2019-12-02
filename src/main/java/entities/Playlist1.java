package entities;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.ForeignKey;
@Entity
public class Playlist1 {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int playlistid;
	int id;
	String name;
	@ManyToMany
	List<Track1> tracks1=new ArrayList<>();
	
	
	public Playlist1() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Playlist1(int id, String name, List<Track1> tracks1) {
		super();
		this.id = id;
		this.name = name;
		this.tracks1 = tracks1;
	}
	
	
	public int getPlaylistid() {
		return playlistid;
	}

	public void setPlaylistid(int playlistid) {
		this.playlistid = playlistid;
	}

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
	

	public List<Track1> getTracks1() {
		return tracks1;
	}

	public void setTracks(List<Track1> tracks1) {
		this.tracks1 = tracks1;
	}

	@Override
	public String toString() {
		return "Playlist [id=" + id + ", name=" + name + ", tracks1=" + tracks1 + "]";
	}
	
	
}