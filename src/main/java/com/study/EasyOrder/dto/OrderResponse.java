package com.study.EasyOrder.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrderResponse {
    private Long orderId;
    private Long productId;
    private String productName;
    private Integer orderQuantity;
    private Integer totalPrice;
    private LocalDateTime orderDate;
}
