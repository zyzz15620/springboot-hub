package com.total650.springboot_hub.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "CommentDto Model Information")
public class CommentDto {
    @Schema(description = "Comment id")
    private Long id;

    @Schema(description = "Name of user who leave the comment")
    @NotEmpty(message = "Name should not be null or empty")
    private String name;

    @Schema(description = "Email of user who leave the comment")
    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;

    @Schema(description = "Comment content")
    @NotEmpty(message = "Comment should not be null or empty")
    @Size(min = 10, message = "Comment should have at least 10 characters")
    private String body;
}
