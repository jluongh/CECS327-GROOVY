package api.p2p;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import data.constants.Files;
import data.index.Chunk;
import data.index.MetadataFile;

public class ChunkService {

	public ChunkService() {

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
			Type listType = new TypeToken<List<MetadataFile>>() {}.getType();
			List<MetadataFile> fileList = new Gson().fromJson(br, listType);
			
			boolean isChunkFound = false;
			
			for (MetadataFile f : fileList) {
				List<Chunk> chunks = f.getChunks();
				int partitionNo = 1;
				for (Chunk c : chunks) {
					if (c.getGuid().equals(guid)) {
						String fileName = Files.ROOT+f.getIndexName().split("\\.")[0]+partitionNo+".txt";
						try (BufferedReader br2 = new BufferedReader(new FileReader(new File(fileName)))) {
							String chunkLine;
							while ((chunkLine = br2.readLine()) != null) {
								content = content+chunkLine+System.lineSeparator();
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
	public List<String> search(String indexName, String query) {
		List<String> chunkLines = new ArrayList<String>();
		query = query.toLowerCase();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(Files.MDF));
			Type listType = new TypeToken<List<MetadataFile>>() {}.getType();
			List<MetadataFile> fileList = new Gson().fromJson(br, listType);
			
			for (MetadataFile f : fileList) {
				if (f.getIndexName().equals(indexName)) {
					for (int partitionNo = 1; partitionNo <= f.getChunks().size(); partitionNo++) {
						String fileName = Files.ROOT+indexName.split("\\.")[0]+partitionNo+".txt";
						try (BufferedReader br2 = new BufferedReader(new FileReader(new File(fileName)))) {
							String chunkLine;
							while ((chunkLine = br2.readLine()) != null) {
								if ((chunkLine.split(";")[0]).toLowerCase().contains(query))
									chunkLines.add(chunkLine);
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
		
		return chunkLines;
	}
}
