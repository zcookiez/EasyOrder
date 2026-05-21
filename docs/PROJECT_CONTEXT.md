# PROJECT_CONTEXT.md

## 1. 프로젝트 기본 정보

- 프로젝트명: EasyOrder
- 그룹 ID: com.study
- 버전: 0.0.1-SNAPSHOT
- Java: 17
- Spring Boot: 3.4.2

---

## 2. 기술 스택

- Spring Boot
- Spring Data JPA
- Gradle
- MariaDB
- Lombok
- JUnit5

---

## 3. 프로젝트 구조

Source:

- src/main/java/com/study/EasyOrder

현재 패키지:

- controller
- service
- repository
- entity
- dto

---

## 4. DB 설정

URL:

jdbc:mariadb://127.0.0.1:3306/easy_order

username:

order_user

JPA:

ddl-auto=update

---

## 5. 현재 도메인

사용 문서:

- DOMAIN_PRODUCT.md
- DOMAIN_ORDER.md