package com.total650.springboot_hub.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Pagination Response Model Information")
public class PostResponse {
    @Schema(description = "List of Blog Posts in a particular page")
    private List<PostDto> content;
    @Schema(description = "Current page number")
    private Integer pageNo;
    @Schema(description = "Number of Blog Posts in a single page")
    private Integer pageSize;
    @Schema(description = "Number of all Blog Posts in database")
    private Long totalElements;
    @Schema(description = "Number of pages, calculated from totalElement/pageSize")
    private Integer totalPages;
    @Schema(description = "Return true if current page is last page")
    private Boolean last;
}
