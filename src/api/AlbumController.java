package api;

import data.models.Album;
import services.AlbumService;

public class AlbumController {
	private AlbumService albumService = new AlbumService();
	
	public Album GetAlbumBySongTitle(String targetSong) {
		try {
			return albumService.getAlbumBySongTitle(targetSong);
		} catch (Exception e) {
			return null;
		}
	}
}
