CREATE database GreasyPopcorn;
USE GreasyPopcorn;


CREATE TABLE movies(
	name VARCHAR(50) NOT NULL,
	movieID VARCHAR(50) NOT NULL PRIMARY KEY,
	releaseDate DATE NOT NULL,
	releaseCountry VARCHAR(50) NOT NULL,
	genre VARCHAR(50) NOT NULL
	);
	
CREATE TABLE users(
	name VARCHAR(50) NOT NULL,
	username VARCHAR(50) PRIMARY KEY,
	password VARCHAR(50) NOT NULL
	);
	
CREATE TABLE reviews(
	label VARCHAR(1000) NOT NULL,
	content VARCHAR(2000) NOT NULL,
	rating INTEGER(50) NOT NULL,
	username VARCHAR(50) NOT NULL,
	movieID VARCHAR(50) NOT NULL,
	CONSTRAINT review_username_fk FOREIGN KEY (username) REFERENCES users(username),
	CONSTRAINT review_movieID_fk FOREIGN KEY (movieID) REFERENCES movies(movieID),
	CONSTRAINT review_pk PRIMARY KEY(username, movieID)
	);
	
CREATE TABLE persons(
	birthdate DATE NOT NULL,
	name VARCHAR(50) NOT NULL,
	personID INTEGER(50) NOT NULL PRIMARY KEY
	);
	
CREATE TABLE roles(
	name VARCHAR(50) NOT NULL,
	movieID VARCHAR(50) NOT NULL,
	personID INTEGER(50) NOT NULL,
	CONSTRAINT role_movieID_fk FOREIGN KEY (movieID) REFERENCES movies(movieID),
	CONSTRAINT role_personID_fk FOREIGN KEY (personID) REFERENCES persons(personID),
	CONSTRAINT role_pk PRIMARY KEY(movieID, personID)
	);