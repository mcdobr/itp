CREATE database GreasyPopcorn;
USE GreasyPopcorn;


CREATE TABLE MOVIES(
	title VARCHAR(50) NOT NULL,
	imdbId VARCHAR(50) NOT NULL,
	releaseDate DATE NOT NULL,
	releaseCountry VARCHAR(50) NOT NULL,
	releaseYear DOUBLE NOT NULL,
	releaseMonth DOUBLE NOT NULL,
	releaseDay DOUBLE NOT NULL
	);