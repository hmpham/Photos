package controller;

/**
 *
 *@author Gibran Garcia
 *@author Hien Pham
 *
 */

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import model.Player;
import model.Album;

public class CreateAlbumController {


	@FXML
	private TextField albumName;

	@FXML
	private Button cancel, create;

	private Album newAlbum;

	private Stage primaryStage;

	private Player currPlayer;

	/**
	 * 
	 * @param primary stage
	 * @param current user
	 * @param new album
	 * @throws throw exception when stage can not be loaded
	 * 
	 */
	public void start(Stage primaryStage, Player currPlayer, Album newAlbum) throws IOException {
		this.primaryStage = primaryStage;
		this.currPlayer = currPlayer;
		this.newAlbum = newAlbum;
	}

	/**
	 * 
	 * cancel create new album action
	 * 
	 */
	@FXML
	void cancelAction(ActionEvent event) {
		Stage stage = (Stage) this.primaryStage;
		stage.close();
	}

	/**
	 * 
	 * create new album
	 * 
	 */
	@FXML
	void createAction(ActionEvent event) {

		//make sure input wasn't empty
		if(this.albumName.getText().isEmpty()) {
			Alert alert1 = new Alert(AlertType.WARNING);
			alert1.setTitle("Warning: Empty album name");
			alert1.setHeaderText(null);
			alert1.setContentText("Can't create an album with no name.");
			alert1.showAndWait();
			return;
		}

		//make sure name doesn't already exist
		for(int i = 0; i < this.currPlayer.getAlbumList().size(); i++) {
			String currAlbumName = this.currPlayer.getAlbumList().get(i).getName().trim();
			String newName = this.albumName.getText().trim();
			if(newName.equals(currAlbumName)) {
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("Warning: Name in use");
				alert2.setHeaderText(null);
				alert2.setContentText("An album with that name already exists.");
				alert2.showAndWait();
				return;
			}
		}

		//here we are in the clear to make the album!
		this.newAlbum.setName(this.albumName.getText().trim());

		//finally: close this window
		Stage stage = (Stage) this.primaryStage;
		stage.close();
	}


}