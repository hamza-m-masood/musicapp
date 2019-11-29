package parse;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import entities.Playlist;
import entities.Track;

import org.w3c.dom.Document;

public class Parse {
	private ArrayList<Track> tracks;
	private ArrayList<Playlist> playlists;
	public void parse() throws ParserConfigurationException, SAXException, IOException {
		int id=0;
		String name=null;
		String artist=null;
		String album=null;
		String genre=null;
		int time=0;
		File xmlFile = new File("C:\\Users\\najmlion\\eclipse-workspace2019\\DOMParser\\iTunes Music Library1.xml");

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);
		doc.getDocumentElement().normalize();
		////////////////////////////////////////////////////////////////////////////
		// PRINTING OUT THE KEYS AT THE TOP OF XML FILE:
		///////////////////////////////////////////////////////////////////////////
		// get all dict nodes
		NodeList dicts = doc.getElementsByTagName("dict");
		// get first dict node
		Node libraryNode = dicts.item(0);
		// make node into element
		Element libElement = (Element) libraryNode;
		// get children of first dict element
		NodeList libChild = libElement.getChildNodes();
		// System.out.println(libElement.getChildNodes().item(4).getTextContent());

		////////////////////////////////////////////////////////////////////////////////////////////////
		// GETTING TRACKS
		/////////////////////////////////////////////////////////////////////////////////////////////
		// getting the first instance of parent dict
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

				if (dictTracksChildren.item(count).getTextContent().equals("31918")) {// last key of dictTracksChildren
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

////////////////////////////////////////////////////////////////////////////////////////////////
// GETTING PLAYLIST
/////////////////////////////////////////////////////////////////////////////////////////////
		boolean lastArrayFound = false;
		Node dictArray = null;// parent array from the parent dict
		NodeList playlist = null;// dict that contains the playlist information including the keys and the array
									// with the tracks
		count = 0;
		NodeList arrayTracks = null;// contains all of the dict nodes that have the track IDs inside
		NodeList arrayTracksChildren;//ever element of a dict node that has a track id
		int trackid;//track id for playlist
		int playlistid=0;
		String playlistName=null;
		playlists=new ArrayList<>();
		
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
			ArrayList<Track> playlistTracks=new ArrayList<>();
			if (parentArrayChildren.item(i).getNodeName().equals("dict")) {
				playlist = parentArrayChildren.item(i).getChildNodes();// playlist that includes all tracks
				for (int j = 0; j < playlist.getLength(); j++) {// for loop iterating through playlist
					// find the name and playlist id through the keys
					if (playlist.item(j).getTextContent().equals("Name")) {
						playlistName=playlist.item(j).getNextSibling().getTextContent();
					} else if (playlist.item(j).getTextContent().equals("Playlist ID")) {
						playlistid=Integer.parseInt(playlist.item(j).getNextSibling().getTextContent());
					}
					// get the array with all the tracks
					else if (playlist.item(j).getNodeName().equals("array")) {
						arrayTracks = playlist.item(j).getChildNodes();

						for (int p = 0; p < arrayTracks.getLength(); p++) {//iterating through child dicts of the child array
							if (!((p % 2) == 0)) {//every odd number in the for loop has a child, meaning every odd number is a dict element that has a child node, which has the track id and the integer
								arrayTracksChildren=arrayTracks.item(p).getChildNodes();
								trackid=Integer.parseInt(arrayTracksChildren.item(2).getTextContent());
								for(Track t : tracks) {
									if(t.getId()==trackid) {
										playlistTracks.add(t);
									}
								}
							}

						}

					}
				}
				Playlist playlistObject=new Playlist(playlistid, playlistName, playlistTracks);
				playlists.add(playlistObject);
			}
			
		}
		//System.out.println(playlists.get(0).getName());
		//System.out.println(playlists.get(0).getTracks().toString());
	}
	public ArrayList<Track> getTracks() {
		return tracks;
	}
	public void setTracks(ArrayList<Track> tracks) {
		this.tracks = tracks;
	}
	public ArrayList<Playlist> getPlaylists() {
		return playlists;
	}
	public void setPlaylists(ArrayList<Playlist> playlists) {
		this.playlists = playlists;
	}
	
	
	
	
}
