package com.study.EasyOrder.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderPageResponse {
    private List<OrderResponse> content;
    private int currentPage;
    private int totalPages;
    private long totalElements;
}
