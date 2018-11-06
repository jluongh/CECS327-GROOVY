package services;

import java.io.*;
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
	
//	public String getFilePathByChunk(Chunk chunk) {
//		
//	}
	
//	public String getGuidByChunkFile(String pathname) {
//		
//	}
	
//	public String getContentByChunkGuid(String guid) {
//		
//	}
	
	/**
	 * NOT TESTED!
	 * 
	 * @param indexName
	 * @param query
	 * @return
	 */
	public List<String> search(String indexName, String query) {
		List<String> chunkLines = new ArrayList<String>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(Files.MDF));
			Type listType = new TypeToken<List<MetadataFile>>() {}.getType();
			List<MetadataFile> fileList = new Gson().fromJson(br, listType);
			
			for (MetadataFile f : fileList) {
				if (f.getIndexName().equals(indexName)) {
					int partitionNo = 1;
					String fileName = indexName.split("\\.")[0]+partitionNo+".txt";
					try (BufferedReader br2 = new BufferedReader(new FileReader(new File(fileName)))) {
						String chunkLine;
						while ((chunkLine = br2.readLine()) != null) {
							if (chunkLine.split(";")[0].contains(query))
								chunkLines.add(chunkLine);
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
