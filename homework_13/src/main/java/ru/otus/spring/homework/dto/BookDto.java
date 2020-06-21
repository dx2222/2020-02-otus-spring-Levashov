package ru.otus.spring.homework.dto;

import lombok.Builder;
import lombok.Data;
import ru.otus.spring.homework.domain.Book;

@Data
@Builder
public class BookDto {

    private Long   id;
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

}
