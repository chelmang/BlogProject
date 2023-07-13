package com.example.blogproject.blog;

import com.example.blogproject.category.CategoryDto;
import com.example.blogproject.post.dto.PostResponseDto;
import com.example.blogproject.security.user.CurrentUser;
import com.example.blogproject.security.user.UserDetailsImpl;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/blog")
public class BlogController {

    private final BlogService blogService;

    @PostMapping("/")
    @Secured("ROLE_USER")
    ResponseEntity<Long> createBlog(@RequestBody BlogDto blogDto, @CurrentUser UserDetailsImpl userDetails){
        Long blogId = blogService.createBlog(blogDto, userDetails);
        return ResponseEntity.ok(blogId);
    }

    @GetMapping("/{blogId}")
    ResponseEntity<BlogDto> getBlog(@PathVariable Long blogId){
        BlogDto blogDto = blogService.getBlogByBlogId(blogId);
        return ResponseEntity.ok(blogDto);
    }

    @GetMapping("/{blogId}/categories")
    ResponseEntity<List<CategoryDto>> getCategoriesByBlog(@PathVariable Long blogId){
        List<CategoryDto> categoryDtos = blogService.getCategoriesByBlogId(blogId);
        return ResponseEntity.ok(categoryDtos);
    }

    @GetMapping("/getAllBlogs")
    ResponseEntity<List<BlogDto>> getAllBlogs(){
        List<BlogDto> blogDtos = blogService.getAllBlogs();
        return ResponseEntity.ok(blogDtos);
    }

    @GetMapping("/getCurrentUsersBlogs")
    @Secured("ROLE_USER")
    ResponseEntity<List<BlogDto>> getCurrentUsersBlogs(@CurrentUser UserDetailsImpl userDetails){
        List<BlogDto> blogDtos= blogService.getCurrentUsersBlogs(userDetails);
        return ResponseEntity.ok(blogDtos);
    }

    @GetMapping("/{blogId}/posts") //특정 블로그 내에 있는 post들을 리턴
    ResponseEntity<List<PostResponseDto>> getPostsByBlog(@PathVariable Long blogId){
        List<PostResponseDto> postDtos= blogService.getPostsByBlogId(blogId);
        return ResponseEntity.ok(postDtos);
    }
    @PatchMapping("/{blogId}")
    @Secured("ROLE_USER")
    ResponseEntity<Void> updateBlog(@PathVariable Long blogId,@RequestBody BlogDto blogDto, @CurrentUser UserDetailsImpl userDetails){
        blogService.updateBlog(blogId,blogDto,userDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{blogId}")
    @Secured("ROLE_USER")
    ResponseEntity<Void> deleteBlog(@PathVariable Long blogId, @CurrentUser UserDetailsImpl userDetails){
        blogService.deleteBlog(blogId,userDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
