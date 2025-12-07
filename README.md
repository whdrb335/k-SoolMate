🍶 K-SoolMate — 한국 전통주 주문 플랫폼

전통주 상품 관리 · 주문/배송 관리 · 회원 인증을
Spring Boot + JPA 실무 스타일로 구현한 전통주 주문 플랫폼입니다.
도메인 중심 설계(DDD Lite), 세션 인증, 재고 관리, soft delete, 성능 최적화까지 포함합니다.

📌 목차

프로젝트 소개

기능 요약

v1 핵심 기능 상세

ERD 구조

기술 스택

실행 방법

버전 로드맵

작성자

📝 프로젝트 소개

K-SoolMate는 한국 전통주 정보를 기반으로
상품 등록 → 주문 → 배송 → 관리의 전 과정을 처리하는
백엔드 중심 전통주 주문 플랫폼입니다.

본 프로젝트는 다음의 목표를 가집니다:

실무형 Spring Boot 아키텍처 학습

JPA 기반 도메인 모델링

인증/인가 처리 구조 깊이 이해

N+1 문제 해결 및 성능 최적화

⚙️ 기능 요약
👤 User (회원)

회원가입 / 회원 정보 수정

세션 기반 로그인 / 로그아웃

BCrypt 비밀번호 암호화 & 변경

soft delete(UserStatus)

JPA @PrePersist / @PreUpdate로 시간 자동화

민감정보를 제외한 SessionUserDTO 저장

🍶 Item & Sool (상품)

Item 추상화 + Sool 구체 타입

soft delete(ItemStatus)

재고 관리(increase / decrease)

서비스 계층에서 DTO 처리

🧾 Order & OrderSool (주문)

주문 생성 / 취소

주문 시 재고 차감

취소 시 재고 자동 복구

Order ↔ OrderSool 연결

전체 주문 조회 / 단건 조회

📦 Delivery (배송)

주문 생성 시 Delivery 자동 생성

READY 상태 초기화

Address는 @Embeddable로 재사용

🔐 세션 기반 인증

HttpSession 로그인

인터셉터(LoginCheckInterceptor)로 인증 검증

로그인 필요한 API 자동 보호

🚫 글로벌 예외 처리

@RestControllerAdvice

UserException, OrderException 등 통합 처리

Result 구조로 통일된 API 응답 제공

⚡ 성능 최적화 (N+1 해결)

fetch join으로 주문 전체 조회 성능 개선

불필요한 쿼리 제거

대량 조회 시 성능 안정화

🧩 v1 핵심 기능 상세
1️⃣ User

회원 도메인 내부에서 비밀번호 로직 단일 책임 보유

Service에서 인코더 주입 → User 객체에 전달

getUserFindById()로 중복 로직 제거

예외 처리 일관화

2️⃣ Item/Sool

도메인 확장을 고려한 추상 클래스 설계

soft delete로 안전한 삭제 처리

3️⃣ Order

주문 생성/취소 로직을 Order 엔티티 내부로 응집

비즈니스 규칙이 엔티티 내부에 집중됨 → 유지보수성↑

4️⃣ Fetch Join

Order 전체 조회 시

Order

OrderSool

Item
관계에서 발생하는 N+1 문제 완전 제거

🗂️ ERD 구조
User 1 --- N Order
Order 1 --- N OrderSool
OrderSool N --- 1 Item
Order 1 --- 1 Delivery
User --- Address (Embedded)
Delivery --- Address (Embedded)


원하면 ERD 이미지 버전도 만들어줄게!

🛠 기술 스택
분야	기술
Language	Java 17
Framework	Spring Boot 3.x
ORM	Spring Data JPA
Build	Gradle
DB	H2 Database
Test	JUnit5
ETC	Lombok, Spring Validation
▶️ 실행 방법
1) 빌드
./gradlew build

2) 실행
java -jar build/libs/k-soolmate-0.0.1-SNAPSHOT.jar

🗺 버전 로드맵
🔷 v2 (기능 확장)

Swagger API 문서화

Admin 권한 분리 (인가 강화)

Fetch join 보강

주문/상품 테스트 확장

공통 응답 구조 통일

🔷 v3 (실서비스 기반)

JWT 인증/인가

AWS EC2 + RDS 배포

Redis 캐싱 적용

GitHub Actions CI/CD

모니터링 도입

👤 작성자

김종규 (whdrb335)
GitHub: https://github.com/whdrb335
