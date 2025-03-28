package com.elin.stocksim_back.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class RespCommonListDto<T> {
    private int limitCount;
    private int page;
    private int nextPage;
    private int totalPages;
    private boolean isFirstPage;
    private boolean isLastPage;
    private int totalElements;
    private List<T> datas;
}
