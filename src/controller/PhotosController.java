package controller;

/**
 *
 *@author Gibran Garcia
 *@author Hien Pham
 *
 */

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import model.Player;
import model.Album;
import model.Photo;

public class PhotosController {
	@FXML
	private Text name;
	@FXML
	private Button logout;

	//left hand side display
	@FXML
	private ScrollPane photosWrapper;	
	@FXML
	private FlowPane photosContainer;	
	@FXML
	private Button goBack, slideshow, addPhoto;

	@FXML
	private Pane photoInfoPane;
	@FXML
	private StackPane photoPaneThumbnail;
	@FXML
	private Text photoPaneName;
	@FXML
	private Button photoPaneRename, photoPaneCopy, photoPaneMove, photoPaneDelete, photoPaneOpen;


	private Stage primaryStage, previousStage;

	private Player currPlayer;
	private String userName;
	private Album currAlbum;
	private Photo activePhoto;

	private ArrayList<Photo> photoList;
	private ArrayList<StackPane> photoFormattedContent;

	/**
	 * 
	 * @param primary stage
	 * @param previous stage
	 * @param current user
	 * @param current album
	 * @throws throw exception when stage can not be loaded
	 * 
	 */
	public void start(Stage primaryStage, Stage previousStage, Player currPlayer, Album currAlbum) throws IOException {
		this.primaryStage = primaryStage;
		this.previousStage = previousStage;

		this.currPlayer = currPlayer;
		this.userName = this.currPlayer.getUsername();
		name.setText("Username: " + userName);

		this.currAlbum = currAlbum;
		this.photoList = this.currAlbum.getPhotoList();
		this.photoFormattedContent = new ArrayList<StackPane>();

		initPhotoContainer(this.photoList, this.photoFormattedContent);
	}

	/**
	 * 
	 * @param list of photo
	 * @param list of photo content
	 * 
	 */
	private void initPhotoContainer(ArrayList<Photo> photoList, ArrayList<StackPane> photoFormattedContent) {
		if(photoList.size() == 0) {
			this.activePhoto = null;
			this.photosWrapper.setVisible(false);
			this.photoInfoPane.setVisible(false);
		}
		else {
			this.activePhoto = photoList.get(0);
			this.photosWrapper.setVisible(true);
			this.photoInfoPane.setVisible(true);

			updatePhotoPane(this.activePhoto);
			//format our photos information and add to content list	
			for(int i = 0; i < photoList.size(); i++) {
				StackPane pane = getFormattedPhoto(photoList.get(i));
				photoFormattedContent.add(pane);
			}

			this.photosContainer.getChildren().setAll(photoFormattedContent);
		}
		return;
	}

	/**
	 * 
	 * @param photo of album
	 * 
	 */
	private void updatePhotoPane(Photo newPhoto) {
		Image imgFromFile = new Image(newPhoto.getPath(), 70, 70, true, false);
		ImageView imgView = new ImageView(imgFromFile);
		
		this.photoPaneThumbnail.getChildren().clear();
		this.photoPaneThumbnail.getChildren().add(imgView);
		
		this.photoPaneName.setText(newPhoto.getPhotoCaption());
	}

	//creates StackPanes for each photo to populate our FlowPane display
	private StackPane getFormattedPhoto(Photo newPhoto) {
		Image imgFromFile = new Image(newPhoto.getPath(), 80, 80, true, false);
		ImageView imgView = new ImageView(imgFromFile);
		
		Text text = new Text(newPhoto.getPhotoCaption());
		text.setWrappingWidth(80);
		
		text.setTextAlignment(TextAlignment.CENTER);
		
		StackPane pane = new StackPane();
		pane.getChildren().add(imgView);
		pane.getChildren().add(text);
		
		StackPane.setAlignment(imgView, Pos.TOP_CENTER);
		StackPane.setAlignment(text, Pos.BOTTOM_CENTER);
		
		pane.setMaxSize(90, 120);
		pane.setMinSize(90, 120);
		pane.setStyle("-fx-background-color: #efefef; -fx-border-color: #e0e0e0;");

		pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				e.consume(); //consume event so it doesn't propagate
				activePhoto = newPhoto;
				photoInfoPane.setVisible(true);

				updatePhotoPane(newPhoto);
			}		
		});
		return pane;
	}

	@FXML
	void openPhotoAction(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/PhotoDisplay.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		Stage newStage = new Stage();
		newStage.initModality(Modality.WINDOW_MODAL);
		newStage.initOwner(this.primaryStage);
		
		DisplayPhotoController Controller = loader.getController();
		Controller.start(newStage, this.activePhoto);
		
		Scene scene = new Scene(root);
		newStage.setResizable(false);
		newStage.setScene(scene);
		newStage.showAndWait();
	}
	
	/**
	 * 
	 * add photo to the album
	 * 
	 */
	@FXML
	void addPhotoAction(ActionEvent event) throws MalformedURLException {
		List<File> photoList = retrievePhotosDialog();
		if(photoList != null) {
			for(int i = 0; i < photoList.size(); i++) {
				File f = photoList.get(i);

				//create photo object
				Photo temp = new Photo(f.getName(), f.toURI().toURL().toExternalForm(), f.lastModified());
				//add photo object to currPlayer's currAlbum arrayList.
				this.currAlbum.addPhoto(temp);

				//also need to add to our display content
				StackPane pane = getFormattedPhoto(temp);
				this.photoFormattedContent.add(pane);


				//if on last photo
				if(i == photoList.size() - 1) {
					this.activePhoto = temp;
					//set photo display to true and also on photo pane(Have at least one photo now)
					this.photosWrapper.setVisible(true);
					this.photoInfoPane.setVisible(true);
					//and update Pane to display the information of it
					updatePhotoPane(temp);
					//update view
					this.photosContainer.getChildren().clear();
					this.photosContainer.getChildren().setAll(this.photoFormattedContent);

				}
			}
		}
	}

	private List<File> retrievePhotosDialog() {
		FileChooser imgSelect = new FileChooser();
		imgSelect.setTitle("Select Images");
		imgSelect.setInitialDirectory(new File(System.getProperty("user.home")));
		imgSelect.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("All Images", "*.*"),
				new FileChooser.ExtensionFilter("JPG", "*.jpg"),
				new FileChooser.ExtensionFilter("PNG", "*.png")
				);
		List<File> imgList = imgSelect.showOpenMultipleDialog(this.primaryStage);
		return imgList;
	}

	/**
	 * 
	 * log out of photo
	 * 
	 */
	@FXML
	void deletePhotoAction(ActionEvent event) {
		//trigger warning before continuing, asking for user confirmation
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Photo");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete this photo?");
		Optional<ButtonType> result = alert.showAndWait();
		
		if(result.get() == ButtonType.OK) {
			//Delete from photoFormattedContent.
			this.photoFormattedContent.remove(getPhotoIndex(this.photoFormattedContent, this.activePhoto.getPhotoCaption()));
			
			//Delete from Album object
			this.currAlbum.removePhoto(this.activePhoto);
			
			//Refresh Flow Pane
			this.photosContainer.getChildren().clear();
			this.photosContainer.getChildren().setAll(this.photoFormattedContent);
			//updates current active album and update Album Pane
			if(this.photoList.size() == 0) {
				this.activePhoto = null;
				this.photoInfoPane.setVisible(false);
				this.photosWrapper.setVisible(false);
			}
			else {
				this.activePhoto = this.photoList.get(0);
				this.photosWrapper.setVisible(true);
				this.photoInfoPane.setVisible(true);
				updatePhotoPane(this.activePhoto);
			}
		}
	}

	@FXML
	void renamePhotoAction(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/PhotoRename.fxml"));
		AnchorPane root = (AnchorPane)loader.load();

		Stage newStage = new Stage();
		newStage.initModality(Modality.WINDOW_MODAL);
		newStage.initOwner(this.primaryStage);

		String oldCaption = this.activePhoto.getPhotoCaption();
		RenamePhotoController Controller = loader.getController();
		Controller.start(newStage, this.currAlbum, this.activePhoto);

		Scene scene = new Scene(root, 322, 191);
		newStage.setResizable(false);
		newStage.setScene(scene);
		newStage.showAndWait();

		//get index of outdated pane
		int photoIndex = getPhotoIndex(this.photoFormattedContent, oldCaption);
		//parse for its text field, and update it
		for(Node node: this.photoFormattedContent.get(photoIndex).getChildren()) {
			if(node instanceof Text && ((Text)node).getText().equals(oldCaption)) {
				((Text)node).setText(this.activePhoto.getPhotoCaption());
			}
		}
		//update flow pane
		this.photosContainer.getChildren().clear();
		this.photosContainer.getChildren().setAll(this.photoFormattedContent);

		updatePhotoPane(this.activePhoto);

	}
	
	@FXML
	void copyPhotoAction(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/AlbumSelect.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		Stage newStage = new Stage();
		newStage.initModality(Modality.WINDOW_MODAL);
		newStage.initOwner(this.primaryStage);
		
		SelectAlbumController Controller = loader.getController();
		Controller.start(newStage, this.currPlayer, this.currAlbum);

		Scene scene = new Scene(root);
		newStage.setResizable(false);
		newStage.setScene(scene);
		newStage.showAndWait();
	}
	
	@FXML
	void movePhotoAction(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/AlbumSelect.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		Stage newStage = new Stage();
		newStage.initModality(Modality.WINDOW_MODAL);
		newStage.initOwner(this.primaryStage);
		
		SelectAlbumController Controller = loader.getController();
		Controller.start(newStage, this.currPlayer, this.currAlbum);

		Scene scene = new Scene(root);
		newStage.setResizable(false);
		newStage.setScene(scene);
		newStage.showAndWait();
	}
	
	private int getPhotoIndex(ArrayList<StackPane> photosFlowList, String photoCaption) {
		//gets index of pane who has same text as album
		for(int i = 0; i < photosFlowList.size(); i++) {
			//get all children of given pane
			for(Node node: photosFlowList.get(i).getChildren()) {
				//if child text, and equals active albums name
				if(node instanceof Text && ((Text)node).getText().equals(photoCaption.trim())) {
					//return index of the Pane
					return i;
				}
			}
		}
		return -1;
	}

	
	@FXML
	void logoutAction(ActionEvent event) {
		Stage stage = (Stage) this.primaryStage;
		stage.close();

		this.previousStage.show();
	}

	/**
	 * 
	 * go back to previous stage
	 * @throws throw exception when action can not be done
	 * 
	 */
	@FXML
	void goBackAction(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Player.fxml"));
		AnchorPane root = (AnchorPane)loader.load();

		PlayerController Controller = loader.getController();
		Controller.start(this.primaryStage, this.previousStage, this.currPlayer);

		Scene scene = new Scene(root);
		//this.primaryStage.setTitle("Photos24 - Made with love");
		this.primaryStage.setResizable(false);
		this.primaryStage.setScene(scene);
		this.primaryStage.show();
	}

}
