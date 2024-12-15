package com.total650.springboot_hub.service.impl;

import com.total650.springboot_hub.model.Post;
import com.total650.springboot_hub.payload.PostDto;
import com.total650.springboot_hub.repository.PostRepository;
import com.total650.springboot_hub.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto){
        // Received request body as DTO in PostController
        // Now convert that DTO to entity
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post newPostEntity = postRepository.save(post);

        // convert entity to DTO, now we can send back more or less info. In this case we add ID to response
        PostDto postResponse = new PostDto();
        postResponse.setId(newPostEntity.getId());
        postResponse.setTitle(newPostEntity.getTitle());
        postResponse.setDescription(newPostEntity.getDescription());
        postResponse.setContent(newPostEntity.getContent());

        return postResponse;
    }
}
