package com.total650.springboot_hub.controller;

import com.total650.springboot_hub.payload.PostDto;
import com.total650.springboot_hub.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // Create blog post
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        PostDto responseDto = postService.createPost(postDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // Get all posts
    @GetMapping
    public List<PostDto> getAllPosts(){
        return postService.getAllPosts();
    }
}
