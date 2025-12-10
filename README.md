# 🍶 K-SoolMate  
**한국 전통주를 소개하고 주문할 수 있는 e-commerce 백엔드 시스템**

전통주 상품 관리 · 주문/배송 기능 · JPA 기반 도메인 설계  
v1에서는 **핵심 도메인 구현 및 주요 문제 해결(MVP 완성)** 에 집중했고,  
v2에서는 **인증/권한 · 문서화 · 조회 최적화 · 테스트 보강**을 통해 실서비스 수준으로 확장할 예정입니다.

---

## 📌 목차
- [프로젝트 소개](#-프로젝트-소개)
- [v1 기능 요약](#-v1-기능-요약)
- [v1 핵심 기능 상세](#-v1-핵심-기능-상세)
- [문제 해결 경험(v1)](#-문제-해결-경험v1)
- [v2 확장 예정 기능](#-v2-확장-예정-기능)
- [ERD 구조](#-erd-구조)
- [기술 스택](#-기술-스택)
- [실행 방법](#-실행-방법)
- [버전 로드맵](#-버전-로드맵)
- [작성자](#-작성자)

---

# 📖 프로젝트 소개
**K-SoolMate**는 다양한 한국 전통주를 조회하고 주문할 수 있는  
백엔드 기반 e-commerce 시스템입니다.

v1에서는 다음에 집중했습니다:
- 올바른 **도메인 모델링**
- 주문/배송 핵심 비즈니스 로직
- Soft Delete 구조
- 전역 예외 처리
- N+1 해결(fetch join)

v2에서는:
- **인증/권한 강화(JWT)**
- **Swagger 문서화**
- **QueryDSL 검색**
- **테스트 코드**  
등을 적용해 실무 수준의 완성도를 목표로 합니다.

---

# 🟩 v1 기능 요약
- 사용자 관리 (회원가입/조회/수정/Soft Delete)
- 전통주 상품 등록/조회/수정/삭제(Soft Delete)
- 주문 생성 및 취소
- 배송 상태 초기화(READY)
- 전역 예외 처리 적용
- N+1 문제 해결(fetch join)
- 기본 인증(Session 기반)

---

# 🔍 v1 핵심 기능 상세

## 1) 👤 User 도메인
- Soft Delete 적용 (UserStatus ENUM)
- 생성/수정일 자동 처리(JPA 콜백)
- 사용자 정보 CRUD

---

## 2) 🍶 Sool(전통주) 도메인
- Item 추상화 기반 Sool 구현
- Soft Delete(ItemStatus)
- 전통주 정보 관리 CRUD

---

## 3) 📦 Order & Delivery
- 주문 생성 시 재고 감소
- 주문 취소 시 자동 재고 복구
- Delivery READY 상태 자동 설정
- OrderSool로 M:N 중간 구조 설계

---

## 4) ⚠️ 전역 예외 처리
- UserException, OrderException 등 커스텀 예외
- RestControllerAdvice로 통합 처리
- Result<T> 응답 통일

---

# 🧠 문제 해결 경험(v1)

v1을 개발하면서 실제로 마주한 문제들과 해결 과정을 정리했습니다.  
→ **면접에서 기술적인 깊이를 드러낼 수 있는 핵심 섹션**

---

## 1) 🔥 주문 조회 시 발생한 N+1 문제 해결
### ● 문제
- Order 조회 시 OrderSool, Sool 엔티티 접근에서 다수의 Lazy 조회 발생  
- 주문 1건 조회에 수십 개의 쿼리가 실행됨

### ● 해결
- JPQL + fetch join으로 필요한 연관관계를 한 번에 조회  
- 불필요한 순환 참조를 피하기 위해 DTO 변환 구조 개선

### ● 결과
- 쿼리 실행 수 **10배 이상 감소**  
- 목록 조회 시 성능 체감되는 수준으로 개선  

---

## 2) 🔥 Soft Delete 도입으로 조회 결과 혼란 발생
### ● 문제
- User/Item을 Soft Delete로 처리하자  
  삭제된 데이터가 조회되면서 UI/API 혼선 발생

### ● 해결
- Repository 조회 조건에 `status = ACTIVE` 명시  
- Admin 조회는 삭제된 데이터도 포함하도록 API 분리

### ● 결과
- 운영자(Admin)와 일반 사용자(Client)의 조회 기준이 명확해짐  
- 도메인 정합성 유지  

---

## 3) 🔥 주문 취소 시 재고 복구 누락 버그
### ● 문제
- 주문 취소 시 재고가 원래대로 복구되지 않음  
- UI와 실제 재고가 불일치하는 문제 발생

### ● 해결
- Order.cancel() 도메인 메서드 안에  
  - 주문 상태 변경  
  - OrderSool 별 재고 복구  
  트랜잭션 내에서 함께 처리하도록 리팩토링

### ● 결과
- 재고 데이터의 일관성 보장  
- 도메인 중심 설계에 가까워짐  

---

## 4) 🔥 인증 체크 로직이 Controller에 중복되는 문제
### ● 문제
- 모든 API마다 session 검증 코드가 반복됨  
- 유지보수 어려움

### ● 해결
- AuthInterceptor를 만들어 `preHandle()`에서 인증 한 번만 체크  
- SessionUserDTO로 세션 내 민감정보 제거

### ● 결과
- 컨트롤러 코드가 훨씬 깔끔해짐  
- 인증/인가 구조 확장(v2 JWT) 기반 완성  

---

# 🚧 v2 확장 예정 기능
> v2 목표: **실서비스 수준 기능 완성**

---

## 🔐 JWT 인증/인가
- 세션 기반 → JWT 기반 구조로 재설계  
- Access / Refresh  
- ROLE_ADMIN / ROLE_CLIENT 접근 분리

---

## 📘 Swagger 문서화
- @Operation 기반 상세 문서  
- DTO에 @Schema 적용  
- 테스트 및 협업 수월해짐  

---

## 🧭 QueryDSL 도입
- 검색 조건 필터링  
- 동적 조회  
- fetch join 최적화  

---

## 🧪 테스트 코드 보강
- Service 단위 테스트  
- MockMvc 기반 API 테스트  
- 인증/인가 테스트

---

# 🗂 ERD 구조
(ERD 이미지 추가 예정)

---

# 🛠 기술 스택
- Java 17  
- Spring Boot 3.x  
- Spring Data JPA  
- H2 / MySQL  
- Gradle  
- Lombok  

---

# 🚀 실행 방법
```bash
git clone https://github.com/whdrb335/k-SoolMate.git
cd k-SoolMate
./gradlew bootRun
