package api.p2p;

import java.io.IOException;
import java.util.List;

import net.tomp2p.peers.Number160;

public interface ReduceInterface {
	public void reduce(Number160 key, List<String> values, Counter counter) throws IOException;

}
