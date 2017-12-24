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

import model.Album;
import model.Photo;

public class RenamePhotoController {
	@FXML
	private TextField newPhotoName;
	
	@FXML
	private Button cancel, rename;

	private Stage primaryStage;
	
	private Album currAlbum;
	
	private Photo activePhoto;
	
	/**
	 * 
	 * @param primary stage
	 * @param current user
	 * @param photo currently selected
	 * @throws throw exception when stage can not be loaded
	 * 
	 */
	public void start(Stage primaryStage, Album currAlbum, Photo activePhoto) throws IOException {
		this.primaryStage = primaryStage;
		this.currAlbum = currAlbum;
		this.activePhoto = activePhoto;
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
	void renamePhotoAction(ActionEvent event) {
		
		//make sure input wasn't empty
		if(this.newPhotoName.getText().isEmpty()) {
			Alert alert1 = new Alert(AlertType.WARNING);
			alert1.setTitle("Warning: Empty photo name");
			alert1.setHeaderText(null);
			alert1.setContentText("Can't change a photo name to nothing.");
			alert1.showAndWait();
			return;
		}

		//make sure name doesn't already exist
		for(int i = 0; i < this.currAlbum.getPhotoList().size(); i++) {
			String currName = this.currAlbum.getPhotoList().get(i).getPhotoCaption().trim();
			if(this.newPhotoName.getText().trim().equals(currName) ) {
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("Warning: Name already in use");
				alert2.setHeaderText(null);
				alert2.setContentText("A photo with that name already exists.");
				alert2.showAndWait();
				return;
			}
		}
		
		this.activePhoto.setPhotoCaption(this.newPhotoName.getText().trim());
		
		Stage stage = (Stage) this.primaryStage;
		stage.close();
	}
	

}

