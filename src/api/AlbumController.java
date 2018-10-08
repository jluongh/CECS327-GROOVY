package api;

import data.models.Album;
import services.AlbumService;

public class AlbumController {
	
	//global variables
	private AlbumService albumService = new AlbumService();
	
	/**
	 * Method gets album by the title of the song
	 * @param targetSong - {String} song
	 * @return album
	 */
	public Album GetAlbumBySongTitle(String targetSong) { // TODO: Trisha - Change to Song ID
		try {
			return albumService.getAlbumBySongTitle(targetSong);
		} catch (Exception e) {
			return null;
		}
	}
}
