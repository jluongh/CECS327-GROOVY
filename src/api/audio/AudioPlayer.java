package api.audio;

import javax.sound.sampled.*;
import java.io.*;
import java.util.*;

public class AudioPlayer {
	
	//global variables
	public HashMap<Integer, AudioData> soundMap = new HashMap<Integer, AudioData>();
	private AudioData ad;
	public boolean isPlaying;
	public int currentSong;

	/**
	 * Converts an AudioInputStream to PCM_SIGNED format if it is not already either PCM_SIGNED or PCM_UNSIGNED
	 * @param audioInputStream - {AudioInputStream}  song that is being streamed
	 * @return audioInputStream
	 */
	private AudioInputStream convertToPCM(AudioInputStream audioInputStream) {
		AudioFormat format = audioInputStream.getFormat();

		if ((format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED)
				&& (format.getEncoding() != AudioFormat.Encoding.PCM_UNSIGNED)) {
			AudioFormat targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format.getSampleRate(), 16,
					format.getChannels(), format.getChannels() * 2, format.getSampleRate(), format.isBigEndian());
			audioInputStream = AudioSystem.getAudioInputStream(targetFormat, audioInputStream);
		}

		return audioInputStream;
	}

	/**
	 * Loading an audio stream from a file
	 * Giving the audio stream a specified name
	 * Formatting and initializing the audio data info
	 * Putting the audio stream into the hashmap
	 * Returning true if the audio stream loaded successfully else returning false 
	 * @param soundName - {String} the name to give this audio stream
	 * @param audioInputStream - {AudioInputStream} the name of the file to load the audio stream from
	 * @return retVal
	 */
	public boolean loadStream(int soundName, AudioInputStream audioInputStream) {
		boolean retVal = true;

		try {
			// convert the audio input stream to a buffered input stream that supports
			// mark() and reset()
			BufferedInputStream bufferedInputStream = new BufferedInputStream(audioInputStream);
			audioInputStream = new AudioInputStream(bufferedInputStream, audioInputStream.getFormat(),
					audioInputStream.getFrameLength());

			try {
				// convert the AudioInputStream to PCM format -- needed for loading
				// mp3 files and files in other formats
				audioInputStream = convertToPCM(audioInputStream);

				DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioInputStream.getFormat());
				SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);

				AudioData ad = new AudioData();
				ad.audioInputStream = audioInputStream;
				ad.dataLine = sourceDataLine;

				soundMap.put(soundName, ad);
				audioInputStream.mark(2000000000);
				sourceDataLine.open(audioInputStream.getFormat());
			} catch (Exception e) {
				e.printStackTrace();
				retVal = false;
			} finally {
				// ain.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			retVal = false;
		}

		return retVal;
	}

	/**
	 * Plays songs in the list of AudioInputStreams objects
	 * @param streams - {List} the list of AudioInputStreams to be played
	 */
	public void playSongs(List<AudioInputStream> streams) {
		if (streams != null) {
			for (int i = 0; i < streams.size(); i++) {
				System.out.println("Playing");
				currentSong = i;
				AudioInputStream stream = streams.get(i);
				loadStream(currentSong, stream);
				play(currentSong, false);
				// while (isPlaying) {
				// }
				System.out.println("Done with one song");
			}
		}
	}

	/**
	 * Plays a sound that has already been loaded by one of the sound loading methods
	 * The sound can be played once or looped forever
	 * If the specified sound name does not exist, this method does nothing
	 * @param soundName - {String} the name of the sound to play
	 * @param loop - {boolean} determinant if the sound should loop forever or play once
	 */
	public void play(int soundName, boolean loop) {
		ad = soundMap.get(soundName);
		currentSong = soundName;
		if (ad != null) {
			if ((ad.thread == null) || (!ad.thread.isAlive())) {
				if (ad.dataLine instanceof SourceDataLine) {
					PlayStreamThread pt = new PlayStreamThread(ad.audioInputStream, (SourceDataLine) ad.dataLine);
					ad.thread = pt;
				} else {
					return;
				}

				ad.thread.setLooping(loop);
				ad.thread.start();
			} else {
				ad.thread.stopSound();
				ad.thread.setLooping(loop);
				ad.thread.playSound();
			}
		}
	}
	
	/**
	 * Resume the current song
	 * Method to play song at a certain time interval
	 */
	public void resume() {
		resume(currentSong);
	}

	/**
	 * Resume the current song based on the sound name in thread
	 * @param soundName - {int} the name of the sound to resume
	 */
	public void resume(int soundName) {
		currentSong = soundName;
		ad = soundMap.get(soundName);
		if (ad != null) {
			if ((ad.thread == null) || (!ad.thread.isAlive())) {
				if (ad.dataLine instanceof SourceDataLine) {
					PlayStreamThread pt = new PlayStreamThread(ad.audioInputStream, (SourceDataLine) ad.dataLine);
					ad.thread = pt;
				} else {
					return;
				}

				ad.thread.start();
			} else {
//				ad.thread.stopSound();
				ad.thread.resumeSound();
//				ad.thread.playSound();
			}
		}
	}

	/**
	 * Stop the current song
	 * Method to stop song at a certain time interval
	 */
	public void stop() {
		stop(currentSong);
	}

	/**
	 * Stops playing the current song based on the sound name in thread
	 * @param soundName - {String} the name of the sound to stop
	 */
	public void stop(int soundName) {
		currentSong = soundName;
		
		ad = soundMap.get(soundName);
		if (ad != null) {
			if (ad.thread != null) {
				ad.thread.stopSound();
			}
		}
	}

	/**
	 * Stops all playing sounds and closes all lines and audio input streams
	 * Any previously loaded sounds will have to be re-loaded to be played again
	 */
	public void shutdown() {
		for (AudioData ad : soundMap.values()) {
			if (ad != null) {
				if (ad.thread != null) {
					ad.thread.stopSound();
					ad.dataLine.close();
					try {
						ad.audioInputStream.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		soundMap.clear();
	}
}