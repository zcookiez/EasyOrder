package com.study.EasyOrder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    @Schema(description = "상품 고유 ID", example = "1")
    private Long productId;
    
    @Schema(description = "주문 수량", example = "2")
    private Integer orderQuantity;
    
    @Schema(description = "단일 상품 가격", example = "4500")
    private Integer price;
}

