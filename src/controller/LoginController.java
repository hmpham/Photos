package controller;

/**
 *
 *@author Gibran Garcia
 *@author Hien Pham
 *
 */


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;

import model.UserList;
import model.Player;
import model.User;


public class LoginController {
	@FXML
	TextField username;

	@FXML
	Button login;

	@FXML
	Button delete;

	private Stage primaryStage;

	private UserList usersObject;
	private ArrayList<User> usersList;

	/**
	 * 
	 * @param primary stage
	 * @param list of user object
	 * @throws throw exception when stage can not be loaded
	 * 
	 */
	public void start(Stage primaryStage, UserList usersObject) throws IOException {
		this.primaryStage = primaryStage;
		this.usersObject = usersObject;

		//want to access the actual ArrayList in our UserList object
		this.usersList = this.usersObject.getUsers();

	}

	/**
	 * 
	 * log in aciton
	 * 
	 */
	public void loginAction(ActionEvent e) {

		if(username.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning: Empty username");
			alert.setHeaderText(null);
			alert.setContentText("Username is empty");
			alert.showAndWait();
			return; //login action technically completed
		}

		String name = username.getText().trim();
		//want to compare input with currently available usernames
		for(int i = 0; i < this.usersList.size(); i++) {
			User currUser = this.usersList.get(i);
			if(name.equalsIgnoreCase(currUser.getUsername())) {
				if(currUser.isAdmin()) {
					try {
						loadAdminWindow(this.usersObject);
						return;
					} catch (IOException f) {
						f.printStackTrace();
					}
				}
				else{
					//non-admin user
					try {
						loadUserWindow(currUser);
						return;
					} catch (IOException f) {
						f.printStackTrace();
					}
				}
			}
		}
		// when user does not exist
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning: User is not found");
		alert.setHeaderText(null);
		alert.setContentText("Username is not found, try again!");
		alert.showAndWait();
		return;
	}

	/**
	 * 
	 * @param usersObject
	 * @throws thorw exception in case stage does not start
	 * 
	 */
	private void loadAdminWindow(UserList usersObject) throws IOException{
		this.username.clear();
		this.primaryStage.hide();

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Admin.fxml"));	
		AnchorPane root = (AnchorPane)loader.load();

		Stage newStage = new Stage();

		AdminController Controller = loader.getController();
		Controller.start(newStage, this.primaryStage, this.usersObject);

		Scene scene = new Scene(root);
		newStage.setScene(scene);
		newStage.showAndWait();
	}

	/**
	 * 
	 * @param currUser
	 * @throws thorw exception in case stage does not start
	 * 
	 */
	private void loadUserWindow(User currUser) throws IOException{
		this.username.clear();
		this.primaryStage.hide();

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Player.fxml"));	
		AnchorPane root = (AnchorPane)loader.load();

		Stage newStage = new Stage();

		PlayerController Controller = loader.getController();
		//need to cast our User as a Player to use Player specific methods
		Player currPlayer = (Player) currUser;
		Controller.start(newStage, this.primaryStage, currPlayer);

		Scene scene = new Scene(root);
		newStage.setScene(scene);
		newStage.showAndWait();
	}
}
