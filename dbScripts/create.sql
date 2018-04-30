CREATE database GreasyPopcorn;
USE GreasyPopcorn;


CREATE TABLE Movie(
	name VARCHAR(50) NOT NULL,
	movieID VARCHAR(50) NOT NULL PRIMARY KEY,
	releaseDate DATE NOT NULL,
	releaseCountry VARCHAR(50) NOT NULL,
	genre VARCHAR(50) NOT NULL
	);
	
CREATE TABLE User(
	name VARCHAR(50) NOT NULL,
	username VARCHAR(50) NOT NULL PRIMARY KEY
	);
	
CREATE TABLE Review(
	reviewID INTEGER(50) NOT NULL PRIMARY KEY,
	label VARCHAR(1000) NOT NULL,
	content VARCHAR(2000) NOT NULL,
	rating INTEGER(50) NOT NULL,
	username VARCHAR(50) NOT NULL,
	movieID VARCHAR(50) NOT NULL,
	CONSTRAINT review_username_fk FOREIGN KEY (username) REFERENCES User(username),
	CONSTRAINT review_movieID_fk FOREIGN KEY (movieID) REFERENCES Movie(movieID)	
	);
	
CREATE TABLE Person(
	birthdate DATE NOT NULL,
	name VARCHAR(50) NOT NULL,
	personID INTEGER(50) NOT NULL PRIMARY KEY
	);
	
CREATE TABLE Role(
	name VARCHAR(50) NOT NULL,
	movieID VARCHAR(50) NOT NULL,
	personID INTEGER(50) NOT NULL,
	CONSTRAINT role_movieID_fk FOREIGN KEY (movieID) REFERENCES Movie(movieID),
	CONSTRAINT role_personID_fk FOREIGN KEY (personID) REFERENCES Person(personID)
	);
	


	
