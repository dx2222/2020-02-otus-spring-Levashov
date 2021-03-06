package ru.otus.spring.homework.dto;

import lombok.Builder;
import lombok.Data;
import ru.otus.spring.homework.domain.Author;
import ru.otus.spring.homework.domain.Book;
import ru.otus.spring.homework.domain.Genre;
import java.util.List;

@Data
@Builder
public class BookDto {

    private Long   id;
    private String name;
    private List<Author> author;
    private List<Genre> genre;
    public String authors;
    public String genres;

    public static BookDto toDto(Book book) {
        return  BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .author(book.getAuthor())
                .genre(book.getGenre())
                .authors(book.authorsToString())
                .genres(book.genresToString())
                .build();
    }

}
