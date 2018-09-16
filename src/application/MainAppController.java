package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import api.AudioPlayer;
import api.PlaylistController;
import api.Searcher;
import api.UserProfileController;
import data.models.Album;
import data.models.Artist;
import data.models.Playlist;
import data.models.Song;
import data.models.User;
import data.models.UserProfile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
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
	private TableView<?> Result;
	@FXML
	private TableColumn<?,String> col1;
	@FXML
	private TableColumn<?,String> col2;
	@FXML
	private TableColumn<?,String> col3;
	@FXML
	private TableColumn<?, Double> col4;
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
	
	// Search bar 
	@FXML
	private TextField txtSearch;
	
	private static User currentUser = MainController.getUser();
	private UserProfileController uc = new UserProfileController();
	private UserProfile up= uc.GetUserProfile(currentUser.getUserID());;
	private PlaylistController pc = new PlaylistController(currentUser.getUserID());
	private List<Playlist> playlist = pc.GetPlaylists();
	private ObservableList<Playlist> playlists = FXCollections.observableArrayList();
	private ObservableList<Song> songs = FXCollections.observableArrayList();
	private ObservableList<Artist> artists = FXCollections.observableArrayList();
	private ObservableList<Album> albums = FXCollections.observableArrayList();
	private List<Song> playlistSong = new ArrayList<Song>();
	
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
		
		playlistTable.getSelectionModel().getSelectedCells().toString();
		
		Result = new TableView<Song>();
		col1 = new TableColumn<Song,String>();
		col2 = new TableColumn<Song,String>();
		col3 = new TableColumn<Song,String>();
		col4 = new TableColumn<Song,Double>();
		
	}

	//when user double click the playlist, it will display the songs in the playlist on the right
	@FXML
	public void clickItem(MouseEvent event)
	{
	    if (event.getClickCount() == 2) //Checking double click
	    {
	        playlistTable.getSelectionModel().getSelectedItem().getName();
	        
	    }
	}
	
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
	
	
	private void search(String text, String type) {
		
		if(type == "song") {
			//update result page to search for that song
			
			List<Song> song=search.findFromSongs(text);
			// #2 update the result page from "Xinyi"
			for(int i = 0; i< song.size();i++)
			{
				System.out.println(song);
			}
			
			//setSearchSong(song);
			
		} else if(type == "album") {
			//update result page to search for that album
			
			// #1 use the search function from "Trisha"
			
			List<Album> album=search.findFromAlbums(text);;
			// #2 update the result page from "Xinyi"
			setSearchAlbum(album);
		} else if(type == "artist") {
			//update result page to search for that artist
			
			// #1 use the search function from "Trisha"
			
			List<Artist> artist=search.findFromArtists(text);
			// #2 update the result page from "Xinyi"
			setSearchArtist(artist);
		}
	}
	
	public void setSearchSong(List<Song> song)
	{
		for (int i = 0; i< song.size();i++)
		{
			songs.add(song.get(i));
		}
		Result = new TableView<Song>();
		Result = new TableView<Song>();
		col1 = new TableColumn<Song,String>();
		col2 = new TableColumn<Song,String>();
		col3 = new TableColumn<Song,String>();
		col4 = new TableColumn<Song,Double>();
		col1.setText("Song");
		col2.setText("Artist");
		col3.setText("Album");
		col4.setText("Duration");
		col5.setText("Add");
//
//		col1.setCellValueFactory(cellData ->  new ReadOnlyStringWrapper(((Song) cellData.getValue()).getTitle()));
//		col2.setCellValueFactory(cellData -> new ReadOnlyStringWrapper (((Song) cellData.getValue()).getArtist()));
//		col3.setCellValueFactory(cellData ->  new ReadOnlyStringWrapper(((Song) cellData.getValue()).getAlbum()));
//		col4.setCellValueFactory(cellData ->  new ReadOnlyDoubleWrapper(((Song) cellData.getValue()).getDuration()).asObject());
		
	}
	
	public void setSearchAlbum(List<Album> album)
	{
		for (int i = 0; i< album.size();i++)
		{
			albums.add(album.get(i));
		}
		Result = new TableView<Album>();
		initializeSearchColumn();
		col1.setText("Album");
		col2.setText("Song");
		col3.setText("Artist");
		col4.setText("Duration");
		col5.setText("Add");
		
		//List<Song> albumSong = album.get(i).getSong();
//		col1.setCellValueFactory(cellData ->  new ReadOnlyStringWrapper(((Album) cellData.getValue()).getName()));
//		col2.setCellValueFactory(cellData -> new ReadOnlyStringWrapper (((Album) cellData.getValue()).getSong()));
//		col3.setCellValueFactory(cellData ->  new ReadOnlyStringWrapper(((Album) cellData.getValue()).getArtist()));
//		col4.setCellValueFactory(cellData ->  new ReadOnlyDoubleWrapper(((Song) cellData.getValue()).getDuration()).asObject());
	}
	
	public void setSearchArtist(List<Artist> artist)
	{
		for (int i = 0; i< artist.size();i++)
		{
			artists.add(artist.get(i));
		}
		Result = new TableView<Artist>();
		initializeSearchColumn();
		col1.setText("Artist");
		col2.setText("Song");
		col3.setText("Album");
		col4.setText("Duration");
		col5.setText("Add");
		
		//List<Song> albumSong = album.get(i).getSong();
//		col1.setCellValueFactory(cellData ->  new ReadOnlyStringWrapper(((Artist) cellData.getValue()).getName()));
//		col2.setCellValueFactory(cellData -> new ReadOnlyStringWrapper (((Album) cellData.getValue()).getSong()));
//		col3.setCellValueFactory(cellData ->  new ReadOnlyStringWrapper(((Artist) cellData.getValue()).getAlbums()));
//		col4.setCellValueFactory(cellData ->  new ReadOnlyDoubleWrapper(((Song) cellData.getValue()).getDuration()).asObject());
		
	}
	
	//set center panel to be playlist panel
	public void setTabletoPlaylist()
	{
		col1.setText("Name");
		col2.setText("Artist");
		col3.setText("Album");
		col4.setText("DateAdded");
		col5.setText("Delete");
	}
	
	public void initializeSearchColumn()
	{
		Result = new TableView<Song>();
		col1 = new TableColumn<Song,String>();
		col2 = new TableColumn<Song,String>();
		col3 = new TableColumn<Song,String>();
		col4 = new TableColumn<Song,Double>();
	}

	// Event Listener on ImageView[#addPlaylist].onMouseClicked
	@FXML
	public void addPlaylistClicked(MouseEvent event) 
	{
		showInputBox();
	}
	
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
	
	private class ButtonCelladdSong extends TableCell<Disposer.Record, Boolean> {
        Button cellButton = new Button("Delete");
        
        ButtonCelladdSong()
        {
            
        	//Action when the button is pressed
            cellButton.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent t) 
                {
                    //TODO:handle add action
                	Song currentSong = (Song) ButtonCelladdSong.this.getTableView().getItems().get(ButtonCelladdSong.this.getIndex());
                	//prompt user to enter playlist name they wish to add to
                	TextInputDialog dialog = new TextInputDialog("");
           		 
            		dialog.setTitle("Enter Playlist Name");
            		dialog.setHeaderText("Enter the playlist name:");
            		dialog.setContentText("Name:");
            		Optional<String> result = dialog.showAndWait();
            		//create new playlist
            		if(result.isPresent())
            		{
            			String playlistName = result.get();
            			for(int i = 0; i<playlist.size();i++)
            			{
            				if(playlist.get(i).getName().equals(playlistName))
            				{
            					playlistSong.add(currentSong);
            				}
            			}
            		}
                	
                }
            });
            
        }
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
