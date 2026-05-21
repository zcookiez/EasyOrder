# DOMAIN_ORDER.md

## 1. DB 스키마
```sql
CREATE TABLE orders (
    order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    order_quantity INT NOT NULL,
    total_price INT NOT NULL,
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_order_product FOREIGN KEY(product_id) REFERENCES product(product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## 2. Entity 규칙
- **패키지**: `com.study.EasyOrder.entity.Order`
- **연관관계**: `Product`와 `ManyToOne` (Lazy Loading 권장)
- **제약**: `product_id`를 외래키로 가지며, 주문 테이블에 상품명 등 가변 정보를 중복 저장하지 않음.

## 3. 비즈니스 규칙
- **금액 계산**: `totalPrice = product.price * orderQuantity` (주문 시 자동 계산)
- **재고 관리**:
    - 주문 전: `product.stock >= orderQuantity` 체크 (부족 시 예외 발생)
    - 주문 시: `product.stock`에서 수량만큼 차감
- **변경 제한**: 주문 데이터의 수정(Update)은 제공하지 않음 (필요 시 취소 후 재주문)
- **데이터 무결성**: 주문 이력이 있는 상품은 실제 DB 삭제 제한 (RESTRICT)

## 4. DTO 및 인터페이스 규칙
- **OrderRequest**: `productId`, `orderQuantity` 필수
- **OrderResponse**: `orderId`, `productId`, `productName`, `orderQuantity`, `totalPrice`, `orderDate` 포함
- **URL**: `/orders/**`
