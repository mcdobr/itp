--INSERT MOVIES--
  
  INSERT INTO movies VALUES(
	default,
    'Wings',
     STR_TO_DATE('1927-05-19' , '%Y-%m-%d'),
     'Drama'
  );
  INSERT INTO movies VALUES(
    default,
    'The Broadway Melody',
     STR_TO_DATE('1929-02-01' , '%Y-%m-%d'),
     'Romance'
  );
  INSERT INTO movies VALUES(
    default,
    'All Quiet on the Western Front',
     STR_TO_DATE('1930-04-21' , '%Y-%m-%d'),
     'WAR'
     
  );
  INSERT INTO movies VALUES(
    default,
    'Cimarron',
     STR_TO_DATE('1931-01-26' , '%Y-%m-%d'),
     'Western'
     
  );
  INSERT INTO movies VALUES(
    default,
    'Grand Hotel',
     STR_TO_DATE('1932-04-12' , '%Y-%m-%d'),
     'Romance'
  );
  INSERT INTO movies VALUES(
    default,
    'Cavalcade',
     STR_TO_DATE('1933-01-05' , '%Y-%m-%d'),
     'Romance'
  );
  INSERT INTO movies VALUES(
    default,
    'It Happened One Night',
     STR_TO_DATE('1934-02-22' , '%Y-%m-%d'),
     'Comedy'
  );
  INSERT INTO movies VALUES(
    default,
    'Mutiny on the Bounty',
     STR_TO_DATE('1935-11-08' , '%Y-%m-%d'),
     'Adventure'
  );
  INSERT INTO movies VALUES(
    default,
    'The Great Ziegfeld',
     STR_TO_DATE('1936-03-22' , '%Y-%m-%d'),
     'Drama'
  );
  INSERT INTO movies VALUES(
    default,
    'The Life of Emile Zola',
     STR_TO_DATE('1937-08-11' , '%Y-%m-%d'),
     'Drama'     
  );
  INSERT INTO movies VALUES(
    default,
    'You Can`t Take It with You',
     STR_TO_DATE('1938-08-23' , '%Y-%m-%d'),
     'Romance'
  );
  INSERT INTO movies VALUES(
    default,
    'Gone with the Wind',
     STR_TO_DATE('1939-12-28' , '%Y-%m-%d'),
     'Romance'
  );



INSERT INTO users VALUES('mircea', 'Mircea Dobreanu', 'abc', true);
INSERT INTO users VALUES('paul', 'Paul Brinza', 'def', false);


INSERT INTO persons VALUES(default, 'brad pitt');
INSERT INTO persons VALUES(default, 'angelina jolie');


INSERT INTO reviews VALUES(default, 'mircea', 7, 9, 'un film naspa', 'am plecat de la cinema imediat ce am vazut intro-ul', default);
INSERT INTO reviews VALUES(default, 'paul', 3, 2, 'extraordinar regizor', 'pacat ca inca face filme', default);
INSERT INTO reviews VALUES(default, 'mircea', 4, 6, 'mai degraba crosetam', 'am plecat', default);
INSERT INTO reviews VALUES(default, 'paul', 9, 10, 'tarantino e doar ok', 'pulp fiction a fost ok', default);


INSERT INTO roles VALUES(default, 1, 1, 'Mr. Smith');
INSERT INTO roles VALUES(default, 1, 2, 'Mrs. Smith');
