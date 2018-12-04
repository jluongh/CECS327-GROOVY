package api.p2p;

import java.io.IOException;
import java.util.List;

public interface ReduceInterface {
	public void reduce(Number160 key, List<String> values, Peer p) throws IOException;

}
