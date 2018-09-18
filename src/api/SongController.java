package api;

import services.SongService;

public class SongController {
	
	private SongService songService = new SongService();

	public SongController() {
		
	}
	
	public String FormatDuration(double duration) {
		try {
			return songService.FormatDuration(duration);
		} catch (Exception e) {
			return null;
		}
	}
}
