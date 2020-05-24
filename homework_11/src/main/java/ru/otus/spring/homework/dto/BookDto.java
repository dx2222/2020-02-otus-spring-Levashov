package ru.otus.spring.homework.dto;

import lombok.Builder;
import lombok.Data;
import ru.otus.spring.homework.domain.Author;
import ru.otus.spring.homework.domain.Book;
import ru.otus.spring.homework.domain.Genre;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class BookDto {

    private String id;
    private String name;
    public String authors;
    public String genres;

    public static BookDto toDto(Book book) {
        return  BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .authors(book.authorsToString())
                .genres(book.genresToString())
                .build();
    }

    public static Book toDomain(BookDto bookDto) {
        Book book = Book.builder()
                   .id(bookDto.getId())
                   .name(bookDto.getName().trim())
                   .build();

        if (!bookDto.authors.trim().equals("")) {
            List<Author> authorsList = Author.getAuthorList(bookDto.authors.split(","));
            book.setAuthor(authorsList);
        } else {
            book.setAuthor(new ArrayList<>());
        }

        if (!bookDto.genres.trim().equals("")) {
            List<Genre> genresList = Genre.getGenreList(bookDto.genres.split(","));
            book.setGenre(genresList);
        } else {
            book.setGenre(new ArrayList<>());
        }

        return  book;
    }

}
