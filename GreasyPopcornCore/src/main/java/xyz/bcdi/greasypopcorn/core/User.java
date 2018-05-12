package xyz.bcdi.greasypopcorn.core;

import com.fasterxml.jackson.annotation.*;

public class User {
	private String username;
	private String password;
	private String name;
	
	@JsonCreator
	public User(@JsonProperty("username") String username,
			@JsonProperty("password") String password,
			@JsonProperty("name") String name) {
		this.username = username;
		this.password = password;
		this.name = name;
	}
	
	public User(String username, String name) {
		this(username, null, name);
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
