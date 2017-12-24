package model;

/**
*
*@author Gibran Garcia
*@author Hien Pham
*
*/

public class Admin extends User {

	/**
	 * 
	 * create admin account for the application
     * 
     */
	public Admin() {
		this.isAdmin = true;
		this.username = "Admin";
	}
	
	/**
	 * 
     * @param name of new user
     * 
     */
	public Admin(String name) {
		this.isAdmin = true;
		this.username = name;
	}
}
