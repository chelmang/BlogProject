package com.example.blogproject.category;

import com.example.blogproject.blog.Blog;
import com.example.blogproject.blog.BlogDto;
import com.example.blogproject.blog.BlogRepository;
import com.example.blogproject.post.dto.PostResponseDto;
import com.example.blogproject.post.entity.Post;
import com.example.blogproject.post.repository.PostRepository;
import com.example.blogproject.security.user.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final BlogRepository blogRepository;
    private final PostRepository postRepository;
    public Long createCategory(CategoryDto categoryDto, UserDetailsImpl userDetails) {
        Blog blog = blogRepository.findById(categoryDto.getBlogId()).orElseThrow(
                ()-> new RuntimeException("블로그가 존재하지 않음")
        );
        if(blog.getAccount().getId()!=userDetails.getAccount().getId()&& !userDetails.hasRoleAdmin()){
            throw new RuntimeException("본인의 블로그가 아님");
        }
        Category category = new Category(categoryDto,blog);
        categoryRepository.save(category);
        return category.getId();
    }

    public CategoryDto getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                ()->new RuntimeException("카테고리가 존재하지 않음")
        );
        return new CategoryDto(category);
    }

    public List<CategoryDto> getAllCategoriesByBlogId(Long blogId) {
        List<Category> categories = categoryRepository.findByBlogId(blogId);
        List<CategoryDto> categoryDtos = new ArrayList<>(categories.size());
        for(Category category: categories){
            categoryDtos.add(new CategoryDto(category));
        }
        return categoryDtos;
    }

    public void updateCategory(Long categoryId, CategoryDto categoryDto, UserDetailsImpl userDetails) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                ()->new RuntimeException("카테고리가 존재하지 않음")
        );
        if(!userDetails.hasRoleAdmin()&& userDetails.getAccount().getId()!=category.getBlog().getAccount().getId()){
            throw new RuntimeException("본인의 블로그 카테고리가 아님");
        }
        category.update(categoryDto);
    }

    public void deleteCategory(Long categoryId, UserDetailsImpl userDetails) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                ()->new RuntimeException("카테고리가 존재하지 않음")
        );
        if(!userDetails.hasRoleAdmin()&& userDetails.getAccount().getId()!=category.getBlog().getAccount().getId()){
            throw new RuntimeException("본인의 블로그 카테고리가 아님");
        }
        categoryRepository.delete(category);
    }

    public List<PostResponseDto> getAllPostDtosByCategoryId(Long categoryId) {

        List<Post> posts = postRepository.findAllByCategoryId(categoryId);
        List<PostResponseDto> dtos = new ArrayList<>(posts.size());
        for(Post post:posts){
            dtos.add(new PostResponseDto(post));
        }
        return dtos;
    }
}
