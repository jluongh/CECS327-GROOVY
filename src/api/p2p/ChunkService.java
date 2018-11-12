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

	public ChunkService(PeerService ps) {
		this.ps = ps;
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

//	/**
//	 * 
//	 * @param indexName
//	 * @param query
//	 * @return
//	 */
//	public List<Song> search(String indexName, String query) {
//		List<Song> songs = new ArrayList<Song>();
//		query = query.toLowerCase();
//
//		try {
//			BufferedReader br = new BufferedReader(new FileReader(Files.MDF));
//			Type listType = new TypeToken<List<MetadataFile>>() {
//			}.getType();
//			List<MetadataFile> fileList = new Gson().fromJson(br, listType);
//
//			for (MetadataFile f : fileList) {
//				if (f.getIndexName().equals(indexName)) {
//					for (int partitionNo = 1; partitionNo <= f.getChunks().size(); partitionNo++) {
//						String fileName = Files.ROOT + indexName.split("\\.")[0] + partitionNo + ".txt";
//						try (BufferedReader br2 = new BufferedReader(new FileReader(new File(fileName)))) {
//							String chunkLine;
//							while ((chunkLine = br2.readLine()) != null) {
//								if ((chunkLine.split(";")[0]).toLowerCase().contains(query)) {
//									String[] songInfo = chunkLine.split(";");
//
//									Song song = new Song();
//									song.setSongID(Integer.parseInt(songInfo[data.constants.Files.SONGID]));
//									song.setTitle(songInfo[data.constants.Files.TITLE]);
//									song.setArtist(songInfo[data.constants.Files.ARTIST]);
//									song.setAlbum(songInfo[data.constants.Files.ALBUM]);
//									song.setDuration(Double.parseDouble(songInfo[data.constants.Files.DURATION]));
//
//									songs.add(song);
//								}
//							}
//						}
//					}
//				}
//			}
//
//			br.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return songs;
//	}

	/**
	 * 
	 * @param indexName
	 * @param query
	 * @return
	 * @throws ClassNotFoundException
	 */
	public List<Song> search(int index, String query) {
		List<Song> songs = new ArrayList<Song>();
		query = query.toLowerCase();

		String indexName = "";
		switch(index) {
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

						if ((int)queryLetter >= (int)firstLetter && (int)queryLetter <= (int)lastLetter) {

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
