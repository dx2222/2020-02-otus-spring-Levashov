package ru.otus.spring.homework.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Author {

    private Long authorID;
    private final String name;

    public Author(Long authorID, String name) {
        this.authorID = authorID;
        this.name     = name;
    }

    public static List<Author>  getAuthorList(String[] authors) {
        List<Author> authorList = new ArrayList<>();
        for (String curAuthor : authors) {
            authorList.add(new Author(0L,curAuthor));
        }
        return  authorList;
    }
}
