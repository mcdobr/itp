package xyz.bcdi.greasypopcorn.core;

import java.util.Optional;

public class Role {
	private Integer roleID, movieID;
	private Optional<Integer> personID;
	private String name;

	private Role(Integer roleID, Integer movieID, Optional<Integer> personID, String name) {
		this.roleID = roleID;
		this.movieID = movieID;
		this.personID = personID;
		this.name = name;
	}
	
	public Role(Integer roleID, Integer movieID, Integer personID, String name) {
		this(roleID, movieID, Optional.ofNullable(personID), name);
	}
	
	public Role(Integer roleID, Integer movieID, String name) {
		this(roleID, movieID, Optional.empty(), name);
	}

	public Integer getRoleID() {
		return roleID;
	}

	public void setRoleID(Integer roleID) {
		this.roleID = roleID;
	}

	public Integer getMovieID() {
		return movieID;
	}

	public void setMovieID(Integer movieID) {
		this.movieID = movieID;
	}

	public Optional<Integer> getPersonID() {
		return personID;
	}

	public void setPersonID(Integer personID) {
		this.personID = Optional.ofNullable(personID);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
