DROP TABLE IF EXISTS AUTHOR;
CREATE TABLE AUTHOR(ID LONG AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(255));

DROP TABLE IF EXISTS GENRE;
CREATE TABLE GENRE(ID LONG AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(255));

DROP TABLE IF EXISTS BOOK;
CREATE TABLE BOOK(ID LONG AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(255));

DROP TABLE IF EXISTS COMMENT;
CREATE TABLE COMMENT(ID LONG AUTO_INCREMENT PRIMARY KEY, BOOK_ID LONG, COMMENT VARCHAR(255),
                      FOREIGN KEY(BOOK_ID) REFERENCES BOOK(ID) ON DELETE CASCADE
                      );

DROP TABLE IF EXISTS BOOK_AUTHOR;
CREATE TABLE BOOK_AUTHOR(BOOK_ID LONG, AUTHOR_ID LONG,
                          FOREIGN KEY(BOOK_ID) REFERENCES BOOK(ID) ON DELETE CASCADE,
                          FOREIGN KEY(AUTHOR_ID) REFERENCES AUTHOR(ID) ON DELETE CASCADE
                       );

DROP TABLE IF EXISTS BOOK_GENRE;
CREATE TABLE BOOK_GENRE(BOOK_ID LONG, GENRE_ID LONG,
                          FOREIGN KEY(BOOK_ID) REFERENCES BOOK(ID) ON DELETE CASCADE,
                          FOREIGN KEY(GENRE_ID) REFERENCES GENRE(ID) ON DELETE CASCADE
                       );