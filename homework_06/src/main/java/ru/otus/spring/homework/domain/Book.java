package ru.otus.spring.homework.domain;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="BOOK")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long   id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 100)
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(targetEntity = Author.class,  cascade = CascadeType.ALL)
    @JoinTable(name = "BOOK_AUTHOR",
               joinColumns = @JoinColumn(name = "BOOK_ID", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "id"))
    private  List<Author> author;


    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 100)
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(targetEntity = Genre.class, cascade = CascadeType.ALL)
    @JoinTable(name = "BOOK_GENRE",
               joinColumns = @JoinColumn(name = "BOOK_ID", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "GENRE_ID", referencedColumnName = "id"))
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
