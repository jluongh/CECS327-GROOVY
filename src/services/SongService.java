package services;

public class SongService {

	public SongService() {
		//no initialization
	}
	
	/**
	 * Method formats duration from double to mm:ss string
	 * @param duration - {double} length of song
	 * @return formattedDuration
	 */
	public String FormatDuration(double duration) {
		if (duration == 0.0) 
			return "";
		String formattedDuration = Double.toString(duration/60000000);
		String[] fd = formattedDuration.split("\\.");
		if (fd[1].length()==1) {
			fd[1] = fd[1] + "0";
		}
		return fd[0] + ":" + fd[1];
	}
}
