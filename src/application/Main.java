package application;
	
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import data.models.*;
import javafx.application.Application;
import javafx.stage.Stage;
import services.PlaylistService;
import services.UserService;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Groovy");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
//		Don't delete please :D
//		AudioPlayer player = new AudioPlayer();
//		List<SongInfo> songInfos = new ArrayList<SongInfo>();
//		Playlist playlist = new Playlist();
//		List<Playlist> playlists = new ArrayList<Playlist>();
//		User u1 = new User();
//		List<User> users = new ArrayList<User>();
//		Song one = new Song();
//		one.setSongID(1);
//		one.setTitle("Everybody Wants to Rule the World");
//		one.setArtist("Tears for Fears");
//		one.setDuration("4:11");
//		one.setAlbum("Songs from the Big Chair");
		
//		SongInfo songInfo = new SongInfo();
//		songInfo.setSong(one);
//		songInfo.setAddedDate(new Date());
//		
//		songInfos.add(songInfo);
//		
//		songInfo.setSong(two);
//		songInfo.setAddedDate(new Date());
//		songInfos.add(songInfo);
////		player.LoadSongs(songs);
////		player.Load(one);
//
//
//		playlist.setCreationDate(new Date());
//		playlist.setPlaylistID(1);
//		playlist.setSongInfos(songInfos);
//		
//		playlists.add(playlist);
//		
//		u1.setUsername("jluongh");
//		u1.setPassword("1234");
//		u1.setPlaylists(playlists);
//		users.add(u1);
//		
//		User u2 = new User();
//		u2.setUsername("user1");
//		u2.setPassword("cecs327");
//		u2.setPlaylists(playlists);
//		users.add(u2);
//
////
//		GsonBuilder builder = new GsonBuilder();
//		Gson gson = builder.create();
//		String test = gson.toJson(users);
//		System.out.println(test);
//		UserService us = new UserService();
//		
//		User user = us.getUser("jluongh");
//		System.out.println(user.getPlaylists().size());
//		
//		PlaylistService ps = new PlaylistService();
//		Playlist playlist = new Playlist();
//		playlist.setCreationDate(new Date());
//		playlist.setPlaylistID(3);
//		playlist.setSongInfos(songInfos);
//		ps.CreatePlaylist(user.getUsername(), playlist);
//		playlist.setPlaylistID(4);
//
//		ps.CreatePlaylist(user.getUsername(), playlist);

		launch(args);
	}
	
	
}
