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
@Schema(description = "RegisterDto Model Information")
public class RegisterDto {
    @Schema(description = "Display name for the account")
    private String name;
    @Schema(description = "Username for the account")
    private String username;
    @Schema(description = "Email for the account")
    private String email;
    @Schema(description = "Password for the account")
    private String password;
}
