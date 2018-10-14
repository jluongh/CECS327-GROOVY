package services;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import data.models.Log;

public class LogService {
	
	private String filePath = "./src/api/server/logging.json";

	public List<Log> GetLogs() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			Type listType = new TypeToken<List<Log>>() {}.getType();
			List<Log> logs = new Gson().fromJson(br, listType);
			return logs;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}
	
	public void CreateLog(Log log) {
		int lastID = GetLatestLogID();
		log.setLogID(lastID + 1);
		
		
		List<Log> logs = GetLogs();
		if (logs == null) {
			logs = new ArrayList<Log>();
		}
		
		System.out.println(logs.size());
		logs.add(log);
		SaveLogFile(logs);
	}

	public void DeleteLog(int logID) {
		List<Log> logs = GetLogs();
		logs.removeIf(l -> l.logID == logID);
		SaveLogFile(logs);
	}
	/**
	 * Save user profile updates
	 * 
	 * @param up
	 *               - {UserProfile} user profile object
	 * @return boolean when json file is created and serialized
	 */
	public void SaveLogFile(List<Log> logs) {
		try (Writer writer = new FileWriter(filePath)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(logs, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public int GetLatestLogID() {
		int id;
	
		List<Log> logs = GetLogs();
		
		if (logs != null && !logs.isEmpty()) {
			id = Collections.max(logs).logID;
		}
		else {
			id = -1;
		}
		return id;
	}
}
