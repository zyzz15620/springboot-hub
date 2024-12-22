package com.total650.springboot_hub.payload;

import io.swagger.v3.oas.annotations.media.Schema;
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
    private String usernameOrEmail;
    @Schema(description = "Password to login")
    private String password;
}
