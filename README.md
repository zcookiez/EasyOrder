# EasyOrder
Spring Boot와 Thymeleaf를 활용한 심플하고 직관적인 상품 관리 및 주문 시스템입니다.

## 🚀 프로젝트 개요

EasyOrder는 상품의 등록, 수정, 판매 상태 관리(Soft Delete) 및 고객의 상품 구매 기능을 제공하는 웹 애플리케이션입니다. 
## 🛠 기술 스택

- **Backend**: Java 17, Spring Boot 3.4.2
- **Database**: MariaDB, Spring Data JPA
- **Frontend**: Thymeleaf, Vanilla CSS, JavaScript
- **Documentation**: Swagger (SpringDoc OpenAPI 2.8.4)
- **Build Tool**: Gradle

## ✨ 주요 기능

### 1. 상품 관리 
- **상품 등록/수정**: 이미지 업로드 기능을 포함한 상품 정보 관리
- **상품 목록 페이징**: 대량의 상품 정보를 8개 단위로 나누어 효율적으로 조회
- **검색**: 상품명을 기반으로 한 엔터 키 검색 지원
- **판매 상태 제어**: 다중 선택을 통한 상품 판매 중지 및 재개 기능 (Soft Delete)
- **상태 유지**: 페이징 및 검색 필터 상태를 유지하며 상세/수정 페이지 이동 가능

### 2. 상품 갤러리 
- **상품 목록**: 판매 중인 상품을 카드 형태로 시각화
- **상세 보기**: Tumblbug 스타일의 세련된 상품 상세 레이아웃 및 스크롤 고정 이미지 적용
- **실시간 구매**: 상세 페이지에서 수량 선택 및 즉시 주문 처리 (재고 차감 로직 포함)

### 3. 주문 관리
- **주문 목록 페이징**: 전체 주문 내역을 10개 단위로 나누어 한눈에 확인
- **상품명 검색**: 특정 상품이 포함된 주문 건만 필터링 가능

### 4. API & 개발 도구
- **REST API**: 모든 핵심 기능을 JSON 기반 API로 제공 (`/api/**`)
- **Swagger UI**: 브라우저에서 API 명세 확인 및 즉시 테스트 가능

## 🛠 API 테스트 가이드 (Swagger)

EasyOrder는 개발 및 테스트 편의를 위해 Swagger UI를 제공합니다. 모든 API는 `/api` 접두사를 사용하며 JSON 형식으로 통신합니다.

### 1. Swagger 접속 주소
- **URL**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### 2. 주요 테스트 항목

#### 📦 상품 API (Product)
- `GET /api/products`: 상품 목록 조회 
- `GET /api/products/{id}`: 특정 상품의 상세 정보 확인
- `POST /api/products`: 신규 상품 등록
- `PUT /api/products/{id}`: 상품 정보 수정
- `POST /api/products/delete`: 여러 상품을 선택하여 판매 중지
- `POST /api/products/restore`: 판매 중지된 상품들을 다시 판매 중으로 복구

#### 📝 주문 API (Order)
- `GET /api/orders`: 전체 주문 내역 확인
- `POST /api/orders`: 상품 구매 처리 

### 3. 테스트 방법
1. Swagger UI 접속 후 원하는 API 항목을 클릭합니다.
2. 우측 상단의 **'Try it out'** 버튼을 누릅니다.
3. 필요한 파라미터나 JSON 데이터를 입력한 후 **'Execute'**를 클릭합니다.
4. 하단의 **'Responses'**에서 서버의 실제 응답(JSON)과 상태 코드를 확인합니다.

## 📁 프로젝트 구조

```
src/main/java/com/study/EasyOrder/
├── config/             # Swagger 및 웹 설정
├── controller/         
│   ├── HomeController.java
│   ├── ProductController.java  # 화면 이동용
│   ├── OrderController.java    # 화면 이동용
│   └── api/                    # REST API 전용 (JSON)
├── dto/                # 데이터 전송 객체 (Request/Response)
├── entity/             # JPA 엔티티 (Product, Order)
├── repository/         # JPA 레포지토리
└── service/            # 비즈니스 로직 및 이미지 처리
```

## ⚙️ 시작하기

### 실행 환경
- Java 17 이상 설치 필요
- MariaDB 설치 및 DB 생성 (`easy_order`)

### DB 설정 (`application.properties`)
```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/easy_order
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
```

### 빌드 및 실행
```bash
./gradlew bootRun
```

## 🔗 주요 주소
- **메인 갤러리**: `http://localhost:8080/products/gallery`
- **상품 관리**: `http://localhost:8080/products/list`
- **주문 관리**: `http://localhost:8080/orders/list`
- **API 문서 (Swagger)**: `http://localhost:8080/swagger-ui/index.html`

---
© 2026 EasyOrder Project. All rights reserved.
