package xyz.bcdi.greasypopcorn.core;

public class User {
	private final String username;
	private final String password;
	//private final String name;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
}
