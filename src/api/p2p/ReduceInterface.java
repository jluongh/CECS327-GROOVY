package api.p2p;

import java.io.IOException;
import java.util.List;

import net.tomp2p.peers.Number160;

public interface ReduceInterface {
	public List<String> reduce(String search) throws IOException;

}
