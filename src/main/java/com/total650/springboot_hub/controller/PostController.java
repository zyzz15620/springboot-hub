package com.total650.springboot_hub.controller;

import com.total650.springboot_hub.payload.PostDto;
import com.total650.springboot_hub.payload.PostResponse;
import com.total650.springboot_hub.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "CRUD REST APIs for Post Resource")
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "Create Post REST API", description = "Create Post REST API is used to save post into database")
    @ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){  //@RequestBody automatically read Json to Object
        PostDto responseDto = postService.createPost(postDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Get Posts REST API", description = "Get Posts REST API is used to get all posts from database, supporting pagination")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @GetMapping
    public PostResponse getAllPosts(@RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                    @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
                                    @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        // No need to return ResponseEntity<List<PostDto>> because status code default is 200 anyway
    }

    @Operation(summary = "Get Post REST API", description = "Get Post REST API is used to get a single")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @Operation(summary = "Update Post REST API", description = "Update Post REST API is used to update a particular post")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id){
        PostDto responseDto = postService.updatePost(postDto, id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "Delete Post REST API", description = "Delete Post REST API is used to delete a particular post")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);
    }

    @Operation(summary = "Get Posts With Category REST API", description = "Get Post With Category REST API is used to get all posts that belong to a category")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable(name = "id") Long categoryId){
        List<PostDto> postDtoList = postService.getPostsByCategoryId(categoryId);
        return ResponseEntity.ok(postDtoList);
    }

    @Operation(summary = "Get Posts By Query REST API", description = "Get Post With Query REST API is used to search all posts that the title or description contains query key")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @GetMapping("/search")
    public ResponseEntity<List<PostDto>> searchPosts(@RequestParam String query){
        return ResponseEntity.ok(postService.searchPosts(query)); // /search?query=selenium
    }

    @Operation(summary = "Get Posts By User", description = "Get all posts by a specific user with pagination and sorting")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successfully retrieved posts"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/user/{userId}")
    public PostResponse getPostsByUser(
        @PathVariable(value = "userId") Long userId,
        @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int pageNo,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
        @RequestParam(defaultValue = DEFAULT_SORT_BY) String sortBy,
        @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION) String sortDir
    ) {
        return postService.getPostsByUserId(userId, pageNo, pageSize, sortBy, sortDir);
    }
}
