🚀 K-SoolMate — 한국 전통주 주문 플랫폼

세션 기반 인증 · 전통주 상품 관리 · 주문/배송 관리 · JPA 기반 도메인 설계
Spring Boot 실무 스타일로 구현한 전통주 주문 플랫폼.

📌 목차

프로젝트 소개

기능 요약

v1 핵심 기능 상세

ERD 구조

기술 스택

실행 방법

버전 로드맵

작성자

프로젝트 소개

K-SoolMate는 한국 전통주를 소개하고 주문할 수 있는
e-commerce 형태의 백엔드 시스템입니다.

도메인 중심 설계

세션 기반 인증

JPA 연관관계 최적화

N+1 문제 해결

Soft Delete 적용

기능 요약
👤 User

회원가입 / 수정 / Soft Delete

로그인 / 로그아웃 (HttpSession)

BCrypt 비밀번호 암호화 및 변경

SessionUserDTO로 최소 정보만 세션에 저장

🍶 Item & Sool

Item 추상 클래스 + Sool 구현체

재고 증가/감소

soft delete (ItemStatus)

🛒 Order

주문 생성 / 취소

주문 시 재고 자동 차감

OrderSool로 N:M 관계 관리

단일/전체 주문 조회 API

🚚 Delivery

주문 시 자동 생성

상태 READY

Address Embeddable 재사용

⚙ 공통 기능

글로벌 예외 처리(GlobalAdvice)

Result API 응답 통일화

인터셉터 기반 로그인 체크

Fetch Join으로 N+1 성능 문제 해결

v1 핵심 기능 상세
1️⃣ User 도메인

회원 생성, 수정, 삭제(soft delete)

비밀번호 암호화/검증

로그인 인증 실패 시 공통 예외 처리

엔티티는 비즈니스 로직만 담당하도록 설계
(BCrypt는 Service에서 주입 → Entity 메서드에 전달)

2️⃣ Item & Sool 도메인

공통 속성: 가격, 재고, 상태

Sool 엔티티로 전통주 구현

재고 증가/감소 메서드 내 도메인 규칙 관리

3️⃣ Order 도메인

Order.createOrder()로 주문 생성

Order.cancel()로 취소 및 재고 복구

OrderSool 엔티티로 다대다 매핑 정규화

4️⃣ Delivery

주문 시 자동 생성

Address를 Embeddable로 재사용

5️⃣ Fetch Join 기반 조회 최적화

주문 리스트 조회 시
Order ↔ OrderSool ↔ Item 관계에서 발생하는 N+1 제거

6️⃣ 세션 인증

로그인 시 SessionUserDTO 저장

인터셉터에서 로그인 여부 체크

인증 실패 시 공통 메시지 반환

7️⃣ 글로벌 예외 처리

통일된 구조로 예외 응답:

{
  "code": "USER_EX",
  "message": "비밀번호가 맞지 않습니다"
}

ERD 구조

(원하면 ERD 이미지 제작해줄게)

User 1 --- N Order
Order 1 --- N OrderSool
OrderSool N --- 1 Item
Order 1 --- 1 Delivery

User --- Address (embedded)
Delivery --- Address (embedded)

기술 스택
구분	사용 기술
Language	Java 17
Framework	Spring Boot 3.x
ORM	Spring Data JPA
DB	H2 (개발), MySQL 호환
Build	Gradle
Others	Lombok, Validation, Spring MVC
실행 방법
./gradlew build
java -jar build/libs/k-soolmate-0.0.1-SNAPSHOT.jar

버전 로드맵 (v1 → v2 → v3)
🔹 v2 (기능 확장)

필수

Swagger 문서 자동화

Admin 권한 분리(Role 기반 인가)

조회 성능 강화(fetch join 추가)

공통 API Response 구조 재정비

선택

찜하기 기능

게시판/댓글

파일 업로드

상품 카테고리

🔹 v3 (배포 버전)

JWT 인증/인가 (Access/Refresh)

AWS EC2 + RDS 배포

GitHub Actions CI/CD

Redis 캐싱

서버 모니터링

작성자

김종규 (whdrb335)
GitHub: https://github.com/whdrb335
