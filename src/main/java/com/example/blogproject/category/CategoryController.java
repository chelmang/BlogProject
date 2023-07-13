package com.example.blogproject.category;

import com.example.blogproject.post.dto.PostResponseDto;
import com.example.blogproject.security.user.CurrentUser;
import com.example.blogproject.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping("/create")
    @Secured("ROLE_USER")
    ResponseEntity<Long> createCategory(@RequestBody CategoryDto categoryDto, @CurrentUser UserDetailsImpl userDetails){
        Long categoryId = categoryService.createCategory(categoryDto, userDetails);
        return ResponseEntity.ok(categoryId);
    }

    @GetMapping("/{categoryId}")
    ResponseEntity<CategoryDto> getCategory(@PathVariable Long categoryId){
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(categoryDto);
    }

    @GetMapping("/{categoryId}/posts")
    ResponseEntity<List<PostResponseDto>> getAllPostsByCategory(@PathVariable Long categoryId){
        List<PostResponseDto> postDtos = categoryService.getAllPostDtosByCategoryId(categoryId);
        return ResponseEntity.ok(postDtos);
    }

    @GetMapping("/getAllCategoriesByBlogId/{blogId}") //블로그에 속한 카테고리들을 나열
    ResponseEntity<List<CategoryDto>> getAllCategoriesByBlogId(@PathVariable Long blogId){
        List<CategoryDto> categoryDtos = categoryService.getAllCategoriesByBlogId(blogId);
        return ResponseEntity.ok(categoryDtos);
    }

    @PatchMapping("/{categoryId}")
    ResponseEntity<Void> updateCategory(@PathVariable Long categoryId,@RequestBody CategoryDto categoryDto, @CurrentUser UserDetailsImpl userDetails){
        categoryService.updateCategory(categoryId,categoryDto,userDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId, @CurrentUser UserDetailsImpl userDetails){
        categoryService.deleteCategory(categoryId,userDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
