package controller;

/**
*
*@author Gibran Garcia
*@author Hien Pham
*
*/

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import model.Player;
import model.User;
import model.Album;

public class SelectAlbumController {
	@FXML
	private ListView<Album> albumListView;
	private ObservableList<Album> obsList;
	
	private Stage primaryStage;
	
	private Player currPlayer;
	private Album currAlbum;
	
	private ArrayList<Album> albumsList;
	
	/**
	* @param primary stage
	* @param current player
	* @param active photo
	*
	*/
	public void start(Stage primaryStage, Player currPlayer, Album currAlbum) {
		this.primaryStage = primaryStage;
		this.currPlayer = currPlayer;
		this.currAlbum = currAlbum;
		
		//want to list all albums except current one
		this.albumsList = this.currPlayer.getAlbumList();
		this.obsList = FXCollections.observableArrayList();
		for(int i = 0; i < this.albumsList.size(); i++){
			if(!this.albumsList.get(i).equals(this.currAlbum)) {
				this.obsList.add(this.albumsList.get(i));
			}
		}
		this.albumListView.setItems(this.obsList.sorted());

		//default select first album in the list
		this.albumListView.getSelectionModel().selectFirst();
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
	* select album action
	*
	*/
	@FXML
	void selectAlbumAction(ActionEvent event) {
		Stage stage = (Stage) this.primaryStage;
		stage.close();
	}
}
