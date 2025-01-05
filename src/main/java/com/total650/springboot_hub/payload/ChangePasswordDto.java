package com.total650.springboot_hub.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Schema(description = "Change Password Request")
public class ChangePasswordDto {
    @Schema(description = "Current password")
    @NotEmpty(message = "Current password should not be null or empty")
    private String currentPassword;

    @Schema(description = "New password")
    @NotEmpty(message = "New password should not be null or empty")
    private String newPassword;
} 