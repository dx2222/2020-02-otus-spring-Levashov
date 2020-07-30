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
@Table(name="GENRE")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    public static List<Genre>  getGenreList(String[] genres) {
        List<Genre> genreList = new ArrayList<>();
        for (String curGenre : genres) {
            genreList.add(new Genre(null,curGenre));
        }
        return  genreList;
    }
}
