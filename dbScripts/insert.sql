--INSERT MOVIES--
  
  INSERT INTO movies VALUES(
	default,
    'Wings',
	'Two young men, one rich, one middle class, who are in love with the same woman, become fighter pilots in World War I.',
     STR_TO_DATE('1927-05-19' , '%Y-%m-%d'),
     'Drama'
  );
  INSERT INTO movies VALUES(
    default,
    'The Broadway Melody',
	'A pair of sisters from the vaudeville circuit try to make it big time on Broadway, but matters of the heart complicate the attempt.',
     STR_TO_DATE('1929-02-01' , '%Y-%m-%d'),
     'Romance'
  );
  INSERT INTO movies VALUES(
    default,
    'All Quiet on the Western Front',
	'A young soldier faces profound disillusionment in the soul-destroying horror of World War I.',
     STR_TO_DATE('1930-04-21' , '%Y-%m-%d'),
     'WAR'
     
  );
  INSERT INTO movies VALUES(
    default,
    'Cimarron',
	'A newspaper editor settles in an Oklahoma boom town with his reluctant wife at the end of the nineteenth century.',
     STR_TO_DATE('1931-01-26' , '%Y-%m-%d'),
     'Western'
     
  );
  INSERT INTO movies VALUES(
    default,
    'Grand Hotel',
	'A group of very different individuals staying at a luxurious hotel in Berlin deal with each of their respective dramas.',
     STR_TO_DATE('1932-04-12' , '%Y-%m-%d'),
     'Romance'
  );
  INSERT INTO movies VALUES(
    default,
    'Cavalcade',
	'The triumps and tragedies of two English families, the upper-crust Marryots and the working-class Bridges, from 1899 to 1933 are portrayed.',
     STR_TO_DATE('1933-01-05' , '%Y-%m-%d'),
     'Romance'
  );
  INSERT INTO movies VALUES(
    default,
    'It Happened One Night',
	'A spoiled heiress running away from her family is helped by a man who is actually a reporter in need of a story.',
     STR_TO_DATE('1934-02-22' , '%Y-%m-%d'),
     'Comedy'
  );
  INSERT INTO movies VALUES(
    default,
    'Mutiny on the Bounty',
	'A tyrannical ship captain decides to exact revenge on his abused crew after they form a mutiny against him, but the sailor he targets had no hand in it.',
     STR_TO_DATE('1935-11-08' , '%Y-%m-%d'),
     'Adventure'
  );
  INSERT INTO movies VALUES(
    default,
    'The Great Ziegfeld',
	'The ups and downs of Florenz Ziegfeld Jr., famed producer of extravagant stage revues, are portrayed. ',
     STR_TO_DATE('1936-03-22' , '%Y-%m-%d'),
     'Drama'
  );
  INSERT INTO movies VALUES(
    default,
    'The Life of Emile Zola',
	'The biopic of the famous French muckraking writer and his involvement in fighting the injustice of the Dreyfuss Affair.',
     STR_TO_DATE('1937-08-11' , '%Y-%m-%d'),
     'Drama'     
  );
  INSERT INTO movies VALUES(
    default,
    'You Can`t Take It with You',
	'A man from a family of rich snobs becomes engaged to a woman from a good-natured but decidedly eccentric family.',
     STR_TO_DATE('1938-08-23' , '%Y-%m-%d'),
     'Romance'
  );
  INSERT INTO movies VALUES(
    default,
    'Gone with the Wind',
    'A manipulative woman and a roguish man conduct a turbulent romance during the American Civil War and Reconstruction periods.',
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
