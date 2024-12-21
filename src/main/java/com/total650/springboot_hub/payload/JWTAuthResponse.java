package com.total650.springboot_hub.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JWTAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";

}
