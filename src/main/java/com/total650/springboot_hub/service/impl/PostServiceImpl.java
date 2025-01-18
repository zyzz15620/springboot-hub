package com.total650.springboot_hub.service.impl;

import com.total650.springboot_hub.entity.Account;
import com.total650.springboot_hub.entity.Category;
import com.total650.springboot_hub.entity.Post;
import com.total650.springboot_hub.exception.ResourceNotFoundException;
import com.total650.springboot_hub.payload.PostDto;
import com.total650.springboot_hub.payload.PostResponse;
import com.total650.springboot_hub.repository.AccountRepository;
import com.total650.springboot_hub.repository.CategoryRepository;
import com.total650.springboot_hub.repository.PostRepository;
import com.total650.springboot_hub.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private static ModelMapper mapper;
    private CategoryRepository categoryRepository;
    private AccountRepository accountRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper, CategoryRepository categoryRepository, AccountRepository accountRepository) {
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        // Get current user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username or email", username));
        
        // Get category by id
        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
        
        // Convert DTO to entity after validating relationships
        Post post = mapToEntity(postDto);
        post.setCategory(category);
        post.setAccount(account);
        
        Post newPost = postRepository.save(post);
        
        // Convert entity to DTO
        return mapToDTO(newPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        //If we want to sort by the value instead of label then do Sort.by(sortBy).descending()

        Page<Post> posts = postRepository.findAll(pageable);

        // get content for page object
        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content = listOfPosts.stream()
                .map(post -> mapToDTO(post))
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        // get post by id
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);

        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        List<Post> posts = postRepository.findByCategoryId(categoryId);
        return posts.stream().map((post) -> mapToDTO(post)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPosts(String query) {
        List<Post> posts = postRepository.searchPosts(query);
        return posts.stream().map((post) -> mapToDTO(post)).collect(Collectors.toList());
    }

    @Override
    public PostResponse getPostsByUserId(Long userId, int pageNo, int pageSize, String sortBy, String sortDir) {
        Account account = accountRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
                
        // Create Pageable instance
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        
        Page<Post> posts = postRepository.findByAccountId(userId, pageable);
        
        // Get content for page object
        List<Post> listOfPosts = posts.getContent();
        
        List<PostDto> content = listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
        
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        
        return postResponse;
    }

    private static PostDto mapToDTO(Post post) {
        PostDto postDto = mapper.map(post, PostDto.class);
        postDto.setAccountId(post.getAccount().getId());
        return postDto;
    }

    private static Post mapToEntity(PostDto postDto) {
        Post postEntity = mapper.map(postDto, Post.class);

//        Post postEntity = new Post();
//        postEntity.setTitle(postDto.getTitle());
//        postEntity.setDescription(postDto.getDescription());
//        postEntity.setContent(postDto.getContent());
        return postEntity;
    }
}
