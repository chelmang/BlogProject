package com.example.blogproject.post.service;

import com.example.blogproject.blog.Blog;
import com.example.blogproject.blog.BlogRepository;
import com.example.blogproject.category.Category;
import com.example.blogproject.category.CategoryRepository;
import com.example.blogproject.comment.dto.CommentResponseDto;
import com.example.blogproject.comment.entity.Comment;
import com.example.blogproject.comment.repository.CommentRepository;
import com.example.blogproject.post.dto.PostRequestDto;
import com.example.blogproject.post.dto.PostResponseDto;
import com.example.blogproject.post.entity.Post;
import com.example.blogproject.post.repository.PostRepository;
import com.example.blogproject.security.user.UserDetailsImpl;
import com.example.blogproject.tag.entity.Tag;
import com.example.blogproject.tag.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
//    private final PostMapper postMapper; 매퍼 적용이 안돼서 빼고 해보았음..
    private final BlogRepository blogRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;

    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + id));

//        return postMapper.toResponseDto(post);
        return new PostResponseDto(post);
    }

    public Long createPost(PostRequestDto requestDto, UserDetailsImpl userDetails) {
        Blog blog = blogRepository.findById(requestDto.getBlogId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + requestDto.getBlogId()));

        if(blog.getAccount().getId()!=userDetails.getAccount().getId()){
            throw new RuntimeException("본인 블로그가 아님");
        }
        Post post = new Post();
        //블로그 설정
        post.setBlog(blog);

        //카테고리 설정. 카테고리 없으면 null로 저장
        if(requestDto.getCategoryId()==null){
            post.setCategory(null);
        }else{
            Category category = categoryRepository.findById(requestDto.getCategoryId()).orElseThrow(
                    ()->new RuntimeException("Invalid CategoryId: "+requestDto.getCategoryId())
            );
            if(category.getBlog().getId()!=blog.getId()){
                throw new RuntimeException("블로그 내의 카테고리가 아님");
            }
            post.setCategory(category);
        }

        //태그설정. 없으면 null
        List<String> tagNames = requestDto.getTagNames();
        List<Tag> tags = new ArrayList<>(tagNames.size());
        for(String tagName: tagNames){
            Tag tag = tagRepository.findById(tagName).orElse(null);
            if(tag==null){
                Tag newTag=new Tag(tagName);
                tagRepository.save(newTag);
                tags.add(newTag);
            }else{
                tags.add(tag);
            }
        }

        post.setTags(tags);
        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());
        postRepository.save(post);
        return post.getId();

    }

    public void updatePost(Long id, PostRequestDto requestDto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + id));

        if(!userDetails.hasRoleAdmin() && post.getBlog().getAccount().getId()!=userDetails.getAccount().getId()){
            //현 유저가 블로그 주인인지 확인.(또는 관리자)
            throw new RuntimeException("본인의 블로그가 아님");
        }

        //postRepository.save(post); 영속성 컨텍스트는 트랜잭션 커밋시점에 더티체크 후 자동저장됨
        post.update(requestDto);
    }

    public void deletePost(Long id, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + id));
        if(!userDetails.hasRoleAdmin() && post.getBlog().getAccount().getId()!=userDetails.getAccount().getId()){
            //현 유저가 블로그 주인인지 확인.(또는 관리자)
            throw new RuntimeException("본인의 블로그가 아님");
        }
        postRepository.delete(post);
    }

    public List<CommentResponseDto> getCommentsByPostId(Long id) {
        List<Comment> comments = commentRepository.findAllByPostId(id);
        List<CommentResponseDto> dtos = new ArrayList<>(comments.size());
        for(Comment comment: comments){
            dtos.add(new CommentResponseDto(comment));
        }
        return dtos;
    }
}
