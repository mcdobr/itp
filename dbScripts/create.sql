CREATE database GreasyPopcorn;
USE GreasyPopcorn;

CREATE TABLE movies(
	movieID INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	releaseDate DATE,
	genre VARCHAR(50),
	CONSTRAINT movie_pk PRIMARY KEY(movieID)
);
	
CREATE TABLE users(
	username VARCHAR(50),
	name VARCHAR(50) NOT NULL,
	password VARCHAR(50) NOT NULL,
	isPromoter BOOLEAN NOT NULL DEFAULT FALSE,
	CONSTRAINT user_pk PRIMARY KEY(username)
);
	
CREATE TABLE reviews(
	username VARCHAR(50) NOT NULL,
	movieID INT NOT NULL,
	label VARCHAR(1000),
	content VARCHAR(2000),
	rating INT NOT NULL,
	CONSTRAINT review_username_fk FOREIGN KEY (username) REFERENCES users(username),
	CONSTRAINT review_movieID_fk FOREIGN KEY (movieID) REFERENCES movies(movieID),
	CONSTRAINT review_pk PRIMARY KEY(username, movieID)
);
	
CREATE TABLE persons(
	personID INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	birthdate DATE,
	CONSTRAINT person_pk PRIMARY KEY(personID)
);
	
CREATE TABLE roles(
	movieID INT NOT NULL,
	personID INT,
	name VARCHAR(50) NOT NULL,
	CONSTRAINT role_movieID_fk FOREIGN KEY (movieID) REFERENCES movies(movieID),
	CONSTRAINT role_personID_fk FOREIGN KEY (personID) REFERENCES persons(personID),
	CONSTRAINT role_pk PRIMARY KEY(movieID, personID)
);
