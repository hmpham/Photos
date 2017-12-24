package model;

/**
 *
 *@author Gibran Garcia
 *@author Hien Pham
 *
 */

import java.io.*;

public abstract class User implements Serializable {
	String username;
	boolean isAdmin;

	/**
	 * 
	 * @return user name
	 * 
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * 
	 * @return user name
	 * 
	 */
	public String toString() {
		return this.getUsername();
	}

	/**
	 * 
	 * @return true if user is admin
	 * 
	 */
	public boolean isAdmin() {
		return this.isAdmin;
	}
}
