package com.total650.springboot_hub.repository;

import com.total650.springboot_hub.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    //No need to write anything here because JpaRepository already provides all CRUD methods
    //JpaRepository also extends PagingAndSortingRepository interface. That's mean we also got sorting methods
    //We also don't need to add @Repository to this interface class because it already has it internally
}
