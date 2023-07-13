package com.example.blogproject.blog;

import com.example.blogproject.category.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogDto {
    private Long id;
    private String blogName;
    private String description;

    private String ownerNickname;
    private LocalDateTime createdAt;

    private List<String> categories=new ArrayList<>();
    public BlogDto(Blog blog) {
        this.id = blog.getId();
        this.blogName= blog.getBlogName();
        this.description = blog.getDescription();
        this.ownerNickname = blog.getAccount().getNickname();
        this.createdAt = blog.getCreatedAt();
        for(Category category:blog.getCategories()){
            this.categories.add(category.getCategoryName());
        }
    }
}
