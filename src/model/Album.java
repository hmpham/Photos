package model;

/**
*
*@author Gibran Garcia
*@author Hien Pham
*
*/

import java.io.*;
import java.util.ArrayList;

public class Album implements Serializable{
	
	private String name;
	private ArrayList<Photo> photos;
	private int photoCount;
	
	/**
	 * 
     * @param name of album
     * 
     */
	public Album(String albumName) {
		this.name = albumName;
		this.photos = new ArrayList<Photo>();
		this.photoCount = 0;
	}
	
	/**
	 * 
     * create new album
     * 
     */
	public Album() {
		this.name = null;
		this.photos = new ArrayList<Photo>();
		this.photoCount = 0;
	}
	
	/**
	 * 
     *@return name of album
     * 
     */
	public String getName() {
		return this.name;
	}
	
	public String toString() {
		return this.name;
	}
	
	/**
	 * 
     * @param set name for album
     * 
     */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
     * @param add photo to album
     * 
     */
	public void addPhoto(Photo newPhoto) {
		this.photos.add(newPhoto);
		this.photoCount = this.photoCount + 1;
	}
	
	/**
	 * 
     * @param photo to delete
     * 
     */
	public void removePhoto(Photo deletePhoto) {
		for(Photo p: this.photos) {
			if(p.equals(deletePhoto)) {
				this.photos.remove(p);
				this.photoCount = this.photoCount - 1;
				return;
			}
		}
	}

	/**
	 * 
     * @return the ArrayList of photo
     * 
     */
	public ArrayList<Photo> getPhotoList() {
		return this.photos;
	}
	
	/**
	 * 
     * @return number of photo in album
     * 
     */
	public int getPhotoCount() {
		return this.photoCount;
	}
	
}
