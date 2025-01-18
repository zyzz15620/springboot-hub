package com.total650.springboot_hub.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Schema(description = "PostDto Model Information")
public class PostDto {
    private Long id;

    @Schema(description = "Blog Post Title")
    @NotEmpty //Not null or Empty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    @Schema(description = "Blog Post Description")
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;

    @Schema(description = "Blog Post Content")
    @NotEmpty
    private String content;
    private Set<CommentDto> comments;

    @Schema(description = "Blog Post Category")
    private Long categoryId;

    @Schema(description = "Post Author ID")
    private Long accountId;
}
