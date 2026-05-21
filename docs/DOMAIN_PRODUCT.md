# PRODUCT_DOMAIN.md

## 목적

EasyOrder 상품 CRUD 도메인

상품 등록 / 조회 / 수정 / 삭제 기능 제공

주의:

삭제는 실제 삭제가 아닌 Soft Delete 사용

---

## DB 스키마

```
CREATE TABLE product (

    product_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '상품 고유 번호',
    name VARCHAR(100) NOT NULL COMMENT '상품명',
    price INT NOT NULL COMMENT '상품 가격',
    stock INT NOT NULL DEFAULT 0 COMMENT '재고 수량',
    description TEXT COMMENT '상품 상세 설명',
    image_url VARCHAR(255) COMMENT '상품 이미지 URL',
    delete_yn BOOLEAN NOT NULL DEFAULT false COMMENT '삭제 여부',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '생성일'

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4;
```

---

## Entity 규칙

Entity명:

Product

패키지:

com.study.EasyOrder.entity

필드:

- productId : Long
- name : String
- price : Integer
- stock : Integer
- imageUrl : String
- deleteYn : Boolean
- createdAt : LocalDateTime

규칙:

- @Entity 사용
- @Table(name="product")
- productId는 IDENTITY 전략
- createdAt은 @PrePersist 사용
- deleteYn 기본값 false

예:

```java
@Entity
@Table(name="product")
public class Product {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
       
    private Long productId;

    private String name;

    private Integer price;

    private Integer stock;

    private String imageUrl;

    private Boolean deleteYn;

    private LocalDateTime createdAt;

    @PrePersist
    public void created(){

        this.createdAt=
            LocalDateTime.now();

        if(this.deleteYn==null){

            this.deleteYn=false;

        }

    }

}
```

---

## 입력 규칙

상품 생성/수정 요청 시:

필수:

- name
- price
- stock

선택:

- imageUrl

입력 금지:

- productId
- deleteYn
- createdAt

올바른 요청:

```json
{
    "name":"콜라",
    "price":2000,
    "stock":10,
    "imageUrl":"https://example.com/cola.png"
}
```

잘못된 요청:

```json
{
    "productId":1,
    "deleteYn":true,
    "createdAt":"2030-01-01"
}
```

---

## 검증 규칙

상품명:

빈 값 금지

가격:

0 이상

재고:

0 이상

이미지 URL:

URL 형식 (권장)

권장:

```java
@NotBlank
private String name;

@Min(0)
private Integer price;

@Min(0)
private Integer stock;
```

---

## Repository

ProductRepository

extends JpaRepository<Product,Long>

패키지:

com.study.EasyOrder.repository

추가 메서드:

```java
List<Product> findByDeleteYnFalse();
Optional<Product> findByProductIdAndDeleteYnFalse( Long productId);
```

삭제된 상품 제외 조회

---

## DTO

ProductRequest

필드:

- name
- price
- stock
- description
- imageUrl

주의:

productId 제외

deleteYn 제외

createdAt 제외

---

ProductResponse

필드:

- productId
- name
- price
- stock
- description
- imageUrl
- createdAt

Entity 직접 반환 금지

반드시 DTO 사용

---

## Service

필수 메서드:

create()

findAll()

findById()

update()

delete()

---

삭제 규칙:

실제 삭제 금지

금지:

```java
productRepository.delete()
```

사용:

```java
product.setDeleteYn(true);

productRepository.save(product);
```

---

조회 규칙:

삭제된 상품 제외

예:

```java
findByDeleteYnFalse()
```

단건 조회:

삭제된 상품 조회 불가

예외:

```java
throw new IllegalArgumentException(
"삭제된 상품입니다."
);
```

---

## 주문 연관 규칙

Product는 Order를 직접 참조하지 않는다.

양방향 매핑 금지

금지:

```java
@OneToMany(
mappedBy="product"
)

private List<Order> orders;
```

사용:

Order → Product

이유:

현재 요구사항에
Product → Order 조회 없음

순환 참조 방지

복잡도 감소

---

## Controller

URL

/products

API:

POST /products

GET /products

GET /products/{id}

PUT /products/{id}

DELETE /products/{id}

@RestController 사용

---

## 비즈니스 규칙

상품 삭제 시:

실제 데이터 삭제 금지

deleteYn=true 처리

주문 이력이 있어도
상품 데이터 유지

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

코드 생성 전:

1. 생성 파일 목록

2. 수정 파일 목록

3. 변경 이유

먼저 출력

승인 후 생성
 생성
�� 출력

승인 후 생성
 생성
�� 출력

승인 후 생성
