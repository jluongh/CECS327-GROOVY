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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
	public void init(PeerService ps) {
		File mdfjson = new File(Files.MDF);
		if (!mdfjson.exists()) {
			String[][] indices = {Files.ALBUMS, Files.ARTISTS, Files.SONGS, Files.USERS};
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
