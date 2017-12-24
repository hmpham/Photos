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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import model.Player;
import model.Album;

public class RenameAlbumController {
	@FXML
	private TextField newAlbumName;
	
	@FXML
	private Button cancel, rename;

	private Stage primaryStage;
	
	private Player currPlayer;
	
	private Album activeAlbum;
	
	/**
	 * 
	 * @param primary stage
	 * @param current user
	 * @param album currently selected
	 * @throws throw exception when stage can not be loaded
	 * 
	 */
	public void start(Stage primaryStage, Player currPlayer, Album activeAlbum) throws IOException {
		this.primaryStage = primaryStage;
		this.currPlayer = currPlayer;
		this.activeAlbum = activeAlbum;
	}

	/**
	 * 
	 * cancel action
	 * 
	 */
	@FXML
	void cancelAction(ActionEvent event) {
		Stage stage = (Stage) this.primaryStage;
		stage.close();
	}
	
	/**
	 * 
	 * rename action
	 * 
	 */
	@FXML
	void renameAlbumAction(ActionEvent event) {
		
		//make sure input wasn't empty
		if(this.newAlbumName.getText().isEmpty()) {
			Alert alert1 = new Alert(AlertType.WARNING);
			alert1.setTitle("Warning: Empty album name");
			alert1.setHeaderText(null);
			alert1.setContentText("Can't change an album name to nothing.");
			alert1.showAndWait();
			return;
		}

		//make sure name doesn't already exist
		for(int i = 0; i < this.currPlayer.getAlbumList().size(); i++) {
			String currName = this.currPlayer.getAlbumList().get(i).getName().trim();
			if(this.newAlbumName.getText().trim().equals(currName) ) {
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("Warning: Name in use");
				alert2.setHeaderText(null);
				alert2.setContentText("An album with that name already exists.");
				alert2.showAndWait();
				return;
			}
		}
		
		this.activeAlbum.setName(newAlbumName.getText().trim());
		
		Stage stage = (Stage) this.primaryStage;
		stage.close();
	}
	

}

