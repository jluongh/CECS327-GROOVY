package api;

import services.SongService;

public class SongController {
	
	//global variables
	private SongService songService = new SongService();

	public SongController() {
		//no initialization
	}
	
	/**
	 * Method formats duration of song from double to formatted string (mm:ss)
	 * @param duration - {double} length of the song
	 * @return duration
	 */
	public String FormatDuration(double duration) {
		try {
			return songService.FormatDuration(duration);
		} catch (Exception e) {
			return null;
		}
	}
}
