package roles;

/**
 * Abstract superclass for all user roles in the Student Management System
 * Contains shared properties and methods for the Admin, Professor, and Student roles
 * @author Katie
 */
public abstract class User {

	//instance variables
	
	/**
	 * ID of user
	 */
	private String id;
	
	/**
	 * Name of user
	 */
	private String name;
	
	/**
	 * Username of user's login
	 */
	private String username;
	
	/**
	 * Password for user's login
	 */
	private String password;
	
	
	//constructor
	
	/**
	 * Creates a new User
	 * @param id of user
	 * @param name of user
	 * @param username for user's login
	 * @param password for user's login
	 */
	public User(String id, String name, String username, String password) {
		
		//throws exception if user ID is null or empty
		if (id == null || id.trim().isEmpty()) {
			throw new IllegalArgumentException("User ID cannot be null or empty.");
		}
		
		//throws exception if name is null or empty
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty.");
		}
		
		//throws exception if username is null or empty
		if (username == null || username.trim().isEmpty()) {
			throw new IllegalArgumentException("Username cannot be null or empty.");
		}
		
		//throws exception if password is null or empty
		if (password == null || password.trim().isEmpty()) {
			throw new IllegalArgumentException("Password cannot be null or empty.");
		}
		
		setId(id);
		setName(name);
		setUsername(username);
		setPassword(password);
				
	}
	
	
	//getters and setters
	
	/**
	 * Gets User ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets User ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets User's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets User's name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	//methods

	/**
	 * Abstract method to display user-specific menu
	 * Must be implemented by all subclasses
	 */
	public abstract void displayMenu();	
	
	/**
	 * Authenticates the User by validating the username and password
	 * @param username to validate
	 * @param password to validate
	 * @return true if username and password match, false if not
	 */
	public boolean authenticate(String username, String password) {
		
		//checks if username or password is null
		if (username == null || password == null) {
			return false;
		}
		
		//authenticates login if username and passwords match what's in the system
		return this.username.equals(username.trim()) && this.password.equals(password.trim());		
		
	}	
	
}
