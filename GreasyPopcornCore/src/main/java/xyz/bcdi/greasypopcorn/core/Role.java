package xyz.bcdi.greasypopcorn.core;

import com.fasterxml.jackson.annotation.*;

public class Role {
	private final Integer roleID, movieID, personID;
	private final String name;

	@JsonCreator
	public Role(@JsonProperty("roleID") Integer roleID,
			@JsonProperty("movieID") Integer movieID,
			@JsonProperty("personID") Integer personID,
			@JsonProperty("name") String name) {
		this.roleID = roleID;
		this.movieID = movieID;
		this.personID = personID;
		this.name = name;
	}
	
	public Integer getRoleID() {
		return roleID;
	}

	public Integer getMovieID() {
		return movieID;
	}

	public Integer getPersonID() {
		return personID;
	}

	public String getName() {
		return name;
	}

	public static class RoleBuilder {
		private Integer roleID, movieID, personID;
		private String name;
		
		public RoleBuilder() {}
		
		private RoleBuilder(Role role) {
			this.roleID = role.roleID;
			this.movieID = role.movieID;
			this.personID = role.personID;
			this.name = role.name;
		}
		
		public Role build() {
			return new Role(roleID, movieID, personID, name);
		}
		
		public RoleBuilder withRoleID(Integer roleID) {
			this.roleID = roleID;
			return this;
		}
		
		public RoleBuilder withMovieID(Integer movieID) {
			this.movieID = movieID;
			return this;
		}
		
		public RoleBuilder withPersonID(Integer personID) {
			this.personID = personID;
			return this;
		}
		
		public RoleBuilder withName(String name) {
			this.name = name;
			return this;
		}
		
		public static RoleBuilder copyOf(Role role) {
			return new RoleBuilder(role);
		}
	}
}
