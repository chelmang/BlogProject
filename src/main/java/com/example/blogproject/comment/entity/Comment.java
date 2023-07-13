package com.example.blogproject.comment.entity;


import com.example.blogproject.account.entity.Account;
import com.example.blogproject.comment.dto.CommentRequestDto;
import com.example.blogproject.post.entity.Post;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;


    public Comment(CommentRequestDto requestDto, Account account, Post post) {
        this.account= account;
        this.post=post;
        this.content= requestDto.getContent();
    }

    // 추가적인 필드, 관계 설정 등

}
