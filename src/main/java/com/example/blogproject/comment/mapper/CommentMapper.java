//package com.example.blogproject.comment.mapper;
//
//import com.backend.domain.comment.dto.response.CommentResponseDto;
//import com.backend.domain.comment.entity.Comment;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//
//@Mapper
//public interface CommentMapper {
//
//    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);
//
//    @Mapping(source = "comment.user.username", target = "username")
//    CommentResponseDto toResponseDto(Comment comment);
//
//
//}
