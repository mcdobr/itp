package xyz.bcdi.greasypopcorn.core;

import com.fasterxml.jackson.annotation.*;

public class Person {
	private final Integer personID;
	private final String name;

	@JsonCreator
	public Person(@JsonProperty("personID") int personID,
			@JsonProperty("name") String name) {
		this.personID = personID;
		this.name = name;
	}
	
	public static class PersonBuilder {
		private Integer personID;
		private String name;
		
		public PersonBuilder() {}
		
		private PersonBuilder(Person person) {
			this.personID = person.personID;
			this.name = person.name;
		}
		
		public Person build() {
			return new Person(personID, name);
		}
		
		public static PersonBuilder copyOf(Person person) {
			return new PersonBuilder(person);
		}
	}
}
