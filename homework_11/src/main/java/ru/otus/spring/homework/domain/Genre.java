package ru.otus.spring.homework.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Genre")
public class Genre {

    @Id
    private String id;

    private String name;

    public static List<Genre>  getGenreList(String[] genres) {
        List<Genre> genreList = new ArrayList<>();
        for (String curGenre : genres) {
            genreList.add(new Genre(null,curGenre));
        }
        return  genreList;
    }
}
