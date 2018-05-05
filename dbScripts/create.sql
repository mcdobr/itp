CREATE database GreasyPopcorn;
USE GreasyPopcorn;

CREATE TABLE movies(
	movieID VARCHAR(50) PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	releaseDate DATE NOT NULL,
	genre VARCHAR(50) NOT NULL
);
	
CREATE TABLE users(
	username VARCHAR(50) PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	password VARCHAR(50) NOT NULL
);
	
CREATE TABLE reviews(
	username VARCHAR(50) NOT NULL,
	movieID VARCHAR(50) NOT NULL,
	label VARCHAR(1000),
	content VARCHAR(2000),
	rating INTEGER(50) NOT NULL,
	CONSTRAINT review_username_fk FOREIGN KEY (username) REFERENCES users(username),
	CONSTRAINT review_movieID_fk FOREIGN KEY (movieID) REFERENCES movies(movieID),
	CONSTRAINT review_pk PRIMARY KEY(username, movieID)
);
	
CREATE TABLE persons(
	personID INTEGER(50) PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	birthdate DATE
);
	
CREATE TABLE roles(
	movieID VARCHAR(50),
	personID INTEGER(50),
	name VARCHAR(50) NOT NULL,
	CONSTRAINT role_movieID_fk FOREIGN KEY (movieID) REFERENCES movies(movieID),
	CONSTRAINT role_personID_fk FOREIGN KEY (personID) REFERENCES persons(personID),
	CONSTRAINT role_pk PRIMARY KEY(movieID, personID)
);