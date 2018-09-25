package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import java.util.Date;

import api.AlbumController;
import api.ArtistController;
import api.AudioPlayer;
import api.PlaylistController;
import api.Searcher;
import api.SongController;
import api.UserProfileController;
import data.models.Album;
import data.models.Artist;
import data.models.Playlist;
import data.models.Song;
import data.models.SongInfo;
import data.models.User;
import data.models.UserProfile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import com.sun.prism.impl.Disposer;

public class MainAppController implements Initializable {
	@FXML
	private ListView playlistScreen;
	@FXML
	private ImageView addPlaylist;
	@FXML
	private ImageView playMusic;
	@FXML
	private ImageView previousMusic;
	@FXML
	private ImageView nextMusic;
	@FXML
	private ImageView stopMusic;
	@FXML
	private ImageView Mute;
	@FXML
	private Text songName;
	@FXML
	private Text artistName;
	@FXML
	private Label userNameText;
	@FXML
	private Slider sldVolume;
	@FXML
	private TableView<Object> Result;
	@FXML
	private TableColumn<Object,String> col1;
	@FXML
	private TableColumn<Object,String> col2;
	@FXML
	private TableColumn<Object,String> col3;
	@FXML
	private TableColumn<Object, String> col4;
	@FXML
	private TableColumn<Disposer.Record,Boolean> col5;
	@FXML 
	private TableView<Playlist> playlistTable;
	@FXML 
	private TableColumn<Disposer.Record,Boolean> delete;
	@FXML
	private TableColumn<Playlist,String> playlistName;
	
	// search buttons
	@FXML
	private ImageView btnAlbum;
	@FXML
	private ImageView btnSong;
	@FXML
	private ImageView btnArtist;
	private boolean isSearch = false;
	
	// Search bar 
	@FXML
	private TextField txtSearch;
	//hi
	
	private static User currentUser = MainController.getUser();
	private UserProfileController uc = new UserProfileController();
	private UserProfile up= uc.GetUserProfile(currentUser.getUserID());;
	private PlaylistController pc = new PlaylistController(currentUser.getUserID());
	private List<Playlist> playlist = pc.GetPlaylists();
	private ObservableList<Playlist> playlists = FXCollections.observableArrayList();
    private ObservableList<Object> userSong = FXCollections.observableArrayList();
	private ObservableList<Object> songs = FXCollections.observableArrayList();
	private ObservableList<Object> artistSong = FXCollections.observableArrayList();
	private ObservableList<Object> albumSong = FXCollections.observableArrayList();
	private AlbumController amc = new AlbumController();
	private ArtistController atc = new ArtistController();
	private SongController sc = new SongController();
	
	// Audio player
	AudioPlayer player = new AudioPlayer();
	Searcher search = new Searcher();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		// Change the label to the username
		userNameText.setText(currentUser.getUsername());
		setTabletoPlaylist();
		for (int i = 0; i< playlist.size();i++)
		{
			playlists.add(playlist.get(i));
		}
		
		//display playlist name on sidebar
		playlistName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
		
        //Insert Button
		delete.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Disposer.Record, Boolean>, ObservableValue<Boolean>>() {
		
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Disposer.Record, Boolean> p) 
            {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
            
		});
		//display delete button
		delete.setCellFactory(new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {

            @Override
            public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
                return new ButtonCelldeletePlaylist();
            }
        
        });
		
		playlistTable.setItems(playlists);
	}

	//when user double click the playlist, it will display the songs in the playlist on the right
	@FXML
	public void clickItem(MouseEvent event)
	{
		isSearch=false;
	    if (event.getClickCount() == 2) //Checking double click
	    {
	    	setTabletoPlaylist();
	    	for(int i = 0; i<Result.getItems().size();i++)
			{
				Result.getItems().clear();
			}
	        Playlist userChoose = playlistTable.getSelectionModel().getSelectedItem();
	        List<Song> songPlay = new ArrayList<Song>();
	        ArrayList<SongInfo> songin = (ArrayList<SongInfo>) userChoose.getSongInfos();
	        if (songin!=null && !songin.isEmpty())
	        {
	        	for (int i = 0; i<userChoose.getSongInfos().size();i++)
		        {
		        	songPlay.add(userChoose.getSongInfos().get(i).getSong());
		        	userSong.add(userChoose.getSongInfos().get(i));
		        	
		        }
		        //load playlist to the audio player
		        player.LoadSongs(songPlay);
		        //display object to the table
				col1.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(((SongInfo) cellData.getValue()).getSong().getTitle()));
				col2.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(atc.GetArtistBySongTitle(((SongInfo) cellData.getValue()).getSong().getTitle()).getName()));
				col3.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(amc.GetAlbumBySongTitle(((SongInfo) cellData.getValue()).getSong().getTitle()).getName()));
				col4.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(((SongInfo) cellData.getValue()).getAddedDate().toString()));
				col5.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Disposer.Record, Boolean>, ObservableValue<Boolean>>() 
				{
					
		            @Override
		            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Disposer.Record, Boolean> p) 
		            {
		                return new SimpleBooleanProperty(p.getValue() != null);
		            }
		            
				});
				col5.setCellFactory(new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() 
				{

		            @Override
		            public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p)
		            {
		                return new ButtonCellPlaySong();
		            }
		        
		        });
				
				Result.setItems(userSong);
				Result.refresh();
	        }
	        
	    }
	}
	
	//make if song button is clicked then
	@FXML
	public void btnSongClick(MouseEvent event) 
	{
		search(txtSearch.getText(), "song");
		
	}
	// make if album button is clicked then 
	@FXML
	public void btnAlbumClick(MouseEvent event) throws IOException 
	{
		search(txtSearch.getText(), "album");
	}
	
	// make if artist button is clicked then 
	@FXML
	public void btnArtistClick(MouseEvent event) throws IOException 
	{
		search(txtSearch.getText(), "artist");
	}
	
	//when row in table isClicked
	@FXML
	public void playTable(MouseEvent event)
	{
		if (event.getClickCount() == 2) //Checking double click
	    {
			if(isSearch== true)
			{
				Song userSong = (Song) Result.getSelectionModel().getSelectedItem();
				player.Load(userSong);
				player.Play();
				songName.setText(userSong.getTitle());
				String artist = atc.GetArtistBySongTitle(userSong.getTitle()).getName();
				artistName.setText(artist);
			}
			else
			{
				SongInfo userSong = (SongInfo) Result.getSelectionModel().getSelectedItem();
				player.Load(userSong.getSong());
				player.Play();
				songName.setText(userSong.getSong().getTitle());
				String artist = atc.GetArtistBySongTitle(userSong.getSong().getTitle()).getName();
				artistName.setText(artist);
			}
			
	    }
	}
	
	private void search(String text, String type) {
		isSearch=true;
		if(type == "song") {
			//update result page to search for that song
			List<Song> song=search.findFromSongs(text);
			setSearchSong(song);
		} else if(type == "album") {
			//update result page to search for that album
			List<Album> album=search.findFromAlbums(text);;
			setSearchAlbum(album);
		} else if(type == "artist") {
			//update result page to search for that artist			
			List<Artist> artist=search.findFromArtists(text);
			setSearchArtist(artist);
		}
	}
	
	//search song according to song name
	public void setSearchSong(List<Song> song)
	{
		for(int i = 0; i<Result.getItems().size();i++)
		{
			Result.getItems().clear();
		}
		Result.refresh();
		for (int i = 0; i< song.size();i++)
		{
			songs.add(song.get(i));
		}
		col1.setText("Song");
		col2.setText("Artist");
		col3.setText("Album");
		col4.setText("Duration");
		col5.setText("Add");
		//display object to the table
		col1.setCellValueFactory(cellData ->  new ReadOnlyStringWrapper(((Song) cellData.getValue()).getTitle()));
		col2.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(atc.GetArtistBySongTitle(((Song) cellData.getValue()).getTitle()).getName()));
		col3.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(amc.GetAlbumBySongTitle(((Song) cellData.getValue()).getTitle()).getName()));
		col4.setCellValueFactory(cellData ->  new ReadOnlyStringWrapper(sc.FormatDuration(((Song) cellData.getValue()).getDuration())));
		
		col5.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Disposer.Record, Boolean>, ObservableValue<Boolean>>() {
			
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Disposer.Record, Boolean> p) 
            {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
            
		});
		//display delete button
		col5.setCellFactory(new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {

            @Override
            public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
                return new ButtonCelladdSong();
            }
        
        });
		
		Result.setItems(songs);
		Result.refresh();
	}
	
	//search by album
	public void setSearchAlbum(List<Album> album)
	{
		for(int i = 0; i<Result.getItems().size();i++)
		{
			Result.getItems().clear();
		}
		Result.refresh();
		for (int i = 0; i< album.size();i++)
		{
			for (int j = 0; j<album.get(i).getSongs().size();j++)
			{
				albumSong.add(album.get(i).getSongs().get(j));
			}
		}
		col1.setText("Album");
		col2.setText("Song");
		col3.setText("Artist");
		col4.setText("Duration");
		col5.setText("Add");
		//display object to the table
		col1.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(amc.GetAlbumBySongTitle(((Song) cellData.getValue()).getTitle()).getName()));
		col2.setCellValueFactory(cellData -> new ReadOnlyStringWrapper (((Song) cellData.getValue()).getTitle()));
		col3.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(atc.GetArtistBySongTitle(((Song) cellData.getValue()).getTitle()).getName()));
		col4.setCellValueFactory(cellData ->  new ReadOnlyStringWrapper(sc.FormatDuration(((Song) cellData.getValue()).getDuration())));
		
		col5.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Disposer.Record, Boolean>, ObservableValue<Boolean>>() {
			
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Disposer.Record, Boolean> p) 
            {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
            
		});
		//display delete button
		col5.setCellFactory(new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {

            @Override
            public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
                return new ButtonCelladdSong();
            }
        
        });
		Result.setItems(albumSong);
		Result.refresh();
	}
	
	//search according to artist
	public void setSearchArtist(List<Artist> artist)
	{
		for(int i = 0; i<Result.getItems().size();i++)
		{
			Result.getItems().clear();
		}
		Result.refresh();
		for (int i = 0; i< artist.size();i++)
		{
			for(int j = 0; j < artist.get(i).getAlbums().size();j++)
			{
				for(int k = 0; k< artist.get(i).getAlbums().get(j).getSongs().size();k++)
				{
					artistSong.add(artist.get(i).getAlbums().get(j).getSongs().get(k));
				}
			}
		}
		col1.setText("Artist");
		col2.setText("Song");
		col3.setText("Album");
		col4.setText("Duration");
		col5.setText("Add");
		//display object to the table
		col1.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(atc.GetArtistBySongTitle(((Song) cellData.getValue()).getTitle()).getName()));
		col2.setCellValueFactory(cellData -> new ReadOnlyStringWrapper (((Song) cellData.getValue()).getTitle()));
		col3.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(amc.GetAlbumBySongTitle(((Song) cellData.getValue()).getTitle()).getName()));
		col4.setCellValueFactory(cellData ->  new ReadOnlyStringWrapper(sc.FormatDuration(((Song) cellData.getValue()).getDuration())));
		System.out.print(sc.FormatDuration(artist.get(0).getAlbums().get(0).getSongs().get(0).getDuration()));
		col5.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Disposer.Record, Boolean>, ObservableValue<Boolean>>() {
			
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Disposer.Record, Boolean> p) 
            {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
            
		});
		//display delete button
		col5.setCellFactory(new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {

            @Override
            public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
                return new ButtonCelladdSong();
            }
        
        });
		Result.setItems(artistSong);
		Result.refresh();
	}
	
	//set center panel to be playlist panel
	public void setTabletoPlaylist()
	{
		col1.setText("Name");
		col2.setText("Artist");
		col3.setText("Album");
		col4.setText("DateAdded");
		col5.setText("Play");
	}
	

	// Event Listener on ImageView[#addPlaylist].onMouseClicked
	@FXML
	public void addPlaylistClicked(MouseEvent event) 
	{
		showInputBox();
	}
	
	//prompt user to input new playlist name
	public void showInputBox()
	{
		TextInputDialog dialog = new TextInputDialog("");
		 
		dialog.setTitle("Enter Playlist Name");
		dialog.setHeaderText("Enter the playlist name:");
		dialog.setContentText("Name:");
		Optional<String> result = dialog.showAndWait();
		//create new playlist
		if(result.isPresent())
		{
			String playlistName = result.get();
			playlists.removeAll(playlists);
			pc.CreatePlaylist(playlistName);
			playlist=pc.GetPlaylists();
			for(int i = 0; i<playlist.size();i++)
			{
				playlists.add(playlist.get(i));
			}
			playlistTable.refresh();
		}
	}
	
	// Event Listener on ImageView[#playMusic].onMouseClicked
	@FXML
	public void playMusicClicked(MouseEvent event) 
	{
		player.Play();
	}
	// Event Listener on ImageView[#previousMusic].onMouseClicked
	@FXML
	public void previousIsClicked(MouseEvent event) 
	{
		player.Previous();
	}
	// Event Listener on ImageView[#nextMusic].onMouseClicked
	@FXML
	public void nextIsClicked(MouseEvent event) 
	{
		player.Next();
	
	}
	// Event Listener on ImageView[#stopMusic].onMouseClicked
	@FXML
	public void musicStopClicked(MouseEvent event) 
	{
		player.Pause();
	}
	// Event Listener on ImageView[#Mute].onMouseClicked
	@FXML
	public void muteIsClicked(MouseEvent event) 
	{
		player.setVolume(0);
	}
	// Event Listener on ImageView[#exit].onMouseClicked

	//delete button constructor
	private class ButtonCelldeletePlaylist extends TableCell<Disposer.Record, Boolean> {
        Button cellButton = new Button("Delete");
        
        ButtonCelldeletePlaylist()
        {
            
        	//Action when the button is pressed
            cellButton.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent t) 
                {
                    //TODO:handle delete action
                	Playlist currentPlaylist = (Playlist) ButtonCelldeletePlaylist.this.getTableView().getItems().get(ButtonCelldeletePlaylist.this.getIndex());
                	pc.DeletePlaylist(currentPlaylist.getPlaylistID());
                	playlists.remove(currentPlaylist);
                	playlistTable.refresh();
                }
            });
            
        }
        //display the button when there is an object associate with it
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
            }else {
        setText(null);
        setGraphic(null);
            }
        }
	}
	
	//play song button constructor
	private class ButtonCellPlaySong extends TableCell<Disposer.Record, Boolean> {
        Button cellButton = new Button("Play");
        
        ButtonCellPlaySong()
        {
            
        	//Action when the button is pressed
            cellButton.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent t) 
                {
                	Song currentsong = (Song) ButtonCellPlaySong.this.getTableView().getItems().get(ButtonCellPlaySong.this.getIndex());
                	player.Load(currentsong);
        			player.Play();
        			songName.setText(currentsong.getTitle());
        			String artist = atc.GetArtistBySongTitle(currentsong.getTitle()).getName();
        			artistName.setText(artist);
                }
            });
            
        }
        //display the button when there is an object associate with it
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
            }else {
        setText(null);
        setGraphic(null);
            }
        }
	}
	
	//add song button constructor
	private class ButtonCelladdSong extends TableCell<Disposer.Record, Boolean> {
        Button cellButton = new Button("Add");
        
        ButtonCelladdSong()
        {
            
        	//Action when the button is pressed
            cellButton.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent t) 
                {
                	
                	// We will be implementing a drop down box * NOT Working yet*
                	
                	Song currentSong = (Song) ButtonCelladdSong.this.getTableView().getItems().get(ButtonCelladdSong.this.getIndex());
                	
                	String[] dropDown= {" test"};
                	for(int i = 0; i<playlist.size();i++)
        			{
                		dropDown[i] = playlist.get(i).getName().toString();
        			}
                	
                	String dropInput = (String) JOptionPane.showInputDialog(null, "Please select a playlist to add the song to:",
                	        "Choice which playlist", JOptionPane.QUESTION_MESSAGE, null, dropDown, dropDown[1]); // Initial choice
                	
            		{
            			
            			boolean found = false;
            			//find if the playlist user entered exist
            			for(int i = 0; i<playlist.size();i++)
            			{
            				//if yes, add the song to the playlist
            				if(playlist.get(i).getName().equals(dropInput))
            				{
            					Date date = new Date();
            					SongInfo newSong = new SongInfo(currentSong, date);
            					pc.AddToPlaylistBySongInfo(playlist.get(i).getPlaylistID(), newSong);
            					found = true;
            				}
            			}
            			//if not, give error message to user
            			if (found ==false)
            			{
            				Alert error = new Alert(Alert.AlertType.ERROR);
            				error.setTitle("Invalid Playlist Name");
            				error.setHeaderText("You enter a playlist name that does not exist");
            	            error.setContentText("Please try again.");
            	            Stage errorStage = (Stage) error.getDialogPane().getScene().getWindow();
            	            error.showAndWait();
            			}
            		}
            		

                	    
                	    
                	    
//                	
//                	Song currentSong = (Song) ButtonCelladdSong.this.getTableView().getItems().get(ButtonCelladdSong.this.getIndex());
//                	//prompt user to enter playlist name they wish to add to
//                	TextInputDialog dialog = new TextInputDialog("");
//           		 
//            		dialog.setTitle("Enter Playlist Name");
//            		dialog.setHeaderText("Enter the playlist name:");
//            		dialog.setContentText("Name:");
//            		Optional<String> result = dialog.showAndWait();
//            		//create new playlist
//            		if(result.isPresent())
//            		{
//            			String playlistName = result.get();
//            			boolean found = false;
//            			//find if the playlist user entered exist
//            			for(int i = 0; i<playlist.size();i++)
//            			{
//            				//if yes, add the song to the playlist
//            				if(playlist.get(i).getName().equals(playlistName))
//            				{
//            					Date date = new Date();
//            					SongInfo newSong = new SongInfo(currentSong, date);
//            					pc.AddToPlaylistBySongInfo(playlist.get(i).getPlaylistID(), newSong);
//            					found = true;
//            				}
//            			}
//            			//if not, give error message to user
//            			if (found ==false)
//            			{
//            				Alert error = new Alert(Alert.AlertType.ERROR);
//            				error.setTitle("Invalid Playlist Name");
//            				error.setHeaderText("You enter a playlist name that does not exist");
//            	            error.setContentText("Please try again.");
//            	            Stage errorStage = (Stage) error.getDialogPane().getScene().getWindow();
//            	            error.showAndWait();
//            			}
//            		}
                	
                }
            });
        }
        //display the button when there is an object associate with it
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
            }else {
        setText(null);
        setGraphic(null);
            }
        }
	}
	
	
	public void updateInfo() {
		
		// Have not tested this!
		songName = new Text(player.currentSong.getTitle());

		
		// The function in song.java is commented out
		
		//artistName = new Text(player.currentSong.getArtist());
	}
	
	
	// The volume slider is not working yet 
	@FXML
	public void onSliderChanged(MouseEvent event) {

	    sldVolume.valueProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
            	
            	float sliderValue = (float) sldVolume.getValue();
            	
            	player.setVolume(sliderValue);
                System.out.println("here: "+ sliderValue );
            }
        });
	    
	    
	}
		
	
	
}
