package com.study.EasyOrder.service;

import com.study.EasyOrder.dto.ProductPageResponse;
import com.study.EasyOrder.dto.ProductResponse;
import com.study.EasyOrder.dto.ProductRequest;

import java.util.List;

public interface ProductService {

    // ==========================================
    // [1] 상품 등록 (Create)
    // ==========================================

    /** 상품 저장 처리 */
    void create(ProductRequest request);

    // ==========================================
    // [2] 상품 조회 (Read)
    // ==========================================

    /** 상품 목록 조회 */
    ProductPageResponse findAll(int page, boolean activeOnly);

    /** 상품 상세 조회 */
    ProductResponse findById(Long productId);

    // ==========================================
    // [3] 상품 수정 (Update)
    // ==========================================

    /** 상품 수정 처리 */
    void update(Long productId, ProductRequest request);

    // ==========================================
    // [4] 상품 관리 (Delete / Restore)
    // ==========================================

    /** 선택 상품 판매 중지 (Soft Delete) */
    void deleteProducts(List<Long> productIds);

    /** 선택 상품 판매 재개 (Restore) */
    void restoreProducts(List<Long> productIds);
}
