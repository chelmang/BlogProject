package com.example.blogproject.tag.entity;

import com.example.blogproject.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Tag")
public class Tag {

    @Id
    @Column(name = "tag_name")
    private String tagName;

    @ManyToMany(mappedBy = "tags")
    private List<Post> posts = new ArrayList<>();


    public Tag(String tagName) {
        this.tagName=tagName;
    }
}
