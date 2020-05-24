package ru.otus.spring.homework.dto;

import lombok.Builder;
import lombok.Data;
import ru.otus.spring.homework.domain.Book;
import ru.otus.spring.homework.domain.Comment;

@Data
@Builder
public class CommentDto {
    private String id;
    private String bookID;
    private String comment;

    public static CommentDto toDto(Comment comment) {
        return  CommentDto.builder()
                .id(comment.getId())
                .bookID(comment.getBook().getId())
                .comment(comment.getTextComment())
                .build();
    }

    public static Comment toDomain(CommentDto commentDto) {
        return Comment.builder()
                .id(commentDto.getId())
                .textComment(commentDto.getComment().trim())
                .book(Book.builder().id(commentDto.getBookID()).build())
                .build();
    }
}
