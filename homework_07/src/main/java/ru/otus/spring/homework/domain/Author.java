package ru.otus.spring.homework.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="AUTHOR")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

   public static List<Author>  getAuthorList(String[] authors) {
        List<Author> authorList = new ArrayList<>();
        for (String curAuthor : authors) {
            authorList.add(new Author(null,curAuthor));
        }
        return  authorList;
    }
}
