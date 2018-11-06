package services;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import data.constants.Files;
import data.index.Chunk;
import data.index.MetadataFile;

/**
 * DONT USEEEEEEEEEEEEEEEEEEEEEEEEEEE
 * @author trishaechual
 *
 */
public class MetadataFileService {

	public MetadataFileService() {
		
	}
	
	/**
	 * 
	 * @param fileName
	 * @param newContent
	 */
	public void append (String fileName, String newContent) {
		File f = new File(Files.ROOT+fileName);
		File t = new File(Files.ROOT+"temp.txt");
		
		if (f.exists()) {
			try (BufferedReader br = new BufferedReader(new FileReader(f))) {
				FileWriter writer = new FileWriter(t);
				
				String oLine;
				String nLine;
				
				while((oLine = br.readLine()) != null) {
					
//					writer.write(oLine+System.lineSeparator());
//					if (newContent.charAt(0)==oLine.charAt(0)) {
//						if(oLine.compareTo(newContent)<0) {
//							nLine = System.lineSeparator()+newContent;
//							writer.write(nLine);
//						}
//					}
				}
				
				f.delete();
				t.renameTo(f);
				
				br.close();
				writer.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @param fileName
	 * @param guid
	 * @return
	 */
	public Chunk getChunk(String fileName, String guid) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(Files.MDF));
			Type listType = new TypeToken<List<MetadataFile>>() {}.getType();
			List<MetadataFile> fileList = new Gson().fromJson(br, listType);
			
			for (MetadataFile f : fileList) {
				if (f.getName().equals(fileName)) {
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


