CREATE database GreasyPopcorn;
USE GreasyPopcorn;

CREATE TABLE movies(
	movieID INT AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	description VARCHAR(2000),
	releaseDate DATE,
	genre VARCHAR(50),
	CONSTRAINT movie_pk PRIMARY KEY(movieID)
);
	
CREATE TABLE users(
	username VARCHAR(50),
	name VARCHAR(50) NOT NULL,
	password VARCHAR(50) NOT NULL,
	isModerator BOOLEAN NOT NULL DEFAULT FALSE,
	CONSTRAINT user_pk PRIMARY KEY(username)
);
	
CREATE TABLE persons(
	personID INT AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	CONSTRAINT person_pk PRIMARY KEY(personID)
);

CREATE TABLE reviews(
	reviewID INT AUTO_INCREMENT,
	username VARCHAR(50) NOT NULL,
	movieID INT NOT NULL,
	rating INT NOT NULL,
	label VARCHAR(1000),
	content VARCHAR(2000),
	reviewTime DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT review_username_fk FOREIGN KEY (username) REFERENCES users(username),
	CONSTRAINT review_movieID_fk FOREIGN KEY (movieID) REFERENCES movies(movieID),
	CONSTRAINT review_pair_uk UNIQUE KEY (username, movieID),
	CONSTRAINT review_pk PRIMARY KEY(reviewID)
);

CREATE TABLE roles(
	roleID INT AUTO_INCREMENT,
	movieID INT NOT NULL,
	personID INT,
	roleName VARCHAR(50) NOT NULL,
	CONSTRAINT role_movieID_fk FOREIGN KEY (movieID) REFERENCES movies(movieID),
	CONSTRAINT role_personID_fk FOREIGN KEY (personID) REFERENCES persons(personID),
	CONSTRAINT role_uk UNIQUE KEY(movieID, personID),
	CONSTRAINT role_pk PRIMARY KEY(roleID)
);

