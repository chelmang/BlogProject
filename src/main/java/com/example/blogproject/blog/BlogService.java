package com.example.blogproject.blog;

import com.example.blogproject.category.Category;
import com.example.blogproject.category.CategoryDto;
import com.example.blogproject.category.CategoryRepository;
import com.example.blogproject.post.dto.PostResponseDto;
import com.example.blogproject.post.entity.Post;
import com.example.blogproject.post.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import com.example.blogproject.account.entity.Account;
import com.example.blogproject.security.user.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    public Long createBlog(BlogDto blogDto, UserDetailsImpl userDetails){
        Account account = userDetails.getAccount();
        Blog blog = new Blog(account,blogDto);
        blogRepository.save(blog);
        return blog.getId();
    }

    public BlogDto getBlogByBlogId(Long blogId) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(
                ()->new RuntimeException("블로그가 존재하지 않음")
        );
        return new BlogDto(blog);
    }

    public List<BlogDto> getAllBlogs() {
        List<Blog> blogs = blogRepository.findAll();
        List<BlogDto> blogDtos = new ArrayList<>(blogs.size());
        for(Blog blog: blogs){
            blogDtos.add(new BlogDto(blog));
        }
        return blogDtos;
    }

    public List<BlogDto> getCurrentUsersBlogs(UserDetailsImpl userDetails) {
        List<Blog> blogs = blogRepository.findByAccountId(userDetails.getAccount().getId());
        List<BlogDto> blogDtos = new ArrayList<>(blogs.size());
        for(Blog blog: blogs){
            blogDtos.add(new BlogDto(blog));
        }
        return blogDtos;
    }

    public void updateBlog(Long blogId, BlogDto blogDto, UserDetailsImpl userDetails) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(
                ()-> new RuntimeException("블로그가 존재하지 않음")
        );
        log.info(userDetails.getAuthorities().toString());
        log.info(userDetails.hasRoleAdmin()?"YES":"NO");
        if(blog.getAccount().getId()!=userDetails.getAccount().getId() && !userDetails.hasRoleAdmin()){
            //관리자권한도 없고 글 작성자도 아닌경우
            throw new RuntimeException("블로그 작성자가 아님.");
        }

        blog.update(blogDto);
    }

    public void deleteBlog(Long blogId, UserDetailsImpl userDetails) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(
                ()-> new RuntimeException("블로그가 존재하지 않음")
        );
        log.info(userDetails.getAuthorities().toString());
        log.info(userDetails.hasRoleAdmin()?"YES":"NO");
        if(blog.getAccount().getId()!=userDetails.getAccount().getId() && !userDetails.hasRoleAdmin()){
            //관리자권한도 없고 글 작성자도 아닌경우
            throw new RuntimeException("블로그 작성자가 아님.");
        }

        blogRepository.delete(blog);
    }

    public List<PostResponseDto> getPostsByBlogId(Long blogId) {
        List<Post> posts = postRepository.findAllByBlogId(blogId);
        List<PostResponseDto> dtos = new ArrayList<>(posts.size());
        for(Post post:posts){
            dtos.add(new PostResponseDto(post));
        }
        return dtos;
    }

    public List<CategoryDto> getCategoriesByBlogId(Long blogId) {
        List<Category> categories =categoryRepository.findAllByBlogId(blogId);
        List<CategoryDto> dtos = new ArrayList<>(categories.size());
        for(Category category:categories){
            dtos.add(new CategoryDto(category));
        }
        return dtos;
    }
}
