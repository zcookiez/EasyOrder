package com.study.EasyOrder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    @Schema(description = "상품명", example = "아메리카노")
    private String name;
    
    @Schema(description = "가격", example = "4500")
    private Integer price;
    
    @Schema(description = "재고 수량", example = "100")
    private Integer stock;
    
    @Schema(description = "상품 설명", example = "시원하고 깔끔한 맛의 아메리카노입니다.")
    private String description;
    
    @Schema(description = "기존 이미지 URL (수정 시 사용)", example = "/upload/example.jpg")
    private String imageUrl;
    
    @Schema(description = "새 이미지 파일 (MultipartFile)", hidden = true)
    private MultipartFile image;
}

