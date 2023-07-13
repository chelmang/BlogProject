package com.example.blogproject.blog;

import com.example.blogproject.account.entity.Account;
import com.example.blogproject.category.Category;
import com.example.blogproject.post.entity.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@AllArgsConstructor
public class Blog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="blog_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;

    //가진 카테고리들.
    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> categories = new ArrayList<>();

    //가진 게시물들.
    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @NotBlank
    @Column(name="blog_name")
    private String blogName;

    private String description;

    @Column(name="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    public Blog(Long id){
        this.id = id;
    }

    public Blog(Account account, BlogDto blogDto) {
        this.account=account;
        this.blogName= blogDto.getBlogName();
        this.description=blogDto.getDescription();
    }

    public void update(BlogDto blogDto) {
        this.blogName=blogDto.getBlogName();
        this.description=blogDto.getDescription();
    }
}
