package com.total650.springboot_hub.payload;

import com.total650.springboot_hub.entity.Comment;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String description;
    private String content;
    private Set<CommentDto> comments;
}
