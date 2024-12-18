package com.total650.springboot_hub.service.impl;

import com.total650.springboot_hub.entity.Post;
import com.total650.springboot_hub.exception.ResourceNotFoundException;
import com.total650.springboot_hub.payload.PostDto;
import com.total650.springboot_hub.payload.PostResponse;
import com.total650.springboot_hub.repository.PostRepository;
import com.total650.springboot_hub.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private static ModelMapper mapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto){
        // Received request body as DTO in PostController
        // Now convert that DTO to Entity to save in database
        Post postEntity = mapToEntity(postDto);
        Post newPostEntity = postRepository.save(postEntity); //now the newer entity got its ID back from database

        // convert the newer entity back to DTO, but now we can choose to send back more or less info. In this case we add ID to response, check in mapToDTO()
        return mapToDTO(newPostEntity);
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

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    private static PostDto mapToDTO(Post newPostEntity) {
        PostDto postDto = mapper.map(newPostEntity, PostDto.class);

//        PostDto postDto = new PostDto();
//        postDto.setId(newPostEntity.getId());
//        postDto.setTitle(newPostEntity.getTitle());
//        postDto.setDescription(newPostEntity.getDescription());
//        postDto.setContent(newPostEntity.getContent());
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
