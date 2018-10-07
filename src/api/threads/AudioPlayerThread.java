package api.threads;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.*;


import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import data.models.FileEvent;
import data.models.Song;

public class AudioPlayerThread extends Thread {
	protected DatagramSocket socket = null;
	protected BufferedReader br = null;
	protected boolean morePlaylists = true;

	// global variables
	public Song currentSong;
	private Clip clip;
	ListIterator<Song> iterator;
	private boolean isPlaying;

	public AudioPlayerThread() throws IOException {
		this("AudioPlayerThread");
	}

	public AudioPlayerThread(String name) throws IOException {
		super(name);
		socket = new DatagramSocket(4446);
	}

	public void run() {
		while (true) {
			try {
				byte[] buf = new byte[1024 * 1000 * 50];
				int sizeOfPacket = 63000;
				// receive request
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);

				// display response
				String received = new String(packet.getData(), 0, packet.getLength());
				System.out.println("Server:\n" + received);

				if (received != null) {
					// read buffer -- songs to play
					List<Song> songs = LoadSongs(received);

					if (songs != null) {
						for (int i = 0; i < songs.size(); i++) {
							try {
								
								byte[] data = getFileEvent(songs.get(i).getSongID());
//								byte[] (int idOfPacket + int positionOfPacket + int numberOfPackets) + byte[] data
								
								
								// divide the data into chunks
								int packetcount = data.length / sizeOfPacket;
								int start = 0;
								
								for (int j = 1; j <= packetcount; j++) {
									int end = sizeOfPacket * j;
									
									byte[] id = ByteBuffer.allocate(4).putInt(1).array();
									byte[] offset = ByteBuffer.allocate(4).putInt(j).array();
									System.out.println("Offset: " + j);
									byte[] count = ByteBuffer.allocate(4).putInt(packetcount).array();
									byte[] fragment = Arrays.copyOfRange(data, start, end);
									System.out.println("Fragment length: " + fragment.length + " --- " + start + " - " + end);

									ByteArrayOutputStream baos = new ByteArrayOutputStream();
									baos.write(id);
									baos.write(offset);
									baos.write(count);
									baos.write(fragment);

									byte send[] = baos.toByteArray();

									start = end;
									
									InetAddress address = packet.getAddress();
									int port = packet.getPort();
									
									packet = new DatagramPacket(send, send.length, address, port);
									socket.send(packet);
//									System.out.println("File sent from server");
									
									if (j % 100 == 0) {
								         Thread.sleep(1000);

									}
								}
								
								
								System.out.println("Server: Done");
								
								
								
							} catch (FileNotFoundException e) {
								System.err.println("Could not open quote file. Serving time instead.");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}
					// br.close();
				}
			} catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
				// socket.close();
			}
		}
	}

	public byte[] getFileEvent(int songID) {
		FileEvent fileEvent = new FileEvent();

		String fileName = songID + "";
		String path = "music/" + fileName + ".wav";
		fileEvent.setPath(path);
		fileEvent.setFilename(fileName);
		File file = new File(path);
		if (file.isFile()) {
			try {
				DataInputStream diStream = new DataInputStream(new FileInputStream(file));
				long len = (int) file.length();
				byte[] fileBytes = new byte[(int) len];
				int read = 0;
				int numRead = 0;
				while (read < fileBytes.length
						&& (numRead = diStream.read(fileBytes, read, fileBytes.length - read)) >= 0) {
					read = read + numRead;
				}
				fileEvent.setFileSize(len);
				fileEvent.setFileData(fileBytes);
				fileEvent.setStatus("Success");
//				System.out.println("Success");
				
				return fileBytes;

			} catch (Exception e) {
				e.printStackTrace();
				fileEvent.setStatus("Error");
			}
		} else {
			System.out.println("path specified is not pointing to a file");
			fileEvent.setStatus("Error");
		}
		return null;
	}

	public static byte[] serialize(Object obj) throws IOException {
		Gson gson = new GsonBuilder().create();
		String objString = gson.toJson(obj);
		return objString.getBytes();
	}

	/**
	 * Iterator for songs
	 * 
	 * @param received
	 *                     - string from packet
	 */
	protected List<Song> LoadSongs(String received) {
		Gson gson = new GsonBuilder().create();
		List<Song> songs = new ArrayList<Song>();
		songs = gson.fromJson(received, new TypeToken<List<Song>>() {
		}.getType());
		// this.iterator = songs.listIterator();
		return songs;
	}

	/**
	 * Searching and loading song to be played
	 * 
	 * @param song
	 *                 - {Song} song to be played
	 */
	protected void Load(Song song) {
		currentSong = song;
		String filename = "music/" + currentSong.getSongID() + ".wav";
		try {

			if (isPlaying) {
				Reset();
			}
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File(filename)));

		} catch (LineUnavailableException e) {
			System.out.println("Audio Error");
		} catch (IOException e) {
			System.out.println("File Not Found");
		} catch (UnsupportedAudioFileException e) {
			System.out.println("Wrong File Type");
		}
	}

	/**
	 * Stopping song in order to play new song
	 */
	private void Reset() {
		clip.stop();
		clip.setMicrosecondPosition(0);
		isPlaying = false;
	}

	/**
	 * Playing song
	 */
	public void Play() {
		System.out.println("Play");
		clip.start();
		isPlaying = true;
	}

	/**
	 * Pausing song
	 */
	public void Pause() {
		System.out.println("Pause");
		clip.stop();
		isPlaying = false;
	}

	/**
	 * Stopping song in order to play new song
	 * 
	 * @return iterator
	 */
	public boolean HasNext() {
		return iterator.hasNext();
	}

	/**
	 * Stopping song in order to play new song
	 */
	public void Next() {
		if (iterator.hasNext()) {
			System.out.println("Next");
			Reset();
			Load(iterator.next());
			Play();
		}
	}

	/**
	 * Iterator has previous song
	 * 
	 * @return iterator
	 */
	public boolean HasPrevious() {
		return iterator.hasPrevious();
	}

	/**
	 * Play previous song
	 */
	public void Previous() {
		if (iterator.hasPrevious()) {
			System.out.println("Previous");
			Reset();
			Load(iterator.previous());
			Play();
		}
	}

	/**
	 * Get the volume
	 * 
	 * @return gainControl
	 */

	public float getVolume() {
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		return (float) Math.pow(10f, gainControl.getValue() / 20f);
	}

	/**
	 * Changing the volume
	 * 
	 * @param volume
	 *                   - {float} the sound
	 */
	public void setVolume(float volume) {
		if (volume < 0f || volume > 1f)
			throw new IllegalArgumentException("Volume not valid: " + volume);
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(20f * (float) Math.log10(volume));
	}
}