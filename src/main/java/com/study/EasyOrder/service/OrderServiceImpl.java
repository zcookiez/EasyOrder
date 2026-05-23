package com.study.EasyOrder.service;

import com.study.EasyOrder.dto.OrderPageResponse;
import com.study.EasyOrder.dto.OrderRequest;
import com.study.EasyOrder.dto.OrderResponse;
import com.study.EasyOrder.entity.Order;
import com.study.EasyOrder.entity.Product;
import com.study.EasyOrder.repository.OrderRepository;
import com.study.EasyOrder.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void create(OrderRequest request) {
        // 1. 상품 조회
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        // 2. 재고 확인
        if (product.getStock() < request.getOrderQuantity()) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }

        // 3. 가격 변경 검증
        if (!product.getPrice().equals(request.getPrice())) {
            throw new IllegalArgumentException("상품 가격이 변경되었습니다. 다시 시도해주세요.");
        }

        // 4. 재고 차감 (JPA Dirty Checking 사용)
        product.setStock(product.getStock() - request.getOrderQuantity());

        // 5. Order 저장
        Order order = new Order();
        order.setProduct(product);
        order.setOrderQuantity(request.getOrderQuantity());
        order.setTotalPrice(request.getPrice() * request.getOrderQuantity());
        
        orderRepository.save(order);
    }

    @Override
    public OrderPageResponse findAll(int page, String search) {
        // 1. 페이징 및 정렬 조건 설정 (한 페이지당 10개, 최신 주문순)
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "orderId"));

        // 2. 검색어 유무에 따른 조회 분개
        Page<Order> orderPage;
        if (search != null && !search.trim().isEmpty()) {
            orderPage = orderRepository.findByProduct_NameContainingIgnoreCase(search.trim(), pageable);
        } else {
            orderPage = orderRepository.findAll(pageable);
        }

        // 3. Entity를 Response DTO로 변환
        List<OrderResponse> content = orderPage.getContent().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        // 4. 페이징 응답 객체 생성
        return OrderPageResponse.builder()
                .content(content)
                .currentPage(orderPage.getNumber())
                .totalPages(orderPage.getTotalPages())
                .totalElements(orderPage.getTotalElements())
                .build();
    }

    private OrderResponse convertToResponse(Order order) {
        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .productId(order.getProduct().getProductId())
                .productName(order.getProduct().getName())
                .orderQuantity(order.getOrderQuantity())
                .totalPrice(order.getTotalPrice())
                .orderDate(order.getOrderDate())
                .build();
    }
}
