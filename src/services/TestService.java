package services;

import java.util.*;
import data.models.*;

public class TestService {

	public static void main(String[] args) {
		LibraryService ls = new LibraryService();
		List<Album> albums = ls.getAllAlbums();
		
		for (Album album : albums) {
			System.out.println(album.getName());
		}
	}
}
