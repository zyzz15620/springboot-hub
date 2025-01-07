package com.total650.springboot_hub.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Schema(description = "Update Profile Request")
public class UpdateProfileDto {
    @Schema(description = "User's name")
    @NotEmpty(message = "Name should not be null or empty")
    private String name;
    
    @Schema(description = "User's profile picture (Base64)")
    private String profilePicture;  // Store as Base64 string
} 