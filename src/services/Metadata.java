package services;

import java.io.File;
import java.util.List;

import data.index.MetadataFile;

public class Metadata 
{
	List<MetadataFile> file;
	boolean found = false;
	public void append(String fileName, String content)
	{
		if(file!=null && !file.isEmpty())
		{
			for(int i = 0; i<file.size();i++)
			{
				if(file.get(i).getName().equals(fileName))
				{
					found = true;
				}
			}
			if (found == false)
			{
				MetadataFile mf = new MetadataFile();
				mf.setName(fileName);
				//put it in chunk
			}
		}
		else
		{
			MetadataFile f = new MetadataFile();
			f.setName(fileName);
			f.append(content);
		}
		
	}
	
	public void getChunk(String fileName, int index)
	{
		
	}
}
