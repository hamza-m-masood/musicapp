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
import dao.User1DAO;
import dao.UserDAO;
import entities.Playlist;
import entities.Playlist1;
import entities.Track;
import entities.Track1;
import entities.User;
import entities.User1;
import parse.Parse;
import javax.ws.rs.PathParam;

@Path("/sampleservice")
public class Service {
	private static List<Track> tracks;
	private static List<Track1> tracks1;
	private static List<Playlist> playlist;
	private static List<Playlist1> playlist1;
	private static User user;
	private static User1 user1;
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
		tracks1 = tracksStatic1;

		// USING HIBERNATE TO SAVE TRACKS
		TrackDAO trackdao = new TrackDAO();
		Track1DAO track1dao = new Track1DAO();
		for (Track t : tracks) {
			trackdao.saveTrack(t);
		}

		for (Track1 t : tracks1) {
			track1dao.saveTrack(t);
		}

		// CREATING ARRAYLIST OF PLAYLISTS
		ArrayList<Playlist> playlistStatic = parse.getPlaylists();
		ArrayList<Playlist1> playlistStatic1 = parse.getPlaylists1();
		playlist = playlistStatic;
		playlist1 = playlistStatic1;
		// USING HIBERNATE TO SAVE TRACKS
		PlaylistDAO playlistdao = new PlaylistDAO();
		Playlist1DAO playlist1dao = new Playlist1DAO();

		for (Playlist p : playlist) {
			playlistdao.savePlaylist(p);
		}

		for (Playlist1 p : playlist1) {
			playlist1dao.savePlaylist(p);
		}

		// CREATING USER AND PERSISTING
		user = new User(parse.getLibraryPersistentIdFirst(), "hamza", "123", tracks, playlist);
		UserDAO userdao = new UserDAO();
		userdao.saveuser(user);

		// CREATING USER1 AND PERSISTING
		user1 = new User1(parse.getLibraryPersistentIdSecond(), "tom", "123", tracks1, playlist1);
		User1DAO user1dao = new User1DAO();
		user1dao.saveuser(user1);

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

	// GET ALL TRACKS FROM USER: STRING
	@GET
	@Path("/user/{name}/{password}/getalltracks")
	@Produces("text/plain")
	public String getAllTracksUser(@PathParam("name") String name, @PathParam("password") String password) {
		if (name.equals(user.getName()) && password.equals(user.getPassword())) {
			String greetings = "Hello user name: " + name + " password: " + password + " Library Persistent ID"
					+ user.getLibraryPersistentId() + "\n";

			ArrayList<Track> tracks = new ArrayList<>();
			for (Track t : user.getTracks()) {
				tracks.add(t);
			}

			return greetings + tracks.toString();
		}
		System.out.println("Incorrect user");
		return "Icorrect user";

	}

	// GET ALL TRACKS FROM USER: XML (not working)
	@GET
	@Path("/user/{name}/{password}/getalltracks/xml")
	@Produces("application/xml")
	public List<Track> getAllTracksUserXML(@PathParam("name") String name, @PathParam("password") String password) {
		if (name.equals(user.getName()) && password.equals(user.getPassword())) {

			ArrayList<Track> tracks = new ArrayList<>();
			for (Track t : user.getTracks()) {
				tracks.add(t);
			}

			return tracks;
		}
		System.out.println("not working.");
		return null;

	}

	// GET ALL TRACKS FROM USER1: STRING
	@GET
	@Path("/user1/{name}/{password}/getalltracks")
	@Produces("text/plain")
	public String getAllTracksUser1(@PathParam("name") String name, @PathParam("password") String password) {
		if (name.equals(user1.getName()) && password.equals(user1.getPassword())) {
			String greetings = "Hello user name: " + name + " password: " + password + " Library Persistent ID"
					+ user.getLibraryPersistentId() + "\n";

			ArrayList<Track1> tracks1 = new ArrayList<>();
			for (Track1 t : user1.getTracks1()) {
				tracks1.add(t);
			}

			return greetings + tracks1.toString();

		}
		System.out.println("Incorrect user");
		return "Icorrect user";

	}

	// GET ALL PLAYLISTS FROM USER: STRING
	@GET
	@Path("/user/{name}/{password}/getallplaylists")
	@Produces("text/plain")
	public String getAllPlaylistsUser(@PathParam("name") String name, @PathParam("password") String password) {
		if (name.equals(user.getName()) && password.equals(user.getPassword())) {
			String greetings = "Hello user name: " + name + " password: " + password + " Library Persistent ID"
					+ user.getLibraryPersistentId() + "\n";

			List<Playlist> playlists = new ArrayList<>();
			playlists = user.getPlaylists();

			return greetings + " " + new ArrayList<>(playlists).toString();
		}
		System.out.println("Incorrect user");
		return "Icorrect user";

	}

	// GET ALL PLAYLISTS FROM USER1: STRING
	@GET
	@Path("/user1/{name}/{password}/getallplaylists")
	@Produces("text/plain")
	public String getAllPlaylistsUser1(@PathParam("name") String name, @PathParam("password") String password) {
		if (name.equals(user1.getName()) && password.equals(user1.getPassword())) {
			String greetings = "Hello user name: " + name + " password: " + password + " Library Persistent ID"
					+ user.getLibraryPersistentId() + "\n";

			List<Playlist1> playlists1 = new ArrayList<>();
			playlists1 = user1.getPlaylists1();

			return greetings + new ArrayList<>(playlists1).toString();
		}
		System.out.println("Incorrect user");
		return "Icorrect user";

	}

	// GET SPECIFIC TRACK FROM USER: STRING
	@GET
	@Path("/user/{name}/{password}/gettrack/{id}")
	@Produces("text/plain")
	public String getSpecificTracksUser(@PathParam("name") String name, @PathParam("password") String password, @PathParam("id") String id) {
		if (name.equals(user.getName()) && password.equals(user.getPassword())) {
			String greetings = "Hello user name: " + name + " password: " + password + " Library Persistent ID"
					+ user.getLibraryPersistentId() + "\n";

			for (Track t : user.getTracks()) {
				if(t.getId()==Integer.parseInt(id)) {
					return greetings + t.toString();
				}
			}

			return greetings + "track not found";
		}
		System.out.println("Incorrect user");
		return "Icorrect user";

	}
	
	// GET SPECIFIC PLAYLISTS FROM USER: STRING
	@GET
	@Path("/user/{name}/{password}/getplaylist/{id}")
	@Produces("text/plain")
	public String getSpecificPlaylistUser(@PathParam("name") String name, @PathParam("password") String password, @PathParam("id") String id) {
		if (name.equals(user.getName()) && password.equals(user.getPassword())) {
			String greetings = "Hello user name: " + name + " password: " + password + " Library Persistent ID"
					+ user.getLibraryPersistentId() + "\n";

			for (Playlist p : user.getPlaylists()) {
				if(p.getId()==Integer.parseInt(id)) {
					return greetings + p.toString()+"\n"+p.getTracks();
				}
			}

			return greetings + "track not found";
		}
		System.out.println("Incorrect user");
		return "Icorrect user";

	}
	
	//CHANGE TRACK FROM USER
	

}
