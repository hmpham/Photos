package controller;

/**
*
*@author Gibran Garcia
*@author Hien Pham
*
*/

import javafx.fxml.FXML;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import model.Photo;

public class DisplayPhotoController {
	
	@FXML
	private StackPane photoWrapper;
	
	/**
	* @param primary stage
	* @param active photo
	*
	*/
	public void start(Stage primaryStage, Photo activePhoto) {
		Image imgFromFile = new Image(activePhoto.getPath(), 500, 300, true, true);
		ImageView imgView = new ImageView(imgFromFile);
		
		photoWrapper.getChildren().clear();
		photoWrapper.getChildren().add(imgView);
	}
}
