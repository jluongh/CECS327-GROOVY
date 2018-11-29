package api.p2p;

import java.io.IOException;

public interface ReduceInterface {
	public void reduce(Integer key, String value[]) throws IOException;

}
