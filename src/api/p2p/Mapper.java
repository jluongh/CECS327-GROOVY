package api.p2p;

import java.io.IOException;

public class Mapper implements MapInterface, ReduceInterface{
	public void map(Integer key, String value) throws IOException{
		for (word:value) {
			emit(md5(word), word +":"+1);
		}
	}
	
	public void reduce(Integer key, String values[]) throws IOException{
	word = values[0].split(":")[0]
	emit(key, word +":"+ len(values));
	}
}
