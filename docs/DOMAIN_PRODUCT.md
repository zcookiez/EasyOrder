# DOMAIN_PRODUCT.md

## 1. DB 스키마
```sql
CREATE TABLE product (
    product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price INT NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    description TEXT,
    image_url VARCHAR(255),
    delete_yn BOOLEAN NOT NULL DEFAULT false,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## 2. Entity 규칙
- **패키지**: `com.study.EasyOrder.entity.Product`
- **매핑**: `@Table(name="product")`, ID 전략은 `IDENTITY`
- **필드**: `productId`, `name`, `price`, `stock`, `description`, `imageUrl`, `deleteYn`, `createdAt`
- **자동설정**: `createdAt`은 생성 시 자동 할당, `deleteYn` 기본값은 `false`

## 3. 비즈니스 규칙
- **삭제 정책**: **Soft Delete** 사용. 실제 레코드를 삭제하지 않고 `deleteYn = true` 처리.
- **조회 정책**: 목록 및 단건 조회 시 `deleteYn = false`인 데이터만 노출.
- **입력 검증**: 
    - `name`: 필수, 빈 값 금지
    - `price`: 필수, 0 이상
    - `stock`: 필수, 0 이상

## 4. DTO 및 인터페이스 규칙
- **DTO 구분**: 
    - `ProductRequest`: 입력용 (ID, deleteYn, createdAt 제외)
    - `ProductResponse`: 출력용 (Entity 직접 반환 금지)
- **Repository**: `deleteYn` 필터를 포함한 커스텀 메서드 필요 (`findByDeleteYnFalse` 등)
- **URL**: `/products/**` (RESTful 기준 준수)
