package model;

/**
*
*@author Gibran Garcia
*@author Hien Pham
*
*/

import java.io.*;
import java.util.ArrayList;

public class UserList implements Serializable{
	private ArrayList<User> users;
	
	/**
	 * 
	 * create a new user list
	 * 
	 */
	public UserList() {
		users = new ArrayList<User>();
	}
	
	/**
	 * 
	 * @param new user to add to user list
	 * 
	 */
	public void addUser(User u) {
		this.users.add(u);
	}
	
	/**
	 * 
	 * @return ArrayList of users
	 * 
	 */
	public ArrayList<User> getUsers() {
		return users;
	}

	public static final String storeDir = "dat";
	public static final String storeFile = "users.dat";
	
	/**
	 * 
	 * @param user list
	 * @throws when can not save user list
	 * 
	 */
	public static void writeUserList(UserList list) throws IOException {
		FileOutputStream fileOut = new FileOutputStream(storeDir + File.separator + storeFile);
		ObjectOutputStream oos = new ObjectOutputStream(fileOut);
		oos.writeObject(list);
		oos.close();
		fileOut.close();
	}
	
	/**
	 * 
	 * @return a list of user
	 * @throws when class not found
	 * 
	 */
	public static UserList readUserList() throws IOException, ClassNotFoundException {
		FileInputStream fileIn = new FileInputStream(storeDir + File.separator + storeFile);
		ObjectInputStream ois = new ObjectInputStream(fileIn);
		UserList allUsers = (UserList)ois.readObject();
		ois.close();
		fileIn.close();
		return allUsers;
	}

	
}
