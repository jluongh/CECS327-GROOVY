package api.p2p;

import java.io.IOException;
import java.util.List;

public class Mapper implements MapInterface, ReduceInterface{
	public void map(Integer key, String value) throws IOException{
		for (word: value) {
			emit(md5(word), word +":"+ 1);
		}
	}
	
	public void reduce(String key, List<String> values, Peer p) throws IOException{
		String word = values.get(0).split(":")[0];
		p.emit(key, word +":"+ values.size());
	}

}
