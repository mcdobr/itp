package xyz.bcdi.greasypopcorn.core;

import com.fasterxml.jackson.annotation.*;

public class User {
	private final String username;
	private final String name;
	private final String password;
	private final Boolean isModerator;
	
	public User(@JsonProperty("username") String username,
			@JsonProperty("name") String name,
			@JsonProperty("password") String password,
			@JsonProperty("isModerator") Boolean isModerator) {
		super();
		this.username = username;
		this.name = name;
		this.password = password;
		this.isModerator = isModerator;
	}
	
	public String getUsername() {
		return username;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public Boolean getIsModerator() {
		return isModerator;
	}


	public static class UserBuilder {
		private String username;
		private String name;
		private String password;
		private Boolean isModerator;
		
		public UserBuilder() {}
		
		private UserBuilder(User user) {
			this.username = user.username;
			this.name = user.name;
			this.password = user.password;
			this.isModerator = user.isModerator;
		}
		
		public User build() {
			return new User(username, name, password, isModerator);
		}
		
		public UserBuilder withUsername(String username) {
			this.username = username;
			return this;
		}
		
		public UserBuilder withName(String name) {
			this.name = name;
			return this;		
		}
		
		public UserBuilder withPassword(String password) {
			this.password = password;
			return this;
		}
		
		public UserBuilder withIsModerator(Boolean isModerator) {
			this.isModerator = isModerator;
			return this;
		}
		
		public static UserBuilder copyOf(User user) {
			return new UserBuilder(user);
		}
	}
}
