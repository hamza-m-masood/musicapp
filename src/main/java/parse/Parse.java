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

import entities.Track;

import org.w3c.dom.Document;

public class Parse {
	private ArrayList<Track> tracks;
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

		// Finding the first dict element from the children of the first instance of the
		// parent dict. i.e the dict element with all the tracks
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
		tracks=new ArrayList<>();
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
						id=Integer.parseInt(firstTrack.item(i).getNextSibling().getTextContent());
					}
					if (firstTrack.item(i).getTextContent().equals("Name")) {
						name=firstTrack.item(i).getNextSibling().getTextContent();
					}
					if (firstTrack.item(i).getTextContent().equals("Artist")) {
						artist=firstTrack.item(i).getNextSibling().getTextContent();
					}
					if (firstTrack.item(i).getTextContent().equals("Album")) {

						album=firstTrack.item(i).getNextSibling().getTextContent();
					}
					if (firstTrack.item(i).getTextContent().equals("Genre")) {
						genre=firstTrack.item(i).getNextSibling().getTextContent();
					}
					if (firstTrack.item(i).getTextContent().equals("Total Time")) {
						time=Integer.parseInt(firstTrack.item(i).getNextSibling().getTextContent());
					}
				}
				Track track=new Track(id, name, artist, album, genre, time);
				tracks.add(track);
				
			}
			count++;
		} while (!lastDictFound);
	}
	public ArrayList<Track> getTracks() {
		return tracks;
	}
	public void setTracks(ArrayList<Track> tracks) {
		this.tracks = tracks;
	}
	
	
}
