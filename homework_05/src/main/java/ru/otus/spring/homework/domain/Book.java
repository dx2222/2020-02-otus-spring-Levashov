package ru.otus.spring.homework.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class Book {

    private Long   bookID;
    private String name;

    private  List<Author> authors;
    private  List<Genre> genres;


    public Book(Long bookID, String name, List<Author> authors, List<Genre> genres ) {
        this.bookID    = bookID;
        this.name      = name;
        this.authors   = authors;
        this.genres    = genres;
    }

    public String AuthorsToString() {
        String st = "";
        if ((authors != null)&&(authors.size()>0)) {
            for (Author author : authors) {
                st = st.concat(author.getName() + ",");
            }
            return st.substring(0, st.length() - 1);
        } else {
            return st;
        }
    }

    public String GenresToString() {
        String st = "";
        if ((genres != null)&&(genres.size()>0)) {
            for (Genre genre : genres) {
                st = st.concat(genre.getName() + ",");
            }
            return st.substring(0, st.length() - 1);
        }else {
            return st;
        }
    }

}
