package com.total650.springboot_hub.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "CategoryDto Model Information")
public class CategoryDto {
//    @Schema(description = "Category id")
//    private Long id;
    @Schema(description = "Category name")
    @NotEmpty(message = "Category name should not be null or empty")
    private String name;
    @Schema(description = "Category description")
    private String description;
}
