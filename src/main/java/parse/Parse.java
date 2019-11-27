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
		int id;
		String song;
		String artist;
		String album;
		String genre;
		int time;
		File xmlFile = new File("C:\\Users\\najmlion\\eclipse-workspace2019\\DOMParser\\iTunes Music Library1.xml");

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);
		doc.getDocumentElement().normalize();
		Element root = doc.getDocumentElement();
		NodeList dictList = doc.getElementsByTagName("dict");
		Node node = dictList.item(2);
		System.out.println("third dict value: " + node.getNodeName());

		System.out.println();
		if (node.hasChildNodes()) {
			NodeList dictChildNodes = node.getChildNodes();
			Node nodeChild = dictChildNodes.item(2);
			id = Integer.parseInt(nodeChild.getTextContent().toString());
			Node nodeSongName = dictChildNodes.item(5);
			song = nodeSongName.getTextContent().toString();
			Node nodeArtist = dictChildNodes.item(8);
			artist = nodeArtist.getTextContent().toString();
			Node nodeAlbum = dictChildNodes.item(14);
			album = nodeAlbum.getTextContent().toString();
			Node nodeGenre = dictChildNodes.item(17);
			genre = nodeGenre.getTextContent().toString();
			Node nodeTime = dictChildNodes.item(23);
			time = Integer.parseInt(nodeTime.getTextContent().toString());
			Track track = new Track(id, song, artist, album, genre, time);
			tracks=new ArrayList<Track>();
			tracks.add(track);
		}
	}
	public ArrayList<Track> getTracks() {
		return tracks;
	}
	public void setTracks(ArrayList<Track> tracks) {
		this.tracks = tracks;
	}
	
	
}
