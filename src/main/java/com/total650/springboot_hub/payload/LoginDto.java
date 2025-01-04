package com.total650.springboot_hub.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "LoginDto Model Information")
public class LoginDto {
    @Schema(description = "Username or Email to login")
    @NotEmpty(message = "Email or Username should not be null or empty")
    private String usernameOrEmail;
    @Schema(description = "Password to login")
    @NotEmpty(message = "Password should not be null or empty")
    private String password;
}
