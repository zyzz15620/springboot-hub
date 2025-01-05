package com.total650.springboot_hub.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Account Information")
public class AccountDto {
    @Schema(description = "User's name")
    private String name;
    
    @Schema(description = "Username")
    private String username;
    
    @Schema(description = "Email")
    private String email;
    
    @Schema(description = "Profile picture")
    private String profilePicture;
} 