package com.example.blogproject.tag.controller;


import com.example.blogproject.post.dto.PostResponseDto;
import com.example.blogproject.tag.dto.TagDto;
import com.example.blogproject.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/")
    public ResponseEntity<List<TagDto>> getAllTags() {
        List<TagDto> tags = tagService.getAllTags();
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/{tagName}/posts")
    public ResponseEntity<List<PostResponseDto>> getTagPosts(@PathVariable String tagName) {
        List<PostResponseDto> posts = tagService.getTagPosts(tagName);
        return ResponseEntity.ok(posts);
    }
}
