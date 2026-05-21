# AI_RULES.md

## 1. 기본 원칙

- PROJECT_CONTEXT.md를 최우선 참고
- DOMAIN 문서를 우선 참고
- 기존 프로젝트 구조 준수
- Spring Boot + JPA 유지
- 추측 기반 작업 금지
- 주석 임의 삭제 금지

---

## 2. 코드 작성 규칙

- 항상 빌드 가능한 코드 작성
- 최소 수정 원칙
- 과도한 리팩토링 금지
- 기존 설정 임의 변경 금지
- Entity 직접 반환 금지
- DTO 사용

---

## 3. 코드 생성 절차

코드 생성 전 반드시 아래 수행

### 생성 파일 목록 출력

예:

생성 예정:

- entity/Product.java
- repository/ProductRepository.java
- service/ProductService.java

---

### 수정 파일 목록 출력

예:

수정 예정:

- application.properties

---

### 변경 이유 출력

예:

- Product CRUD 구현
- DTO 추가
- Service 추가

---

### 승인 요청

반드시:

"진행할까요?"

승인 후 코드 생성

---

## 4. 기존 코드 우선

코드 생성 전:

현재 프로젝트 상태 확인

추측 금지

현재 파일 기준 작업

---

## 5. 금지사항

금지:

- 패키지 구조 변경
- 설정 변경
- 전체 리팩토링
- 사용하지 않는 코드 추가

요청 범위만 수정

---

## 6. 도메인 규칙

Product 관련 작업:

PRODUCT_DOMAIN.md 참고

Order 관련 작업:

DOMAIN_ORDER.md 참고