package com.total650.springboot_hub.service.impl;

import com.total650.springboot_hub.entity.Post;
import com.total650.springboot_hub.payload.PostDto;
import com.total650.springboot_hub.repository.PostRepository;
import com.total650.springboot_hub.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        // Now convert that DTO to Entity to save in database
        Post postEntity = mapToEntity(postDto);
        Post newPostEntity = postRepository.save(postEntity); //now the newer entity got its ID back from database

        // convert the newer entity back to DTO, but now we can choose to send back more or less info. In this case we add ID to response, check in mapToDTO()
        return mapToDTO(newPostEntity);
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(post -> mapToDTO(post))
                .collect(Collectors.toList());
    }

    private static PostDto mapToDTO(Post newPostEntity) {
        PostDto postDto = new PostDto();
        postDto.setId(newPostEntity.getId());
        postDto.setTitle(newPostEntity.getTitle());
        postDto.setDescription(newPostEntity.getDescription());
        postDto.setContent(newPostEntity.getContent());
        return postDto;
    }

    private static Post mapToEntity(PostDto postDto) {
        Post postEntity = new Post();
        postEntity.setTitle(postDto.getTitle());
        postEntity.setDescription(postDto.getDescription());
        postEntity.setContent(postDto.getContent());
        return postEntity;
    }
}
