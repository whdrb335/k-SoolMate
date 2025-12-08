# 🍶 K-SoolMate  
**한국 전통주를 소개하고 주문할 수 있는 e-commerce 백엔드 시스템**

전통주 상품 관리 · 주문/배송 기능 · JPA 기반 도메인 설계  
v1은 **핵심 기능 구현에 집중**했고, v2에서 인증/권한·문서화·조회 최적화를 확장할 예정입니다.

---

## 📌 목차
- [프로젝트 소개](#-프로젝트-소개)
- [v1 기능 요약](#-v1-기능-요약)
- [v1 핵심 기능 상세](#-v1-핵심-기능-상세)
- [v2에서 확장 예정 기능](#-v2에서-확장-예정-기능)
- [ERD 구조](#-erd-구조)
- [기술 스택](#-기술-스택)
- [실행 방법](#-실행-방법)
- [버전 로드맵](#-버전-로드맵)
- [작성자](#-작성자)

---

## 📖 프로젝트 소개
**K-SoolMate**는 한국 전통주를 소개하고 주문할 수 있는  
백엔드 e-commerce 시스템입니다.

v1에서는 **도메인 모델링, 주문 로직, 기본 CRUD, Soft Delete** 중심으로 개발하여  
핵심 기능의 동작과 구조적 기반을 다졌습니다.

v2에서는 실제 서비스 수준의 기술 요소(JWT, Swagger, QueryDSL, 테스트)를 추가하여  
포트폴리오 품질을 강화할 예정입니다.

---

## 🟩 v1 기능 요약
- 사용자 관리 (회원가입/조회/수정/삭제-soft delete)
- 전통주 상품 등록, 조회, 삭제
- 주문 생성 및 취소
- 배송 상태 초기화(READY)
- 예외 처리 구조 설계(RestControllerAdvice)
- **권한은 v1에서는 기본 구조만 유지 → 실제 ADMIN/CLIENT 분리는 v2에서 진행**

---

## 🔍 v1 핵심 기능 상세

### 1) **User 도메인**
- Soft Delete 적용 (ENUM 상태값 기반)
- JPA 생명주기 콜백으로 생성/수정일 자동 관리
- 기본 회원 기능 CRUD 제공

### 2) **Sool(전통주) 도메인**
- Item 추상화 구조 설계 후 Sool으로 확장
- 상품 삭제 시 ENUM 상태 변경 방식 적용
- 엔티티 중심 설계 (도메인 주도 구조의 초석)

### 3) **Order / Delivery**
- 주문 생성 시 Delivery READY 상태 자동 설정
- 주문 취소 로직 포함
- 추후 v2에서 권한 기반 "본인 주문만 조회" 기능 강화 예정

### 4) **예외 처리**
- UserException 등 커스텀 예외 타입 정의
- RestControllerAdvice로 도메인 중심 예외 처리 구조 적용

---

## 🚧 v2에서 확장 예정 기능
> v2는 **실무 기술 역량 강화**가 목표입니다.

### 🔐 1) JWT 기반 인증/인가 구현
- 기존 세션 → JWT 구조로 전환  
- AccessToken / RefreshToken 발급  
- 토큰 기반 권한 체크(RBAC)
- **ADMIN / CLIENT 역할 분리 및 접근 제한 구현 (v2에서 진행)**

### 📘 2) Swagger(OpenAPI) 문서화
- @Operation으로 각 엔드포인트 문서화
- DTO 필드에 @Schema 적용
- 문서 UI 자동 생성

### 🧭 3) QueryDSL 도입
- 전통주 검색 필터링 제공
- 주문 조회 최적화(fetch join)
- N+1 문제 제거

### 🧪 4) 테스트 코드(JUnit + MockMvc)
- 서비스 단위 테스트
- 인증/인가 테스트
- 예외 케이스 검증

---

## 🗂 ERD 구조
(이미지 추가 예정)

---

## 🛠 기술 스택
- Java 17  
- Spring Boot 3.x  
- Spring Data JPA  
- Gradle  
- H2 / MySQL  
- Lombok  

---

## 🚀 실행 방법
```bash
git clone https://github.com/whdrb335/k-SoolMate.git
cd k-SoolMate
./gradlew bootRun
