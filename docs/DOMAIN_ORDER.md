# DOMAIN_ORDER.md

## 목적

EasyOrder 주문(Order) 도메인

상품(Product)을 주문하고,
주문 수량과 총 금액을 관리한다.

주문은 반드시 하나의 상품(Product)에 연결된다.

---

## DB 스키마
```
CREATE TABLE orders (
order_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '주문 고유 번호',
    product_id BIGINT NOT NULL COMMENT '주문 상품 ID',
    order_quantity INT NOT NULL COMMENT '주문 수량',
    total_price INT NOT NULL COMMENT '총 결제 금액',
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_order_product
    FOREIGN KEY(product_id)
    REFERENCES product(product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

---

## Entity 규칙

Entity명:

Order

패키지:

com.study.EasyOrder.entity

필드:

- orderId : Long
- product : Product
- orderQuantity : Integer
- totalPrice : Integer
- orderDate : LocalDateTime

---

## 관계 규칙

Order : Product

ManyToOne

하나의 상품은 여러 주문 가능

예:

콜라(product_id=1)

→ 주문1
→ 주문2
→ 주문3

JPA:

@ManyToOne(fetch = FetchType.LAZY)

@JoinColumn(name="product_id")

사용

---
---

## 상품 정보 저장 규칙

orders 테이블에는 상품명(product_name)을 저장하지 않는다.

저장되는 값:

- product_id
- order_quantity
- total_price

상품명은 조회 시 Product 엔티티에서 가져온다.

예:

order.getProduct().getName()

이유:

상품 이름 변경 시

기존 주문 데이터도
변경된 이름으로 조회되어야 한다.

예:

주문 생성 당시:

상품명 = 콜라

이후 상품 수정:

콜라 → 제로콜라

주문 조회 결과:

제로콜라

반드시 현재 Product 정보를 참조한다.

---

## 연관관계 구현 규칙

Order 엔티티는 Product 객체 참조를 사용한다.

예:

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="product_id")
private Product product;
```

주의:

Long productId 필드 직접 생성 금지

잘못된 예:

```java
private Long productId;
```

이유:

JPA 연관관계를 사용하지 않으면
매번 Product를 별도 조회해야 한다.

객체 관계를 사용하여:

order.getProduct().getName()

처럼 접근 가능해야 한다.

---

## 주문 조회 요구사항

주문 조회 시 반드시 아래 정보 포함:

- orderId
- productId
- productName
- orderQuantity
- totalPrice
- orderDate

productName 생성 규칙:

```java
order.getProduct().getName()
```

주의:

주문 테이블에 저장된 값 사용 금지

현재 Product 엔티티 값 사용

---

## 상품 삭제 정책

주문 이력이 존재하는 상품은 삭제 제한

권장:

ON DELETE RESTRICT

이유:

주문 데이터 무결성 유지
---

## 계산 규칙

totalPrice는 직접 입력받지 않는다.

자동 계산:

totalPrice =
product.price * orderQuantity

예:

상품 가격: 2000
주문 수량: 3

총액:

6000

---

## 재고 규칙

주문 생성 시:

product.stock >= orderQuantity

검사

성공:

재고 차감

stock =
stock - orderQuantity

실패:

예외 발생

예:

throw new IllegalArgumentException(
"재고가 부족합니다."
)

---

## Repository

OrderRepository

extends JpaRepository<Order,Long>

패키지:

com.study.EasyOrder.repository

---

## DTO

OrderRequest

필드:

- productId
- orderQuantity

주의:

totalPrice 입력 금지

자동 계산

---

OrderResponse

필드:

- orderId
- productId
- productName
- orderQuantity
- totalPrice
- orderDate

---

## Service

필수 메서드

create()
findAll()
findById()
delete()

수정(update)은 제공하지 않는다.

주문 수정 대신:

삭제 후 재주문

---

## Controller

URL

/orders

API

POST /orders
GET /orders
GET /orders/{id}
DELETE /orders/{id}

@RestController 사용

---

## 비즈니스 규칙

주문 생성 시:

1. Product 조회
2. 재고 확인
3. 재고 차감
4. totalPrice 계산
5. 주문 저장

---

## 주의사항

PROJECT_CONTEXT.md 규칙 준수

기존 설정 변경 금지

최소 수정 원칙

실행 가능한 코드만 작성

Entity 직접 반환 금지

DTO 사용

---

## AI 작업 규칙

코드 생성 전 반드시 출력:

1. 생성 파일 목록
2. 수정 파일 목록
3. 변경 이유

승인 후 생성