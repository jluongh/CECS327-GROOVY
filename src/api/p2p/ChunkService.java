package api.p2p;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import data.constants.Files;
import data.index.Chunk;
import data.index.MetadataFile;
import data.models.Song;

public class ChunkService {

	private PeerService ps;

	public ChunkService() throws IOException {
		ps = new PeerService();
	}

	/**
	 * 
	 * @param guid
	 * @return
	 */
	public String getContentByChunkGuid(String guid) {
		String content = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(Files.MDF));
			Type listType = new TypeToken<List<MetadataFile>>() {
			}.getType();
			List<MetadataFile> fileList = new Gson().fromJson(br, listType);

			boolean isChunkFound = false;

			for (MetadataFile f : fileList) {
				List<Chunk> chunks = f.getChunks();
				int partitionNo = 1;
				for (Chunk c : chunks) {
					if (c.getGuid().equals(guid)) {
						String fileName = Files.ROOT + f.getIndexName().split("\\.")[0] + partitionNo + ".txt";
						try (BufferedReader br2 = new BufferedReader(new FileReader(new File(fileName)))) {
							String chunkLine;
							while ((chunkLine = br2.readLine()) != null) {
								content = content + chunkLine + System.lineSeparator();
							}
						}
						break;
					}
					partitionNo++;
				}

				if (isChunkFound)
					break;
			}

			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content;
	}

	/**
	 * 
	 * @param indexName
	 * @param query
	 * @return
	 */
	public List<Song> search(String indexName, String query) {
		List<Song> songs = new ArrayList<Song>();
		query = query.toLowerCase();

		try {
			BufferedReader br = new BufferedReader(new FileReader(Files.MDF));
			Type listType = new TypeToken<List<MetadataFile>>() {
			}.getType();
			List<MetadataFile> fileList = new Gson().fromJson(br, listType);

			for (MetadataFile f : fileList) {
				if (f.getIndexName().equals(indexName)) {
					for (int partitionNo = 1; partitionNo <= f.getChunks().size(); partitionNo++) {
						String fileName = Files.ROOT + indexName.split("\\.")[0] + partitionNo + ".txt";
						try (BufferedReader br2 = new BufferedReader(new FileReader(new File(fileName)))) {
							String chunkLine;
							while ((chunkLine = br2.readLine()) != null) {
								if ((chunkLine.split(";")[0]).toLowerCase().contains(query)) {
									String[] songInfo = chunkLine.split(";");

									Song song = new Song();
									song.setSongID(Integer.parseInt(songInfo[data.constants.Files.SONGID]));
									song.setTitle(songInfo[data.constants.Files.TITLE]);
									song.setArtist(songInfo[data.constants.Files.ARTIST]);
									song.setAlbum(songInfo[data.constants.Files.ALBUM]);
									song.setDuration(Double.parseDouble(songInfo[data.constants.Files.DURATION]));

									songs.add(song);
								}
							}
						}
					}
				}
			}

			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return songs;
	}

	/**
	 * 
	 * @param indexName
	 * @param query
	 * @return
	 * @throws ClassNotFoundException
	 */
	public List<Song> search1(int index, String query) throws ClassNotFoundException {
		List<Song> songs = new ArrayList<Song>();
		query = query.toLowerCase();

		
		try {
			BufferedReader br = new BufferedReader(new FileReader(Files.MDF));
			Type listType = new TypeToken<List<MetadataFile>>() {
			}.getType();
			List<MetadataFile> fileList = new Gson().fromJson(br, listType);

			for (MetadataFile f : fileList) {
				if (f.getIndexName().equals(indexName)) {
					for (Chunk chunk : f.getChunks()) {
						char firstLetter = chunk.getFirstLine().charAt(0);
						char lastLetter = chunk.getLastLine().charAt(0);
						char queryLetter = query.charAt(0);

						if (queryLetter > firstLetter && queryLetter < lastLetter) {
							String guid = chunk.getGuid();
							String content = ps.get(guid);

							Scanner scanner = new Scanner(content);
							while (scanner.hasNextLine()) {
								String line = scanner.nextLine();
								if ((line.split(";")[0]).toLowerCase().contains(query)) {
									String[] songInfo = line.split(";");

									Song song = new Song();
									song.setSongID(Integer.parseInt(songInfo[data.constants.Files.SONGID]));
									song.setTitle(songInfo[data.constants.Files.TITLE]);
									song.setArtist(songInfo[data.constants.Files.ARTIST]);
									song.setAlbum(songInfo[data.constants.Files.ALBUM]);
									song.setDuration(Double.parseDouble(songInfo[data.constants.Files.DURATION]));

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
		}

		return songs;
	}
}
