package app;

/**
 *This is the main class of the application.
 *
 *@author Gibran Garcia
 *@author Hien Pham
 *
 */

import java.io.FileNotFoundException;
import java.io.IOException;

import controller.LoginController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import model.Admin;
import model.User;
import model.UserList;

public class Photos extends Application {

	/**
	 * 
	 * list of users object
	 * 
	 */
	private UserList usersObject;

	/**
	 * 
	 * @param primaryStage Primary stage of application
	 * 
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		//on program execution, try to load serialized Users
		this.usersObject = this.getUsersContainer();

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Login.fxml"));

		AnchorPane root = (AnchorPane)loader.load();

		LoginController Controller = loader.getController();
		Controller.start(primaryStage, this.usersObject);

		Scene scene = new Scene(root, 360, 280);
		primaryStage.setTitle("Photos24 - Made with love");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * 
	 * store the data
	 * 
	 */
	@Override
	public void stop() {
		//serialize our List with all our users
		try {
			UserList.writeUserList(this.usersObject);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param args input arguments
	 * 
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * 
	 * help store the data
	 * 
	 */
	private UserList getUsersContainer() {
		UserList usersContainer = null;
		try {
			usersContainer = UserList.readUserList();
		} catch (FileNotFoundException e) {
			//if no serialized file exists, means it's first time execution
			usersContainer = new UserList();
			//create just an Admin user.
			User adminUser = new Admin();
			//add Admin to UserList
			usersContainer.addUser(adminUser);
			try {
				UserList.writeUserList(usersContainer);
			} catch (IOException f) {
				f.printStackTrace();
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();	
		}
		return usersContainer;
	}


}