package com.example.blogproject.post.dto;

import com.example.blogproject.post.entity.Post;
import com.example.blogproject.tag.entity.Tag;
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
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private List<String> tagNames = new ArrayList<>();
    private LocalDateTime createdAt;
    private Long blogId;
    private Long categoryId;

    public PostResponseDto(Post post) {
        this.id=post.getId();
        this.title=post.getTitle();
        this.content=post.getContent();
        this.createdAt=post.getCreatedAt();
        this.blogId=post.getBlog().getId();
        if(post.getCategory()!=null){
            this.categoryId=post.getCategory().getId();

        }else{
            this.categoryId=null;
        }

        for(Tag tag: post.getTags()){
            this.tagNames.add(tag.getTagName());
        }

    }
}
