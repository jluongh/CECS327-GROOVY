package api.audio;

import javax.sound.sampled.*;
import java.io.*;
import java.util.*;

public class AudioPlayer {
	private HashMap<String, AudioData> soundMap = new HashMap<String, AudioData>();
	private AudioData ad;
	public boolean isPlaying;
	public String currentSong;
	/**
	 * Converts an AudioInputStream to PCM_SIGNED format if it is not already either
	 * PCM_SIGNED or PCM_UNSIGNED.
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
	 * Loads an audio stream from a file and gives it the specified name. This name
	 * can be used when calling the <code>{@link #play play}</code> and
	 * <code>{@link #stop stop}</code> methods.
	 *
	 * @param soundName
	 *                      the name to give this audio stream
	 * @param filename
	 *                      the name of the file to load the audio stream from
	 *
	 * @return <code>true</code> if the audio stream loaded successfully,
	 *         <code>false</code> otherwise
	 */

	public boolean loadStream(String soundName, AudioInputStream audioInputStream) {
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
	
	public void playSongs(List<AudioInputStream> streams) {
		if (streams != null) {
			for(int i =0; i < streams.size(); i++) {
				System.out.println("Playing");
				currentSong = Integer.toString(i);
				AudioInputStream stream = streams.get(i);
				loadStream(currentSong, stream);
				play(currentSong, false);
				while (isPlaying) {
				}
				System.out.println("Done with one song");
			}
		}
	}

	/**
	 * Plays a sound that has already been loaded by one of the sound loading
	 * methods. The sound can be played once or looped forever. If the specified
	 * sound name does not exist, this method does nothing.
	 *
	 * @param soundName
	 *                      the name of the sound to play
	 * @param loop
	 *                      <code>true</code> if the sound should loop forever,
	 *                      <code>false</code> if the sound should play once
	 */
	public void play(String soundName, boolean loop) {
		ad = soundMap.get(soundName);
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
			while(ad.thread.isAlive()) {
				isPlaying = true;
			}
		}
		isPlaying = false;
	}
	
	public void resume() {
		resume(currentSong);
	}
	
	public void resume(String soundName) {
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
				ad.thread.stopSound();
				ad.thread.resumeSound();
				ad.thread.playSound();
			}
		}
	}

	public void stop() {
		stop(currentSong);
	}
	
	/**
	 * Stops playing the specified sound.
	 *
	 * @param soundName
	 *                      the name of the sound to stop
	 */
	public void stop(String soundName) {
		ad = soundMap.get(soundName);
		if (ad != null) {
			if (ad.thread != null) {
				ad.thread.stopSound();
			}
		}
	}

	/**
	 * Stops all playing sounds and closes all lines and audio input streams. Any
	 * previously loaded sounds will have to be re-loaded to be played again.
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