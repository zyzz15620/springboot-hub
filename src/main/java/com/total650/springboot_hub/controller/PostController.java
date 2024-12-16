package com.total650.springboot_hub.controller;

import com.total650.springboot_hub.payload.PostDto;
import com.total650.springboot_hub.payload.PostResponse;
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
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){  //@RequestBody automatically read Json to Object
        PostDto responseDto = postService.createPost(postDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // Get all posts
    @GetMapping
    public PostResponse getAllPosts(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                    @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                    @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        // No need to return ResponseEntity<List<PostDto>> because status code default is 200 anyway
    }

    // Get post by id
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // Update post by id
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable(name = "id") long id){
        PostDto responseDto = postService.updatePost(postDto, id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // Delete post by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);
    }
}
