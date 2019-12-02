package output;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import dao.Playlist1DAO;
import dao.PlaylistDAO;
import dao.Track1DAO;
import dao.TrackDAO;
import dao.UserDAO;
import entities.Playlist;
import entities.Playlist1;
import entities.Track;
import entities.Track1;
import entities.User;
import parse.Parse;

@Path("/sampleservice")
public class Service {
	private static List<Track> tracks;
	private static List<Track1> tracks1;
	private static List<Playlist> playlist;
	private static List<Playlist1> playlist1;
	static {
		Parse parse = new Parse();
		try {
			parse.parse();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// CREATING ARRAYLIST OF TRACKS
		ArrayList<Track> tracksStatic = parse.getTracks();
		ArrayList<Track1> tracksStatic1 = parse.getTracks1();

		tracks = tracksStatic;
		tracks1=tracksStatic1;
		
		// USING HIBERNATE TO SAVE TRACKS
		TrackDAO trackdao = new TrackDAO();
		Track1DAO track1dao = new Track1DAO();
		for (Track t : tracks) {
			trackdao.saveTrack(t);
		}

		for(Track1 t: tracks1) {
			track1dao.saveTrack(t);
		}

		// CREATING ARRAYLIST OF PLAYLISTS
		ArrayList<Playlist> playlistStatic = parse.getPlaylists();
		ArrayList<Playlist1> playlistStatic1 = parse.getPlaylists1();
		playlist = playlistStatic;
		playlist1=playlistStatic1;
		// USING HIBERNATE TO SAVE TRACKS
		PlaylistDAO playlistdao = new PlaylistDAO();
		Playlist1DAO playlist1dao=new Playlist1DAO();
		
		for(Playlist p: playlist) {
			playlistdao.savePlaylist(p);
		}
		
		for (Playlist1 p : playlist1) {
			playlist1dao.savePlaylist(p);
		}
		
		//CREATING USER AND PERSISTING
		User user=new User(parse.getLibraryPersistentIdFirst(), "hamza", "123", tracks, playlist);
		UserDAO userdao=new UserDAO();
		userdao.saveuser(user);

	}

	@GET
	@Path("/hello")
	@Produces("text/plain")
	public String hello() {
		return "hello";
	}

	@GET
	@Path("/tracks")
	@Produces("text/plain")
	public String tracks() {
		return tracks.toString();
	}
}
