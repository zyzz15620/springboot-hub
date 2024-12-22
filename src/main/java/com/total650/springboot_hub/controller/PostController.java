package com.total650.springboot_hub.controller;

import com.total650.springboot_hub.payload.PostDto;
import com.total650.springboot_hub.payload.PostResponse;
import com.total650.springboot_hub.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.total650.springboot_hub.utils.AppConstants.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // Create blog post
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){  //@RequestBody automatically read Json to Object
        PostDto responseDto = postService.createPost(postDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // Get all posts
    @GetMapping
    public PostResponse getAllPosts(@RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                    @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
                                    @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        // No need to return ResponseEntity<List<PostDto>> because status code default is 200 anyway
    }

    // Get post by id
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // Update post by id
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id){
        PostDto responseDto = postService.updatePost(postDto, id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // Delete post by id
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);
    }

    // Get all posts by category
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable(name = "id") Long categoryId){
        List<PostDto> postDtoList = postService.getPostsByCategoryId(categoryId);
        return ResponseEntity.ok(postDtoList);
    }
}
