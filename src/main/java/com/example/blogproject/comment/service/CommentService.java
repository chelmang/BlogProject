package com.example.blogproject.comment.service;

import com.example.blogproject.comment.dto.CommentRequestDto;
import com.example.blogproject.comment.dto.CommentResponseDto;
import com.example.blogproject.comment.entity.Comment;
import com.example.blogproject.comment.repository.CommentRepository;
import com.example.blogproject.post.entity.Post;
import com.example.blogproject.post.repository.PostRepository;
import com.example.blogproject.security.user.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public List<CommentResponseDto> getPostComments(Long postId) {
        // postId에 해당하는 게시물의 전체 댓글 조회 로직 구현
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        List<CommentResponseDto> dtos = new ArrayList<>(comments.size());
        for(Comment comment: comments){
            dtos.add(new CommentResponseDto(comment));
        }
        return dtos;
    }

    public void updateComment(Long commentId, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment ID: " + commentId));

        if(!userDetails.hasRoleAdmin() && userDetails.getAccount().getId()!=comment.getAccount().getId()){
            throw new RuntimeException("본인의 댓글이 아님");
        }
        comment.setContent(requestDto.getContent());

    }

    public void deleteComment(Long commentId, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment ID: " + commentId));

        if(!userDetails.hasRoleAdmin() && userDetails.getAccount().getId()!=comment.getAccount().getId()){
            throw new RuntimeException("본인의 댓글이 아님");
        }
        commentRepository.delete(comment);
    }

    public Long createComment(CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(requestDto.getPostId())
                .orElseThrow(()-> new RuntimeException("Invalid post ID: "+requestDto.getPostId()));

        Comment comment = new Comment(requestDto,userDetails.getAccount(),post);
        commentRepository.save(comment);
        return comment.getId();
    }
}
