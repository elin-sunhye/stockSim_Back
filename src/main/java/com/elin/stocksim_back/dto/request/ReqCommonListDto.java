package com.elin.stocksim_back.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ReqCommonListDto {
    @Schema(description = "페이징")
    private int page;

    @Schema(description = "게시글 겟수")
    private int limitCount;

    @Schema(description = "정렬")
    private String order;

    @Schema(description = "검색어")
    private String searchText;
}
