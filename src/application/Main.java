package application;

	
import java.util.*;

import api.AudioPlayer;
import data.models.Song;
import javafx.application.Application;
import javafx.stage.Stage;
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
//		List<Song> songs = new ArrayList<Song>();
//		
//		Song one = new Song();
//		one.setSongID(1);
//		one.setTitle("Everybody Wants to Rule the World");
//		one.setArtist("Tears for Fears");
//		one.setDuration("4:11");
//		one.setAlbum("Songs from the Big Chair");
//		songs.add(one);
//		
//		Song two = new Song();
//		two.setSongID(2);
//		two.setTitle("September");
//		two.setArtist("Earth, Wind & Fire");
//		two.setDuration("3:34");
//		two.setAlbum("The Eternal Dance");
//		songs.add(two);
//		
//		player.LoadSongs(songs);
//		player.Load(one);
		launch(args);
	}
	
	
}
