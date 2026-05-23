package com.study.EasyOrder.controller.api;

import com.study.EasyOrder.dto.ProductPageResponse;
import com.study.EasyOrder.dto.ProductRequest;
import com.study.EasyOrder.dto.ProductResponse;
import com.study.EasyOrder.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Swagger / Postman 테스트용 상품 API Controller
 */
@Tag(name = "Product", description = "상품 관리 API")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductApiController {

    private final ProductService productService;

    @Operation(summary = "상품 목록 조회", description = "페이징, 검색어, 판매중 상품 필터링을 지원하는 상품 목록 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping
    public ResponseEntity<ProductPageResponse> getProductList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "false") boolean activeOnly,
            @RequestParam(defaultValue = "") String search) {
        return ResponseEntity.ok(productService.findAll(page, activeOnly, search));
    }

    @Operation(summary = "상품 상세 조회", description = "ID를 기준으로 상품의 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    })
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.findById(productId));
    }

    @Operation(summary = "상품 등록", description = "신규 상품을 등록합니다.")
    @ApiResponse(responseCode = "200", description = "등록 성공")
    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody ProductRequest request) {
        productService.create(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "상품 수정", description = "기존 상품의 정보를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "수정 성공")
    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable Long productId, 
            @RequestBody ProductRequest request) {
        productService.update(productId, request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "선택 상품 판매 중지", description = "ID 목록을 받아 여러 상품의 판매 상태를 '중지'로 변경합니다.")
    @ApiResponse(responseCode = "200", description = "상태 변경 성공")
    @PostMapping("/delete")
    public ResponseEntity<Void> deleteProducts(@RequestBody List<Long> productIds) {
        productService.deleteProducts(productIds);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "선택 상품 판매 재개", description = "ID 목록을 받아 여러 상품의 판매 상태를 '판매중'으로 변경합니다.")
    @ApiResponse(responseCode = "200", description = "상태 복구 성공")
    @PostMapping("/restore")
    public ResponseEntity<Void> restoreProducts(@RequestBody List<Long> productIds) {
        productService.restoreProducts(productIds);
        return ResponseEntity.ok().build();
    }
}

