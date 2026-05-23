package com.study.EasyOrder.service;

import com.study.EasyOrder.dto.OrderPageResponse;
import com.study.EasyOrder.dto.OrderRequest;

public interface OrderService {
    /** 주문 생성 (구매 처리) */
    void create(OrderRequest request);

    /** 주문 목록 조회 (페이징 + 검색) */
    OrderPageResponse findAll(int page, String search);
}
