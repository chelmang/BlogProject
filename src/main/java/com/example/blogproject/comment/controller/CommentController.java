package com.example.blogproject.comment.controller;

import com.example.blogproject.comment.dto.CommentRequestDto;
import com.example.blogproject.comment.dto.CommentResponseDto;
import com.example.blogproject.comment.entity.Comment;
import com.example.blogproject.comment.service.CommentService;
import com.example.blogproject.security.user.CurrentUser;
import com.example.blogproject.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/")
    @Secured("ROLE_USER")
    public ResponseEntity<Long> createComment(@RequestBody CommentRequestDto requestDto,@CurrentUser UserDetailsImpl userDetails) {
        Long commentId = commentService.createComment(requestDto,userDetails);

        return ResponseEntity.ok(commentId);
    }
    @GetMapping("/posts/{postId}")
    public ResponseEntity<List<CommentResponseDto>> getPostComments(@PathVariable Long postId) {
        List<CommentResponseDto> comments = commentService.getPostComments(postId);

        return ResponseEntity.ok(comments);
    }

    @PatchMapping("/{commentId}")
    @Secured("ROLE_USER")
    public ResponseEntity<Void> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, @CurrentUser UserDetailsImpl userDetails) {
        commentService.updateComment(commentId, requestDto, userDetails);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    @Secured("ROLE_USER")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, @CurrentUser UserDetailsImpl userDetails) {
        commentService.deleteComment(commentId,userDetails);
        return ResponseEntity.ok().build();
    }
}
