package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

import api.UserController;
import data.models.User;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.Node;

public class MainController 
{
	//global variables
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
	private static User currentUser;
	
	/**
	 * Event Listener on Button[#btnEnter].onAction to handle opening of the application
	 * Displaying the initial page for logging into profile
	 * Displaying errors for invalid inputs for username and password
	 * Continuing to the next stage if the user validation is correct
	 * @param event - {ActionEvent} the action the user takes
	 * @throws IOException if input or output is invalid.
	 */
	@FXML
	public void handleButtonEnter(ActionEvent event) throws IOException 
	{
		UserController uv = new UserController();
		// if user enter nothing on the username, display error message 
		if(!uv.isValid(userID.getText()))
		{
			Alert error = new Alert(Alert.AlertType.ERROR);
			error.setTitle("invalid input");
			error.setHeaderText("The username cannot be empty");
            error.setContentText("Please enter the username");
            Stage errorStage = (Stage) error.getDialogPane().getScene().getWindow();
            error.showAndWait();
            
		}
		// if user enter nothing on the username, display error message
		else if(!uv.isValid(password.getText()))
		{
			Alert error = new Alert(Alert.AlertType.ERROR);
			error.setTitle("invalid input");
			error.setHeaderText("The password cannot be empty");
            error.setContentText("Please enter the password");
            Stage errorStage = (Stage) error.getDialogPane().getScene().getWindow();
            error.showAndWait();
		}
		//if the user enter the correct credential, transit into new stage: the main application
		else if(uv.isValidCredentials(userID.getText(), password.getText()))
		{
			currentUser = uv.getUser(userID.getText());
			Parent Parent = FXMLLoader.load(getClass().getResource("MainApp.fxml"));
            Scene nextScene = new Scene(Parent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setResizable(false);
            window.setScene(nextScene);
            window.setTitle("Groovy");
            window.show();
		}
		//if user enter wrong credential, throw error message box
		else
		{
			Alert error = new Alert(Alert.AlertType.ERROR);
			error.setTitle("invalid credential");
			error.setHeaderText("You have inputted invalid credential");
            error.setContentText("Please enter the correct username and/or password");
            Stage errorStage = (Stage) error.getDialogPane().getScene().getWindow();
            error.showAndWait();
		}
	}
	
	/**
	 * Getter method for the user
	 * @return currentUser
	 */
	public static User getUser()
	{
		return currentUser;
	}
	
	/**
	 * Event Listener on Button[#btnExit].onAction to handle the closing of the application
	 * Removes stage being displayed
	 * @param event - {ActionEvent} the action the user takes
	 */
	@FXML
	public void handleButtonExit(ActionEvent event)
	{
		//get the current stage of the application
		Stage stage = (Stage) btnExit.getScene().getWindow();
		stage.close();
	}
		

}
