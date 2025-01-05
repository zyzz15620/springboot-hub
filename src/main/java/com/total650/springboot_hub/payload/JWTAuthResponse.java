package com.total650.springboot_hub.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "JWT Authentication Response")
public class JWTAuthResponse {
    @Schema(description = "Access token")
    private String accessToken;
    @Schema(description = "Token type")
    private String tokenType = "Bearer";
    @Schema(description = "User role")
    private String role;
    @Schema(description = "Username")
    private String username;
}
