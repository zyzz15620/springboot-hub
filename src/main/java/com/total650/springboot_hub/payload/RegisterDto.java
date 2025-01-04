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
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "RegisterDto Model Information")
public class RegisterDto {
    @Schema(description = "Display name for the account")
    @NotEmpty(message = "Name should not be null or empty")
    private String name;

    @Schema(description = "Username for the account")
    @NotEmpty(message = "Username should not be null or empty")
    private String username;

    @Schema(description = "Email for the account")
    @Email
    @NotEmpty(message = "Email should not be null or empty")
    private String email;

    @Schema(description = "Password for the account")
    @NotEmpty
    private String password;
}
