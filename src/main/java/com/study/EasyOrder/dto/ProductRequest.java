package com.study.EasyOrder.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String name;
    private Integer price;
    private Integer stock;
    private String description;
    private String imageUrl;
    private MultipartFile image;
}
