package api;

import data.constants.Files;
import data.constants.Net;
import data.constants.Packet;
import data.models.*;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import api.p2p.MetadataService;
import api.p2p.PeerService;

import java.lang.reflect.*;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class SearchController {

	private MetadataService ms;

	public SearchController() {
		PeerService ps;
		try {
			ps = new PeerService();
			ms = new MetadataService(ps);
			ms.init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param query
	 * @return
	 * @throws IOException
	 */
	public List<Song> SearchByArtist(String query) throws IOException {
		return ms.search(Files.ARTIST_INDEX, query);
	}

	/**
	 * 
	 * @param query
	 * @return
	 * @throws IOException
	 */
	public List<Song> SearchByAlbum(String query) throws IOException {
		return ms.search(Files.ALBUM_INDEX, query);
	}

	/**
	 * 
	 * @param query
	 * @return
	 * @throws IOException
	 */
	public List<Song> SearchBySong(String query) throws IOException {
		return ms.search(Files.SONG_INDEX, query);

	}
}
