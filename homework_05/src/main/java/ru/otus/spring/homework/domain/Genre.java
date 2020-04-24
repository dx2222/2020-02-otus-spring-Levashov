package ru.otus.spring.homework.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Genre {

    private Long genreID;
    private final String name;

    public Genre(Long genreID, String name) {
        this.genreID = genreID;
        this.name    = name;
    }

    public static List<Genre> getGenreList(String[] genres) {
        List<Genre> genreList = new ArrayList<>();
        for (String curGenre : genres) {
            genreList.add(new Genre(0L,curGenre));
        }
        return  genreList;
    }
}
