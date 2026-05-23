package com.study.EasyOrder.controller.api;

import com.study.EasyOrder.dto.OrderPageResponse;
import com.study.EasyOrder.dto.OrderRequest;
import com.study.EasyOrder.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Swagger / Postman 테스트용 주문 API Controller
 */
@Tag(name = "Order", description = "주문 관리 API")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    @Operation(summary = "주문 목록 조회", description = "전체 주문 내역을 페이징 및 상품명 검색 조건으로 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping
    public ResponseEntity<OrderPageResponse> getOrderList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String search) {
        return ResponseEntity.ok(orderService.findAll(page, search));
    }

    @Operation(summary = "상품 구매", description = "상품을 구매하여 새로운 주문을 생성합니다.")
    @ApiResponse(responseCode = "200", description = "구매 성공")
    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody OrderRequest request) {
        orderService.create(request);
        return ResponseEntity.ok().build();
    }
}

