package model;

/**
*
*@author Gibran Garcia
*@author Hien Pham
*
*/

import java.util.ArrayList;

public class Player extends User{
	private ArrayList<Album> albums;
	
	/**
	 * 
     * @param name of user
     * 
     */
	public Player(String name) {
		this.isAdmin = false;
		this.username = name;
		this.albums = new ArrayList<Album>();
	}
	
	/**
	 * 
     * @param name of new album
     * 
     */
	public void addAlbum(Album newAlbum) {
		this.albums.add(newAlbum);
	}
	
	/**
	 * 
     * @param name of delete album
     * 
     */
	public void removeAlbum(Album deleteAlbum) {
		for(Album a: this.albums) {
			if(a.equals(deleteAlbum)) {
				this.albums.remove(a);
				return;
			}
		}
	}
	
	/**
	 * 
     * @return ArrayList of album
     * 
     */
	public ArrayList<Album> getAlbumList() {
		return this.albums;
	}
}
