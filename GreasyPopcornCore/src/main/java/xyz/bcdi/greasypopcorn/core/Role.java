package xyz.bcdi.greasypopcorn.core;

import com.fasterxml.jackson.annotation.*;

public class Role {
	private final Integer roleID, movieID, personID;
	private final String roleName;

	@JsonCreator
	public Role(@JsonProperty("roleID") int roleID,
			@JsonProperty("movieID") Integer movieID,
			@JsonProperty("personID") Integer personID,
			@JsonProperty("roleName") String roleName) {
		this.roleID = roleID;
		this.movieID = movieID;
		this.personID = personID;
		this.roleName = roleName;
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

	public String getRoleName() {
		return roleName;
	}

	public static class RoleBuilder {
		private Integer roleID, movieID, personID;
		private String roleName;
		
		public RoleBuilder() {}
		
		private RoleBuilder(Role role) {
			this.roleID = role.roleID;
			this.movieID = role.movieID;
			this.personID = role.personID;
			this.roleName = role.roleName;
		}
		
		public Role build() {
			return new Role(roleID, movieID, personID, roleName);
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
		
		public RoleBuilder withRoleName(String roleName) {
			this.roleName = roleName;
			return this;
		}
		
		public static RoleBuilder copyOf(Role role) {
			return new RoleBuilder(role);
		}
	}
}
