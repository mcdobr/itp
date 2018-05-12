package xyz.bcdi.greasypopcorn.core;

import com.fasterxml.jackson.annotation.*;

public class User {
	private final String username;
	private final String name;
	private final String password;
	private final Boolean isPromoter;
	
	public User(@JsonProperty("username") String username,
			@JsonProperty("name") String name,
			@JsonProperty("password") String password,
			@JsonProperty("isPromoter") Boolean isPromoter) {
		super();
		this.username = username;
		this.name = name;
		this.password = password;
		this.isPromoter = isPromoter;
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

	public Boolean getIsPromoter() {
		return isPromoter;
	}


	public static class UserBuilder {
		private String username;
		private String name;
		private String password;
		private Boolean isPromoter;
		
		public UserBuilder() {}
		
		private UserBuilder(User user) {
			this.username = user.username;
			this.name = user.name;
			this.password = user.password;
			this.isPromoter = user.isPromoter;
		}
		
		public User build() {
			return new User(username, name, password, isPromoter);
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
		
		public UserBuilder withIsPromoter(Boolean isPromoter) {
			this.isPromoter = isPromoter;
			return this;
		}
		
		public static UserBuilder copyOf(User user) {
			return new UserBuilder(user);
		}
	}
}
