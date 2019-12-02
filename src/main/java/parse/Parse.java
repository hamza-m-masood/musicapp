package parse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import entities.Playlist;
import entities.Playlist1;
import entities.Track;
import entities.Track1;
import entities.User;

public class Parse {
	ArrayList<Track> tracks;
	ArrayList<Playlist> playlists;

	ArrayList<Track1> tracks1;
	ArrayList<Playlist1> playlists1;
	
	String libraryPersistentIdFirst;
	String libraryPersistentIdSecond;
	
	User user;
	User user1;
	
	

	public void parse() throws SAXException, IOException, ParserConfigurationException {

		tracks = new ArrayList<>();
		playlists = new ArrayList<>();

		// FIRST XML FILE
		File xmlFile = new File("C:\\Users\\najmlion\\Desktop\\musicapp\\musicapp\\src\\iTunes Music Library1.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);
		doc.getDocumentElement().normalize();
		// SECOND XML FILE
		File xmlFile1 = new File(
				"C:\\Users\\najmlion\\Desktop\\musicapp\\musicapp\\src\\iTunes Music Library2 (1).xml");
		DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder1 = factory1.newDocumentBuilder();
		Document doc1 = dBuilder1.parse(xmlFile1);
		doc1.getDocumentElement().normalize();

		// PARSING FIRST XML FILE
		this.libraryPersistentIdFirst = parseTop(doc);
		tracks = parseTracks(doc, tracks);
		playlists = parsePlaylist(doc, tracks, playlists);

		this.user = new User(libraryPersistentIdFirst, "hamza", "123", tracks, playlists);

		// PARSING SECOND XML FILE
		this.libraryPersistentIdSecond = parseTop(doc1);
		tracks1 = parseTracksSecond(doc1, tracks1);
		playlists1 = parsePlaylistSecond(doc1, tracks1, playlists1);

		this.user1 = new User(libraryPersistentIdSecond, "tom", "123", tracks, playlists);

	}

	public String parseTop(Document doc) {
////////////////////////////////////////////////////////////////////////////
// PRINTING OUT THE LIBRARY PERSISTENT ID: 
///////////////////////////////////////////////////////////////////////////
		NodeList dicts = doc.getElementsByTagName("dict");

		NodeList dictsKeys = dicts.item(0).getChildNodes();
		for (int i = 0; i < dictsKeys.getLength(); i++) {
			if (dictsKeys.item(i).getNodeName().equals("key")) {
				if (dictsKeys.item(i).getTextContent().equals("Library Persistent ID")) {
					return (dictsKeys.item(i).getNextSibling().getTextContent());
				}
			}
		}
		return "";

	}

	public ArrayList<Track1> parseTracksSecond(Document doc, ArrayList<Track1> tracks) {
		int id = 0;
		String name = null;
		String artist = null;
		String album = null;
		String genre = null;
		int time = 0;

		////////////////////////////////////////////////////////////////////////////////////////////////
		// GETTING TRACKS
		/////////////////////////////////////////////////////////////////////////////////////////////
		// getting the first instance of parent dict
		NodeList dicts = doc.getElementsByTagName("dict");
		Node firstDictNode = dicts.item(0);
		// geting children of the first instance of parent dict i.e The first child is
		// "Major Version"
		NodeList firstDictNodeChildren = firstDictNode.getChildNodes();

//		// Finding the first dict element from the children of the first instance of the
//		// parent dict. i.e the dict element with all the tracks
		int count = 0;
		boolean lastDictFound = false;
		Node dictTracks = null;
		do {
			if (firstDictNodeChildren.item(count).getNodeName().equalsIgnoreCase("dict")) {
				dictTracks = firstDictNodeChildren.item(count);// contains all tracks
				lastDictFound = true;
			}
			count++;
		} while (!lastDictFound);

		// Finding all the keys of the dictTracks children i.e last key is
		// <key>31918</key> in first xml

		NodeList dictTracksChildren = dictTracks.getChildNodes();

		NodeList firstTrack = null;
		count = 0;
		lastDictFound = false;
		tracks = new ArrayList<>();
		do {
			if (dictTracksChildren.item(count).getNodeName().equalsIgnoreCase("key")) {// if dictTracksChildren value is
																						// key

				if (dictTracksChildren.item(count).getTextContent().equals("31918")
						|| dictTracksChildren.item(count).getTextContent().equals("4378")) {// last key of
																							// dictTracksChildren
					lastDictFound = true;
				}
			} else if (dictTracksChildren.item(count).getNodeName().equals("dict")) {// if dictTracksChildren value is
																						// dict
				firstTrack = dictTracksChildren.item(count).getChildNodes();

				for (int i = 0; i < firstTrack.getLength(); i++) {
					if (firstTrack.item(i).getTextContent().equals("Track ID")) {
						id = Integer.parseInt(firstTrack.item(i).getNextSibling().getTextContent());
					}
					if (firstTrack.item(i).getTextContent().equals("Name")) {
						name = firstTrack.item(i).getNextSibling().getTextContent();
					}
					if (firstTrack.item(i).getTextContent().equals("Artist")) {
						artist = firstTrack.item(i).getNextSibling().getTextContent();
					}
					if (firstTrack.item(i).getTextContent().equals("Album")) {

						album = firstTrack.item(i).getNextSibling().getTextContent();
					}
					if (firstTrack.item(i).getTextContent().equals("Genre")) {
						genre = firstTrack.item(i).getNextSibling().getTextContent();
					}
					if (firstTrack.item(i).getTextContent().equals("Total Time")) {
						time = Integer.parseInt(firstTrack.item(i).getNextSibling().getTextContent());
					}
				}
				Track1 track = new Track1(id, name, artist, album, genre, time);
				tracks.add(track);

			}
			count++;
		} while (!lastDictFound);

		return tracks;
	}

	public ArrayList<Track> parseTracks(Document doc, ArrayList<Track> tracks) {
		int id = 0;
		String name = null;
		String artist = null;
		String album = null;
		String genre = null;
		int time = 0;

		////////////////////////////////////////////////////////////////////////////////////////////////
		// GETTING TRACKS
		/////////////////////////////////////////////////////////////////////////////////////////////
		// getting the first instance of parent dict
		NodeList dicts = doc.getElementsByTagName("dict");
		Node firstDictNode = dicts.item(0);
		// geting children of the first instance of parent dict i.e The first child is
		// "Major Version"
		NodeList firstDictNodeChildren = firstDictNode.getChildNodes();

//		// Finding the first dict element from the children of the first instance of the
//		// parent dict. i.e the dict element with all the tracks
		int count = 0;
		boolean lastDictFound = false;
		Node dictTracks = null;
		do {
			if (firstDictNodeChildren.item(count).getNodeName().equalsIgnoreCase("dict")) {
				dictTracks = firstDictNodeChildren.item(count);// contains all tracks
				lastDictFound = true;
			}
			count++;
		} while (!lastDictFound);

		// Finding all the keys of the dictTracks children i.e last key is
		// <key>31918</key> in first xml

		NodeList dictTracksChildren = dictTracks.getChildNodes();

		NodeList firstTrack = null;
		count = 0;
		lastDictFound = false;
		tracks = new ArrayList<>();
		do {
			if (dictTracksChildren.item(count).getNodeName().equalsIgnoreCase("key")) {// if dictTracksChildren value is
																						// key

				if (dictTracksChildren.item(count).getTextContent().equals("31918")
						|| dictTracksChildren.item(count).getTextContent().equals("4378")) {// last key of
																							// dictTracksChildren
					lastDictFound = true;
				}
			} else if (dictTracksChildren.item(count).getNodeName().equals("dict")) {// if dictTracksChildren value is
																						// dict
				firstTrack = dictTracksChildren.item(count).getChildNodes();

				for (int i = 0; i < firstTrack.getLength(); i++) {
					if (firstTrack.item(i).getTextContent().equals("Track ID")) {
						id = Integer.parseInt(firstTrack.item(i).getNextSibling().getTextContent());
					}
					if (firstTrack.item(i).getTextContent().equals("Name")) {
						name = firstTrack.item(i).getNextSibling().getTextContent();
					}
					if (firstTrack.item(i).getTextContent().equals("Artist")) {
						artist = firstTrack.item(i).getNextSibling().getTextContent();
					}
					if (firstTrack.item(i).getTextContent().equals("Album")) {

						album = firstTrack.item(i).getNextSibling().getTextContent();
					}
					if (firstTrack.item(i).getTextContent().equals("Genre")) {
						genre = firstTrack.item(i).getNextSibling().getTextContent();
					}
					if (firstTrack.item(i).getTextContent().equals("Total Time")) {
						time = Integer.parseInt(firstTrack.item(i).getNextSibling().getTextContent());
					}
				}
				Track track = new Track(id, name, artist, album, genre, time);
				tracks.add(track);

			}
			count++;
		} while (!lastDictFound);

		return tracks;
	}

	public ArrayList<Playlist1> parsePlaylistSecond(Document doc, ArrayList<Track1> tracks,
			ArrayList<Playlist1> playlists) {
////////////////////////////////////////////////////////////////////////////////////////////////
//GETTING PLAYLIST
/////////////////////////////////////////////////////////////////////////////////////////////
		playlists = new ArrayList<>();
		boolean lastArrayFound = false;
		Node dictArray = null;// parent array from the parent dict
		NodeList playlist = null;// dict that contains the playlist information including the keys and the array
//with the tracks
		int count = 0;
		NodeList arrayTracks = null;// contains all of the dict nodes that have the track IDs inside
		NodeList arrayTracksChildren;// ever element of a dict node that has a track id
		int trackid;// track id for playlist
		int playlistid = 0;
		String playlistName = null;

		NodeList dicts = doc.getElementsByTagName("dict");
		Node firstDictNode = dicts.item(0);
		NodeList firstDictNodeChildren = firstDictNode.getChildNodes();

		do {
			if (firstDictNodeChildren.item(count).getNodeName().equalsIgnoreCase("array")) {
				dictArray = firstDictNodeChildren.item(count);// gets the array node from the parent dict
				lastArrayFound = true;
			}
			count++;
		} while (!lastArrayFound);

		NodeList parentArrayChildren = dictArray.getChildNodes();// includes all dict nodes. Each dict node has one
//playlist.

		for (int i = 0; i < parentArrayChildren.getLength(); i++) {
			ArrayList<Track1> playlistTracks1 = new ArrayList<>();
			if (parentArrayChildren.item(i).getNodeName().equals("dict")) {
				playlist = parentArrayChildren.item(i).getChildNodes();// playlist that includes all tracks
				for (int j = 0; j < playlist.getLength(); j++) {// for loop iterating through playlist
//find the name and playlist id through the keys
					if (playlist.item(j).getTextContent().equals("Name")) {
						playlistName = playlist.item(j).getNextSibling().getTextContent();
					} else if (playlist.item(j).getTextContent().equals("Playlist ID")) {
						playlistid = Integer.parseInt(playlist.item(j).getNextSibling().getTextContent());
					}
//get the array with all the tracks
					else if (playlist.item(j).getNodeName().equals("array")) {
						arrayTracks = playlist.item(j).getChildNodes();

						for (int p = 0; p < arrayTracks.getLength(); p++) {// iterating through child dicts of the child
//array
							if (!((p % 2) == 0)) {// every odd number in the for loop has a child, meaning every odd
//number is a dict element that has a child node, which has the
//track id and the integer
								arrayTracksChildren = arrayTracks.item(p).getChildNodes();
								trackid = Integer.parseInt(arrayTracksChildren.item(2).getTextContent());
								for (Track1 t : tracks) {
									if (t.getId() == trackid) {
										playlistTracks1.add(t);
									}
								}
							}

						}

					}
				}

				Playlist1 playlistObject = new Playlist1(playlistid, playlistName, playlistTracks1);

				playlists.add(playlistObject);

			}

		}
		return playlists;
	}

	public ArrayList<Playlist> parsePlaylist(Document doc, ArrayList<Track> tracks, ArrayList<Playlist> playlists) {
////////////////////////////////////////////////////////////////////////////////////////////////
//GETTING PLAYLIST
/////////////////////////////////////////////////////////////////////////////////////////////
		playlists = new ArrayList<>();
		boolean lastArrayFound = false;
		Node dictArray = null;// parent array from the parent dict
		NodeList playlist = null;// dict that contains the playlist information including the keys and the array
// with the tracks
		int count = 0;
		NodeList arrayTracks = null;// contains all of the dict nodes that have the track IDs inside
		NodeList arrayTracksChildren;// ever element of a dict node that has a track id
		int trackid;// track id for playlist
		int playlistid = 0;
		String playlistName = null;

		NodeList dicts = doc.getElementsByTagName("dict");
		Node firstDictNode = dicts.item(0);
		NodeList firstDictNodeChildren = firstDictNode.getChildNodes();

		do {
			if (firstDictNodeChildren.item(count).getNodeName().equalsIgnoreCase("array")) {
				dictArray = firstDictNodeChildren.item(count);// gets the array node from the parent dict
				lastArrayFound = true;
			}
			count++;
		} while (!lastArrayFound);

		NodeList parentArrayChildren = dictArray.getChildNodes();// includes all dict nodes. Each dict node has one
// playlist.

		for (int i = 0; i < parentArrayChildren.getLength(); i++) {
			ArrayList<Track> playlistTracks = new ArrayList<>();
			if (parentArrayChildren.item(i).getNodeName().equals("dict")) {
				playlist = parentArrayChildren.item(i).getChildNodes();// playlist that includes all tracks
				for (int j = 0; j < playlist.getLength(); j++) {// for loop iterating through playlist
// find the name and playlist id through the keys
					if (playlist.item(j).getTextContent().equals("Name")) {
						playlistName = playlist.item(j).getNextSibling().getTextContent();
					} else if (playlist.item(j).getTextContent().equals("Playlist ID")) {
						playlistid = Integer.parseInt(playlist.item(j).getNextSibling().getTextContent());
					}
// get the array with all the tracks
					else if (playlist.item(j).getNodeName().equals("array")) {
						arrayTracks = playlist.item(j).getChildNodes();

						for (int p = 0; p < arrayTracks.getLength(); p++) {// iterating through child dicts of the child
// array
							if (!((p % 2) == 0)) {// every odd number in the for loop has a child, meaning every odd
// number is a dict element that has a child node, which has the
// track id and the integer
								arrayTracksChildren = arrayTracks.item(p).getChildNodes();
								trackid = Integer.parseInt(arrayTracksChildren.item(2).getTextContent());
								for (Track t : tracks) {
									if (t.getId() == trackid) {
										playlistTracks.add(t);
									}
								}
							}

						}

					}
				}

				Playlist playlistObject = new Playlist(playlistid, playlistName, playlistTracks);

				playlists.add(playlistObject);

			}

		}
		return playlists;
	}

	/**
	 * @return the tracks
	 */
	public ArrayList<Track> getTracks() {
		return tracks;
	}

	/**
	 * @param tracks the tracks to set
	 */
	public void setTracks(ArrayList<Track> tracks) {
		this.tracks = tracks;
	}

	/**
	 * @return the playlists
	 */
	public ArrayList<Playlist> getPlaylists() {
		return playlists;
	}

	/**
	 * @param playlists the playlists to set
	 */
	public void setPlaylists(ArrayList<Playlist> playlists) {
		this.playlists = playlists;
	}





	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the user1
	 */
	public User getUser1() {
		return user1;
	}

	/**
	 * @param user1 the user1 to set
	 */
	public void setUser1(User user1) {
		this.user1 = user1;
	}

	/**
	 * @return the tracks1
	 */
	public ArrayList<Track1> getTracks1() {
		return tracks1;
	}

	/**
	 * @param tracks1 the tracks1 to set
	 */
	public void setTracks1(ArrayList<Track1> tracks1) {
		this.tracks1 = tracks1;
	}

	/**
	 * @return the playlists1
	 */
	public ArrayList<Playlist1> getPlaylists1() {
		return playlists1;
	}

	/**
	 * @param playlists1 the playlists1 to set
	 */
	public void setPlaylists1(ArrayList<Playlist1> playlists1) {
		this.playlists1 = playlists1;
	}

	/**
	 * @return the libraryPersistentIdFirst
	 */
	public String getLibraryPersistentIdFirst() {
		return libraryPersistentIdFirst;
	}

	/**
	 * @param libraryPersistentIdFirst the libraryPersistentIdFirst to set
	 */
	public void setLibraryPersistentIdFirst(String libraryPersistentIdFirst) {
		this.libraryPersistentIdFirst = libraryPersistentIdFirst;
	}

	/**
	 * @return the libraryPersistentIdSecond
	 */
	public String getLibraryPersistentIdSecond() {
		return libraryPersistentIdSecond;
	}

	/**
	 * @param libraryPersistentIdSecond the libraryPersistentIdSecond to set
	 */
	public void setLibraryPersistentIdSecond(String libraryPersistentIdSecond) {
		this.libraryPersistentIdSecond = libraryPersistentIdSecond;
	}
	
	
	
	
	

}
