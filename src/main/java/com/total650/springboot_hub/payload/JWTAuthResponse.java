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
@Schema(description = "JWTResponse Model Information")
public class JWTAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";

}
