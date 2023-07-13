package com.example.blogproject.comment.dto;

import com.example.blogproject.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String nickname;
    private String content;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment) {
        this.id= comment.getId();
        this.content= comment.getContent();
        this.createdAt=comment.getCreatedAt();
        this.nickname=comment.getAccount().getNickname();
    }
}
