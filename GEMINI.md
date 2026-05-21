# GEMINI.md

EasyOrder 프로젝트 작업 전 아래 문서를 순서대로 읽는다.

문서 위치:

docs/

읽기 우선순위:

1. PROJECT_CONTEXT.md
2. AI_RULES.md
3. DOMAIN_PRODUCT.md
4. DOMAIN_ORDER.md

작업 규칙:

- PROJECT_CONTEXT.md 최우선
- AI_RULES.md 작업 절차 준수
- Spring Boot + JPA 유지
- 기존 구조 변경 금지
- DTO 사용
- Entity 직접 반환 금지
- 최소 수정 원칙
- 추측 기반 작업 금지

코드 생성 전 반드시:

1. 생성 파일 목록
2. 수정 파일 목록
3. 변경 이유
4. 사용자 승인 요청

승인 후 코드 생성