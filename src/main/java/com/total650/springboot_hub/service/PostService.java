package com.total650.springboot_hub.service;

import com.total650.springboot_hub.payload.PostDto;
import com.total650.springboot_hub.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePost(long id);

}
