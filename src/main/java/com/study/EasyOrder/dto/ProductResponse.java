package com.study.EasyOrder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long productId;
    private String name;
    private Integer price;
    private Integer stock;
    private String description;
    private String imageUrl;
    private Boolean deleteYn;
    private LocalDateTime createdAt;
}
