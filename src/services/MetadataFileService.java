package services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import data.index.MetadataFile;

public class MetadataFileService {

	private String filePath = data.constants.Files.MDF;

	public List<MetadataFile> getMetadataFile() {
		List<MetadataFile> file = new ArrayList<MetadataFile>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			Type listType = new TypeToken<List<MetadataFile>>() {}.getType();
			List<MetadataFile> response = new Gson().fromJson(br, listType);
			file = response;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return file;
	}
}
