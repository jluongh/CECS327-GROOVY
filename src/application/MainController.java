package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.Node;

public class MainController implements Initializable 
{
	@FXML
	private TextField userID;
	@FXML
	private PasswordField password;
	@FXML
	private Label lbUsername;
	@FXML
	private Label lbPassword;
	@FXML
	private Button btnExit;
	@FXML
	private Button btnEnter;

	//TODO: initialize images etc.
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		
		
	}
	
	// Event Listener on Button[#btnEnter].onAction
	@FXML
	public void handleButtonEnter(ActionEvent event) throws IOException 
	{
		// if user enter nothing on the username, display error message 
		if(userID.getText().equals(""))
		{
			Alert error = new Alert(Alert.AlertType.ERROR);
			error.setTitle("invalid input");
			error.setHeaderText("The username cannot be empty");
            error.setContentText("Please enter the username");
		}
		// if user enter nothing on the username, display error message
		else if(password.getText().equals(""))
		{
			Alert error = new Alert(Alert.AlertType.ERROR);
			error.setTitle("invalid input");
			error.setHeaderText("The password cannot be empty");
            error.setContentText("Please enter the password");
		}
		//TODO: check credential using "database"
		else
		{
			Parent Parent = FXMLLoader.load(getClass().getResource("MainApp.fxml"));
            Scene nextScene = new Scene(Parent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setResizable(false);
            window.setScene(nextScene);
            window.setTitle("Groovy");
            window.show();
		}
	}
	
	// Event Listener on Button[#btnExit].onAction
	@FXML
	public void handleButtonExit(ActionEvent event)
	{

			
	}
		

}
