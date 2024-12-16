package com.total650.springboot_hub.repository;

import com.total650.springboot_hub.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}