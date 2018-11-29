package api.p2p;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import data.constants.Files;
import data.index.Chunk;
import data.index.MetadataFile;
import data.models.Song;

public class MetadataServiceMap {
	Peer ps;

	public MetadataServiceMap(Peer ps) {
		this.ps = ps;
	}

	/**
	 * 
	 */
	public void init() {
		File mdfjson = new File(Files.MDF);

		String[][] indices = { Files.ALBUMS, Files.ARTISTS, Files.SONGS, Files.USERS };
		List<MetadataFile> globalMdf = new ArrayList<MetadataFile>();

		for (String[] index : indices) {
			MetadataFile mdf = new MetadataFile(new File(index[0]), new File(index[1]), new File(index[2]), ps);
			globalMdf.add(mdf);
		}

		try (Writer writer = new FileWriter(mdfjson)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(globalMdf, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public List<Song> search(int index, String query) {
		List<Song> songs = new ArrayList<Song>();
		query = query.toLowerCase();

		String indexName = "";
		switch (index) {
		case Files.SONG_INDEX:
			indexName = Files.SONG_IDX;
			break;
		case Files.ALBUM_INDEX:
			indexName = Files.ALBUM_IDX;
			break;
		case Files.ARTIST_INDEX:
			indexName = Files.ARTIST_IDX;
			break;
		}

		try {
			BufferedReader br = new BufferedReader(new FileReader(Files.MDF));
			Type listType = new TypeToken<List<MetadataFile>>() {
			}.getType();
			List<MetadataFile> fileList = new Gson().fromJson(br, listType);

			for (MetadataFile f : fileList) {
				if (f.getIndexName().equals(indexName)) {
					for (Chunk chunk : f.getChunks()) {
						char firstLetter = chunk.getFirstLine().toLowerCase().charAt(0);
						char lastLetter = chunk.getLastLine().toLowerCase().charAt(0);
						char queryLetter = query.toLowerCase().charAt(0);

						if ((int) queryLetter >= (int) firstLetter && (int) queryLetter <= (int) lastLetter) {

							String guid = chunk.getGuid();
							String content = ps.get(guid);

							Scanner scanner = new Scanner(content);
							while (scanner.hasNextLine()) {
								String line = scanner.nextLine();
								if ((line.split(";")[0]).toLowerCase().startsWith(query)) {
									String[] songInfo = line.split(";");

									Song song = new Song();
									song.setSongID(Integer.parseInt(songInfo[Files.SONGID[index]]));
									song.setTitle(songInfo[Files.TITLE[index]]);
									song.setArtist(songInfo[Files.ARTIST[index]]);
									song.setAlbum(songInfo[Files.ALBUM[index]]);
									song.setDuration(Double.parseDouble(songInfo[Files.DURATION[index]]));

									songs.add(song);
								}
							}
							scanner.close();
						}
					}
				}
			}

			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return songs;
	}

}
