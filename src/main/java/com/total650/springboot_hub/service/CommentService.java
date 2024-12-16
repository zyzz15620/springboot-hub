package com.total650.springboot_hub.service;

import com.total650.springboot_hub.payload.CommentDto;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
}
