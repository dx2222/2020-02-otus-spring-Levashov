package ru.otus.spring.homework.dto;

import lombok.Builder;
import lombok.Data;
import ru.otus.spring.homework.domain.Comment;

@Data
@Builder
public class CommentDto {
    private Long id;
    private Long bookID;
    private String comment;

    public static CommentDto toDto(Comment comment) {
        return  CommentDto.builder()
                .id(comment.getId())
                .bookID(comment.getBookID())
                .comment(comment.getComment())
                .build();
    }
}
