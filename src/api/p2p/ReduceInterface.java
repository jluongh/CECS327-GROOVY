package api.p2p;

import java.io.IOException;
import java.util.List;

public interface ReduceInterface {
	public void reduce(String key, List<String> values) throws IOException;

}
