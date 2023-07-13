package com.example.blogproject.post.entity;

import com.example.blogproject.account.entity.Account;
import com.example.blogproject.blog.Blog;
import com.example.blogproject.category.Category;
import com.example.blogproject.comment.entity.Comment;
import com.example.blogproject.post.dto.PostRequestDto;
import com.example.blogproject.security.user.UserDetailsImpl;
import com.example.blogproject.tag.entity.Tag;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "post_tag",
            joinColumns = {@JoinColumn(name = "post_id", referencedColumnName = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_name", referencedColumnName = "tag_name")})
    private List<Tag> tags;

    //가진 댓글들
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
    public Post(PostRequestDto requestDto, Account account, Blog blog, Category category) {
        this.content= requestDto.getContent();
        this.title=requestDto.getTitle();
        this.blog=blog;
        this.category=category;
    }

    public void update(PostRequestDto requestDto) {
        this.title= requestDto.getTitle();
        this.content= requestDto.getContent();
    }

    // 추가적인 필드, 관계 설정 등

}
