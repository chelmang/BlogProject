package com.example.blogproject.category;

import com.example.blogproject.blog.Blog;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Long id;

    private String categoryName;

    @ManyToOne
    @JoinColumn(name="blog_id")
    private Blog blog;

    public Category(CategoryDto categoryDto, Blog blog) {
        this.blog=blog;
        this.categoryName =categoryDto.getCategoryName();
    }

    public void update(CategoryDto categoryDto) {
        this.categoryName=categoryDto.getCategoryName();
    }
}
