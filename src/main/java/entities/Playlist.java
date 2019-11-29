package entities;
import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Playlist {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int playlistid;
	int id;
	String name;
	ArrayList<Track> tracks=new ArrayList<>();
	
	
	public Playlist() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Playlist(int id, String name, ArrayList<Track> tracks) {
		super();
		this.id = id;
		this.name = name;
		this.tracks = tracks;
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
	public ArrayList<Track> getTracks() {
		return tracks;
	}
	public void setTracks(ArrayList<Track> tracks) {
		this.tracks = tracks;
	}

	@Override
	public String toString() {
		return "Playlist [id=" + id + ", name=" + name + ", tracks=" + tracks + "]";
	}
	
	
}
