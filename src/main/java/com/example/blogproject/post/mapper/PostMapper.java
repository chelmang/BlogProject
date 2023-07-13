//package com.example.blogproject.post.mapper;
//
//
//import com.example.blogproject.post.dto.PostResponseDto;
//import com.example.blogproject.post.entity.Post;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//
//@Mapper(componentModel = "spring")
//public interface PostMapper {
//
//    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);
//
//    @Mapping(target = "id", source = "post.id")
//    @Mapping(target = "title", source = "post.title")
//    @Mapping(target = "content", source = "post.content")
//    @Mapping(target = "createdAt", source = "post.createdAt")
//    PostResponseDto toResponseDto(Post post);
//
//}
