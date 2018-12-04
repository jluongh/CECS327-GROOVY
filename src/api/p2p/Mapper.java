package api.p2p;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


public abstract class Mapper implements MapInterface, ReduceInterface{
	
	private TreeMap<String, String> map;
	
	public void map(String line) throws IOException{
		String [] values = line.split(";");
		
		String key = values[0];
		String value = line;
		
		map.put(key, value);
	}
	
	public List<String> reduce(String search) throws IOException{
		List<String> values = new ArrayList<String>();
		for (String s : map.keySet()) {
			if (s.contains(search.toLowerCase())) {
				values.add(map.get(s));
			}
		}
		return values;
	}

}
