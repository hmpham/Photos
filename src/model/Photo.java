package model;

/**
 *
 *@author Gibran Garcia
 *@author Hien Pham
 *
 */

import java.io.*;

public class Photo implements Serializable {
	private String caption;
	private String path;
	private long lastModified;
	
	/**
	 * 
	 * @param photo caption
	 * @param photo path
	 * 
	 */
	public Photo(String photoCaption, String path, long lastModified) {
		this.caption = photoCaption;
		this.path = path;
		this.lastModified = lastModified;
	}

	/**
	 * 
	 * @return photo caption
	 * 
	 */
	public String getPhotoCaption() {
		return this.caption;
	}

	/**
	 * 
	 * @param new caption
	 * 
	 */
	public void setPhotoCaption(String newCaption) {
		this.caption = newCaption;
	}

	/**
	 * 
	 * @return path of the photo
	 * 
	 */
	public String getPath() {
		return this.path;
	}
	
	public long getLastModified() {
		return this.lastModified;
	}
}
