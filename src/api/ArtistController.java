package api;

import data.models.Artist;
import services.ArtistService;

public class ArtistController {
	private ArtistService artistService = new ArtistService();
	
	/**
	 * Method gets Artist by the song title
	 * @param targetSong
	 * @return
	 */
	public Artist GetArtistBySongTitle(String targetSong) { // TODO: Change to song ID
		try {
			return artistService.getArtistBySongTitle(targetSong);
		} catch (Exception e) {
			return null;
		}
	}
}
