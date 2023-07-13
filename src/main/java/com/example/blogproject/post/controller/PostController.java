package com.example.blogproject.post.controller;

import com.example.blogproject.comment.dto.CommentResponseDto;
import com.example.blogproject.post.dto.PostRequestDto;
import com.example.blogproject.post.dto.PostResponseDto;
import com.example.blogproject.post.service.PostService;
import com.example.blogproject.security.user.CurrentUser;
import com.example.blogproject.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id) {
        PostResponseDto postResponseDto = postService.getPost(id);
        return ResponseEntity.ok(postResponseDto);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPost(@PathVariable Long id) {
        List<CommentResponseDto> dtos = postService.getCommentsByPostId(id);
        return ResponseEntity.ok(dtos);
    }
    @PostMapping("/")
    public ResponseEntity<?> createPost(@RequestBody PostRequestDto requestDto, @CurrentUser UserDetailsImpl userDetails) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        Long accountId = userDetails.getAccount().getId();
        //이 코드들은 @CurrentUser 로 줄임. 후에 메소드에는 userDetails를 인자로 주도록 변경

        Long postId = postService.createPost(requestDto, userDetails);
        return ResponseEntity.ok(postId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @CurrentUser UserDetailsImpl userDetails) {
        postService.updatePost(id, requestDto, userDetails); //인자에 userDetails 추가
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id ,@CurrentUser UserDetailsImpl userDetails) {
        postService.deletePost(id,userDetails);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/posts/getAuth")
//    public ResponseEntity<List<String>> getAuthorities() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//
//        List<String> authorities = userDetails.getAuthorities().stream()
//                .map(Object::toString)
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(authorities);
//    }
}
