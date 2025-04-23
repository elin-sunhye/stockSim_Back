package com.elin.stocksim_back.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class RespCommonListDto<T> {
    @Schema(description = "페이징")
    private int page;

    @Schema(description = "게시글 겟수")
    private int limitCount;

    @Schema(description = "다음 페이지")
    private int nextPage;

    @Schema(description = "총 페이지")
    private int totalPages;

    @Schema(description = "첫 페이지 여부")
    private boolean isFirstPage;

    @Schema(description = "마지막 페이지 여부")
    private boolean isLastPage;

    @Schema(description = "총 데이터 갯수")
    private int totalElements;

    @Schema(description = "데이터 리스트")
    private List<T> dataList;
}
