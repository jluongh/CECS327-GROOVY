package api;

import data.models.Artist;
import services.ArtistService;

public class ArtistController {
	private ArtistService artistService = new ArtistService();
	
	public Artist GetArtistBySongTitle(String targetSong) {
		try {
			return artistService.getArtistBySongTitle(targetSong);
		} catch (Exception e) {
			return null;
		}
	}
}
