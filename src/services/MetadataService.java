package services;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;

import data.constants.Files;
import data.index.Chunk;
import data.index.MetadataFile;

public class MetadataService {
	public MetadataService() {
		
	}
	
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
}
