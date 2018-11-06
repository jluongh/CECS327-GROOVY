package services;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import data.constants.Files;
import data.index.Chunk;
import data.index.MetadataFile;

public class MetadataService {
	public MetadataService() {
		
	}
	
	/**
	 * 
	 */
	public void init() {
		File mdfjson = new File(Files.MDF);
		if (!mdfjson.exists()) {
			String[][] indices = {Files.ALBUMS, Files.ARTISTS, Files.SONGS, Files.USERS};
			List<MetadataFile> globalMdf = new ArrayList<MetadataFile>();
			
			for (String[] index : indices) {
				MetadataFile mdf = new MetadataFile(new File(index[0]), new File(index[1]), new File(index[2]));
				globalMdf.add(mdf);
			}
			
			try (Writer writer = new FileWriter(mdfjson)) {
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				gson.toJson(globalMdf, writer);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void append(String indexName, String contentItem) {
//		File f = new File(Files.ROOT+fileName);
//		File t = new File(Files.ROOT+"temp.txt");
//		
//		if (f.exists()) {
//			try (BufferedReader br = new BufferedReader(new FileReader(f))) {
//				FileWriter writer = new FileWriter(t);
//				
//				String oLine;
//				String nLine;
//				
//				while((oLine = br.readLine()) != null) {
//					
////					writer.write(oLine+System.lineSeparator());
////					if (newContent.charAt(0)==oLine.charAt(0)) {
////						if(oLine.compareTo(newContent)<0) {
////							nLine = System.lineSeparator()+newContent;
////							writer.write(nLine);
////						}
////					}
//				}
//				
//				f.delete();
//				t.renameTo(f);
//				
//				br.close();
//				writer.close();
//				
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	/**
	 * 
	 * @param indexName
	 * @param guid
	 * @return
	 */
	public Chunk getChunk(String indexName, String guid) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(Files.MDF));
			Type listType = new TypeToken<List<MetadataFile>>() {}.getType();
			List<MetadataFile> fileList = new Gson().fromJson(br, listType);
			
			for (MetadataFile f : fileList) {
				if (f.getIndexName().equals(indexName)) {
					List<Chunk> chunks = f.getChunks();
					for (Chunk c : chunks) {
						if (c.getGuid().equals(guid))
							return c;
					}
				}
			}
			
			return null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
