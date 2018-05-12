package xyz.bcdi.greasypopcorn.dbaccess;

import java.sql.*;
import java.util.*;

import xyz.bcdi.greasypopcorn.core.*;
import xyz.bcdi.greasypopcorn.core.Person.*;

public class PersonDAO extends AbstractDatabaseAccessObject {
	private static final PersonDAO instance = new PersonDAO();

	public static PersonDAO getInstance() {
		return instance;
	}

	private PersonDAO() {
		super();
	}

	public List<Person> getPersons() {
		List<Person> persons = new ArrayList<>();

		try {
			statement = conn.prepareStatement(sql.getProperty("getPersons"));
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				Person person = copyOf(rs).build();
				persons.add(person);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return persons;
	}
	
	public Person getPersonByID(int personID) {
		Person person = null;
		try {
			ResultSet rs = getResourceById("getPersonByID", Integer.valueOf(personID));
			if (rs.next()) {
				person = copyOf(rs).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return person;
	}
	
	public SqlOperationEffect replaceOrCreatePerson(Person p) {
		SqlOperationEffect opEffect = SqlOperationEffect.FAILED;
		
		Person personToBeReplaced = getPersonByID(p.getPersonID());
		try {
			if (personToBeReplaced == null ) {
				statement = conn.prepareStatement(sql.getProperty("createPerson"));
				statement.setInt(1, p.getPersonID());
				statement.setString(2, p.getName());
				statement.executeUpdate();
				opEffect = SqlOperationEffect.CREATED;
			} else {
				statement = conn.prepareStatement(sql.getProperty("replacePerson"));
				statement.setString(1, p.getName());
				statement.setInt(2, p.getPersonID());
				statement.executeUpdate();
				opEffect = SqlOperationEffect.REPLACED;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return opEffect;
	}

	public Person createPerson(Person p) {
		Person result = null;
		try {
			statement = conn.prepareStatement(sql.getProperty("postPerson"), Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, p.getName());
			statement.executeUpdate();
			
			ResultSet rs = statement.getGeneratedKeys();
			int lastID = -1;
			if (rs.next())
				lastID = rs.getInt(1);
			
			result = getPersonByID(lastID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean deletePerson(int personID) {
		return deleteEntity("deletePerson", personID);
	}
	
	private static PersonBuilder copyOf(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		PersonBuilder pb = new PersonBuilder();

		final int columns = rsmd.getColumnCount();
		for (int col = 1; col <= columns; ++col) {
			switch (rsmd.getColumnName(col).toLowerCase()) {
			case "personid":
				pb.withPersonID(rs.getInt("personID"));
				break;
			case "name":
				pb.withName(rs.getString("name"));
				break;
			}
		}
		return pb;
	}
}
