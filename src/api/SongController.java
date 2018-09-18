package api;

import services.SongService;

public class SongController {
	
	private SongService songService = new SongService();

	/**
	 * 
	 */
	public SongController() {
		
	}
	
	/**
	 * Method formats duration of song from double to formatted string (mm:ss)
	 * @param duration
	 * @return
	 */
	public String FormatDuration(double duration) {
		try {
			return songService.FormatDuration(duration);
		} catch (Exception e) {
			return null;
		}
	}
}
