//package api;
//
//import data.models.Artist;
//import services.ArtistService;
//
//public class ArtistController {
//	
//	//global variable
//	private ArtistService artistService = new ArtistService();
//	
//	/**
//	 * Method gets Artist by the song title
//	 * @param targetSong - {String} song 
//	 * @return artist
//	 */
//	public Artist GetArtistBySongTitle(String targetSong) { // TODO: Change to song ID
//		try {
//			return artistService.getArtistBySongTitle(targetSong);
//		} catch (Exception e) {
//			return null;
//		}
//	}
//}
