package data.index;

import java.io.*;
import java.util.*;

import api.p2p.Peer;
import net.tomp2p.peers.Number160;

public class MetadataFile {

	private String indexName;
	private long totalSize;
	private List<Chunk> chunks = new ArrayList<Chunk>();
	
	public MetadataFile() {
		
	}
	
	public MetadataFile(File file1, File file2, File file3, Peer ps) {
		if (file1.exists() && file2.exists() && file3.exists()) {
			this.indexName = getIndexName(file1, file2, file3);
			this.totalSize = getIndexSize(file1, file2, file3);
			this.chunks = populateChunks(file1, file2, file3, ps);
		}
	}
	
	public String getIndexName() {
		return indexName;
	}

	public long getTotalSize() {
		return totalSize;
	}

	public List<Chunk> getChunks() {
		return chunks;
	}

	public void setChunks(List<Chunk> chunks) {
		this.chunks = chunks;
	}

	/**
	 * 
	 * @param file1
	 * @param file2
	 * @param file3
	 * @return
	 */
	private String getIndexName(File file1, File file2, File file3) {
		String[] files = new String[] {file1.getName(),file2.getName(),file3.getName()};
		List<String> index = new ArrayList<String>();
		
		for (int i = 0; i < files.length; i++) {
			String[] names = files[i].split("\\.");
			index.add(names[0].replaceAll("[0-9]", ""));
		}
		
		if (index.stream().distinct().limit(2).count() <= 1)
			return index.get(0);
		
		return "";
	}
	
	/**
	 * 
	 * @param file1
	 * @param file2
	 * @param file3
	 * @return
	 */
	private long getIndexSize(File file1, File file2, File file3) {
		return file1.length() + file2.length() + file3.length();
	}

	/**
	 * 
	 * @param file1
	 * @param file2
	 * @param file3
	 * @return
	 */
	private List<Chunk> populateChunks(File file1, File file2, File file3, Peer ps) {
		List<Chunk> chunks = new ArrayList<Chunk>();
		
		List<File> files = new ArrayList<File>();
		files.add(file1);
		files.add(file2);
		files.add(file3);
		
		for (File f : files) {
			boolean isFirst = true;
			String content = "";
			String first = "";
			String last = "";
			Number160 guid = null;
			
			try (BufferedReader br = new BufferedReader(new FileReader(f))) {
				String line;
				while ((line = br.readLine()) != null) {
					if (isFirst) {
						first = line;
						isFirst = false;
					}
					content = content + line + System.lineSeparator();
					last = line;
				}
				
				guid = Number160.createHash(content);
				ps.put(guid, content);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Chunk chunk = new Chunk(guid.toString());
			chunk.setFirstLine(first);
			chunk.setLastLine(last);
			
			chunks.add(chunk);
		}
		
		return chunks;
	}
}
