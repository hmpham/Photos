package controller;

/**
 *
 *@author Gibran Garcia
 *@author Hien Pham
 *
 */

import java.io.IOException;

import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import model.UserList;
import model.User;
import model.Player;


public class AdminController {
	private Stage primaryStage, previousStage;

	@FXML
	private Button logout, create;

	@FXML
	private TextField username;

	@FXML
	private ListView<User> listView;
	private ObservableList<User> obsList;

	private UserList usersObject;
	private ArrayList<User> usersList;

	/**
	 * 
	 * @param primary stage
	 * @param privious stage
	 * @param list of userObject
	 * @throws throw exception when stage can not be loaded
	 * 
	 */
	public void start(Stage primaryStage, Stage previousStage, UserList usersObject) throws IOException { 
		this.primaryStage = primaryStage;
		this.previousStage = previousStage;

		this.usersObject = usersObject;
		this.usersList = this.usersObject.getUsers();

		this.obsList = FXCollections.observableArrayList();
		for(int i = 0; i < this.usersList.size(); i++){
			this.obsList.add(this.usersList.get(i));
		}
		this.listView.setItems(this.obsList.sorted());

		//default select first user in the list when start the program
		this.listView.getSelectionModel().selectFirst();

	}

	/**
	 * 
	 * log out
	 * @throws throw exception when action can not be done
	 * 
	 */
	@FXML
	void logoutAction(ActionEvent event) throws IOException {
		Stage stage = (Stage) this.primaryStage;
		stage.close();

		this.previousStage.show();
	}

	/**
	 * 
	 * delete user
	 * 
	 */
	@FXML
	public void deleteAction(ActionEvent e) {
		//trigger warning before continue, asking for user confirmation
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete User");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete this user?");
		Optional<ButtonType> result = alert.showAndWait();

		if(result.get() == ButtonType.OK) {
			//user can not delete the original Admin account
			User currUser = this.listView.getSelectionModel().getSelectedItem();
			if(currUser.getUsername().equalsIgnoreCase("admin")) {
				Alert alert1 = new Alert(AlertType.WARNING);
				alert1.setTitle("Warning: Attempting to delete admin");
				alert1.setHeaderText(null);
				alert1.setContentText("You can not delete the Admin account");
				alert1.showAndWait();
			}
			else {
				this.usersList.remove(this.listView.getSelectionModel().getSelectedItem());
				this.obsList.setAll(this.usersList);
				this.listView.setItems(this.obsList.sorted());
				this.listView.getSelectionModel().select(0);
			}
		}
		//do nothing if hit cancel
	}

	/**
	 * 
	 * create new user
	 * 
	 */
	@FXML
	void createAction(ActionEvent event) {
		String newUsername = username.getText();

		//warn user if they want to create new account with empty name
		if(username.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning: Creating empty username");
			alert.setHeaderText(null);
			alert.setContentText("Creating a new user requires a name!");
			alert.showAndWait();
		}
		//check if new user is already exist
		else if(duplicateCase(newUsername)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning: Existing user");
			alert.setHeaderText(null);
			alert.setContentText("The username you are trying to create already exists.");
			alert.showAndWait();
		}
		else {
			User newPlayer = new Player(newUsername.trim());
			//add user to our object being serialized on close.
			this.usersObject.addUser(newPlayer);

			this.obsList.add(newPlayer);
			//need to resort the list after adding a user
			this.listView.setItems(this.obsList.sorted());
			//select new user who was added to the listview
			this.listView.getSelectionModel().select(newPlayer);


			//clear the textbox after successfully adding new user to the list
			username.clear();
		}
	}

	/**
	 * 
	 * @param new user name
	 * @return true if new user name already exist
	 * 
	 */
	private boolean duplicateCase(String Newusername) {
		for(int i = 0; i < this.usersList.size(); i++) {
			User temp = this.usersList.get(i);
			if(Newusername.trim().equalsIgnoreCase(temp.getUsername().trim())) {
				return true;
			}
		}
		return false;
	}
}
