package com.example.blogproject.tag.service;


import com.example.blogproject.post.dto.PostResponseDto;
import com.example.blogproject.post.entity.Post;
import com.example.blogproject.tag.dto.TagDto;
import com.example.blogproject.tag.entity.Tag;
import com.example.blogproject.tag.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<TagDto> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public List<PostResponseDto> getTagPosts(String tagName) {
        Tag tag = tagRepository.findById(tagName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid tag name: " + tagName));
        List<Post> posts = tag.getPosts();
        List<PostResponseDto> postDtos = new ArrayList<>(posts.size());
        for(Post post: posts){
            postDtos.add(new PostResponseDto(post));
        }

        return postDtos;
    }

    private TagDto convertToResponseDto(Tag tag) {
        TagDto responseDto = new TagDto();
        responseDto.setTagName(tag.getTagName());
        // 추가적인 필드 설정
        return responseDto;
    }
}
