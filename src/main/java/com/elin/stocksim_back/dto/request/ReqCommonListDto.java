package com.elin.stocksim_back.dto.request;

import lombok.Data;

@Data
public class ReqCommonListDto<T> {
    private int page;
    private int limitCount;
    private String order;
    private String searchText;
    private T data;
}
