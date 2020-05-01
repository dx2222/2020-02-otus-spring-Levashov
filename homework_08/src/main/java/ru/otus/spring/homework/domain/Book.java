package ru.otus.spring.homework.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Book")
public class Book {

    @Id
    private String  id;

    private String name;

    @Field(name = "author")
    private  List<Author> author;

    @Field(name = "genre")
    private  List<Genre> genre;

    public String AuthorsToString() {
        String st = "";
        if ((author != null)&&(author.size()>0)) {
            for (Author author1 : author) {
                st = st.concat(author1.getName() + ",");
            }
            return st.substring(0, st.length() - 1);
        } else {
            return st;
        }
    }

    public String GenresToString() {
        String st = "";
        if ((genre != null)&&(genre.size()>0)) {
            for (Genre genre1 : genre) {
                st = st.concat(genre1.getName() + ",");
            }
            return st.substring(0, st.length() - 1);
        }else {
            return st;
        }
    }

}
