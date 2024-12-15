package com.total650.springboot_hub.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PostDto {
    private long id;
    private String title;
    private String description;
    private String content;
}
