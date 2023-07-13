package com.example.blogproject.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private Long blogId;
    private String categoryName;

    public CategoryDto(Category category) {
        this.id=category.getId();
        this.blogId=category.getBlog().getId();
        this.categoryName=category.getCategoryName();
    }
}
