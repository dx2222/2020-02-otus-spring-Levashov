package ru.otus.spring.homework.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.homework.domain.Author;
import ru.otus.spring.homework.domain.Book;
import ru.otus.spring.homework.domain.Comment;
import ru.otus.spring.homework.domain.Genre;

import java.util.ArrayList;
import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private Book book1;

    @ChangeSet(order = "000", id = "dropDB", author = "dl", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }


    @ChangeSet(order = "001", id = "initBooks", author = "dl", runAlways = true)
    public void initKnowledges(MongoTemplate template){
        Author author1 = template.save(Author.builder().name("AUTHOR1").build());
        Author author2 = template.save(Author.builder().name("AUTHOR2").build());
        Author author3 = template.save(Author.builder().name("AUTHOR3").build());

        Genre genre1 = template.save(Genre.builder().name("GENRE1").build());
        Genre genre2 = template.save(Genre.builder().name("GENRE2").build());
        Genre genre3 = template.save(Genre.builder().name("GENRE3").build());

        List<Author> authors = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();

        authors.add(author1);
        genres.add(genre1);

        book1 = template.save(Book.builder().name("BOOK1").author(authors).genre(genres).build());

        authors.clear();
        genres.clear();

        authors.add(author2);
        genres.add(genre2);
        genres.add(genre3);

        template.save(Book.builder().name("BOOK2").author(authors).genre(genres).build());

        authors.add(author2);
        authors.add(author3);
        genres.add(genre2);
        genres.add(genre3);

        template.save(Book.builder().name("BOOK3").author(authors).genre(genres).build());


    }


    @ChangeSet(order = "002", id = "initComment", author = "dl", runAlways = true)
    public void initStudents(MongoTemplate template){

        template.save(Comment.builder().book(book1).textComment("COMMENT1").build());
        template.save(Comment.builder().book(book1).textComment("COMMENT2").build());
        template.save(Comment.builder().book(book1).textComment("COMMENT3").build());

    }
}
