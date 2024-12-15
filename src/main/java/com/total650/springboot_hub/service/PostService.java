package com.total650.springboot_hub.service;

import com.total650.springboot_hub.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts();
}
