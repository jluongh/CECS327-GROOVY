package application;
	
import java.io.IOException;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	
	/**
	 * Loading the main.fxml file
	 * Setting and showing the primary stage 
	 * @param primaryStage- {Stage} main stage
	 */
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
	
	/**
	 * Lauching the application
	 * @param args - arguments
	 */
	public static void main(String[] args) {

		
//		PlaylistService ps = new PlaylistService(0);
//		ps.CreatePlaylist("Sample");
//		ps.DeletePlaylist(0);
		launch(args);
	}
	
	
}
