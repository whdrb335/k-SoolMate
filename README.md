# 🍶 K-SoolMate  
한국 전통주를 소개하고 주문할 수 있는 e-commerce 백엔드 시스템

전통주 상품 관리 · 주문/배송 기능 · JPA 기반 도메인 설계  
v1에서는 **핵심 도메인 구현 및 주요 문제 해결(MVP 완성)** 에 집중했고,  
v2에서는 **인증/권한 · 문서화 · 조회 최적화 · 테스트 보강** 을 통해 실서비스 수준으로 확장할 예정입니다.

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
- 주문/배송 핵심 비즈니스 로직 구현
- Soft Delete 기반 안정적인 데이터 관리
- 전역 예외 처리 구조 도입
- N+1 해결(fetch join)로 조회 성능 최적화

v2에서는 아래 기능들을 강화합니다:
- **JWT 기반 인증/인가**
- **Swagger 문서화 자동화**
- **QueryDSL 검색/정렬**
- **테스트 코드 강화 (단위 + 통합)**

---

# 🟩 v1 기능 요약
- 사용자 관리 (회원가입/조회/수정/Soft Delete)
- 전통주 상품 등록/조회/수정/삭제 (Soft Delete)
- 주문 생성 및 취소 기능  
- 주문 시 재고 감소 / 취소 시 재고 복구
- 배송 상태 READY 자동 설정
- 전역 예외 처리 적용 (RestControllerAdvice)
- N+1 문제 해결(fetch join)
- 기본 인증(Session 기반)

---

# 🔍 v1 핵심 기능 상세

## 1) 👤 User 도메인
- Soft Delete 적용 (UserStatus ENUM)
- 생성/수정일 자동 처리(@PrePersist / @PreUpdate)
- 사용자 정보 조회·수정 등 CRUD 지원

---

## 2) 🍶 Sool(전통주) 도메인
- Item 추상화 구조 기반 확장
- Soft Delete(ItemStatus ENUM)
- 도메인 중심 설계로 안정적인 상품 관리
- SINGLE_TABLE 전략 사용으로 확장 가능성 확보

---

## 3) 📦 Order & Delivery
- 주문 생성 시 재고 감소
- 주문 취소 시 자동 재고 복구
- Delivery READY 상태 자동 설정
- OrderSool로 M:N 중간 테이블 구성
- 주문 총 금액 계산 기능 제공

---

## 4) ⚠️ 전역 예외 처리
- UserException, OrderException 등 커스텀 예외 정의
- RestControllerAdvice로 공통 처리
- Result<T> 형태로 응답 통일(JSON 포맷 일관성)

---

# 🧠 문제 해결 경험(v1)

v1을 개발하면서 실제로 마주한 문제들과 해결 과정을 정리했습니다.  
→ **면접과 포트폴리오에서 기술적 깊이를 드러낼 수 있는 핵심 섹션**

---

## 1) 🔥 주문 조회 시 발생한 N+1 문제 해결
### ● 문제
- Order 조회 시 OrderSool, Sool 엔티티 접근에서 Lazy 로딩으로 쿼리가 연쇄적으로 발생  
- 주문 1건 조회에 수십 개 쿼리 발생

### ● 해결
- fetch join 적용해 필요한 연관관계를 한 번에 로딩  
- DTO 변환 구조 개선으로 순환 참조 제거

### ● 결과
- 쿼리 수 **10배 이상 감소**  
- 목록 조회 성능 대폭 개선  

---

## 2) 🔥 Soft Delete 도입으로 조회 혼란 발생
### ● 문제
- User/Item의 삭제 후에도 데이터가 조회됨  
- API/UI 혼란 발생

### ● 해결
- 기본 Repository 조회 조건에 `status = ACTIVE` 추가  
- Admin API에서는 삭제 데이터 포함 조회 가능하도록 분리

### ● 결과
- 사용자/관리자 역할별 조회 기준 명확해짐  
- 비즈니스 정합성 개선  

---

## 3) 🔥 주문 취소 시 재고 복구 누락 버그
### ● 문제
- 주문 취소 시 재고 복구 누락  
- UI와 실제 재고 불일치 문제 발생

### ● 해결
- Order.cancel() 내부에서  
  - 주문 상태 변경  
  - OrderSool 삭제 시 재고 복구 로직 실행  
  로직을 **도메인 중심으로 재구조화**

### ● 결과
- 재고 일관성 확보  
- 유지보수성 향상  

---

## 4) 🔥 Controller 인증 체크 중복 문제
### ● 문제
- 모든 컨트롤러에서 session 검증 코드 중복  
- 유지보수 어려움 증가

### ● 해결
- AuthInterceptor 도입  
- preHandle()에서 인증 로직을 공통 처리  
- SessionUserDTO로 세션의 민감정보 최소화

### ● 결과
- 컨트롤러 코드 간결해짐  
- v2 JWT 전환을 위한 인프라 기반 완성  

---

# 🚧 v2 확장 예정 기능

## 🔐 JWT 인증/인가
- Session → JWT 구조로 재설계  
- Access / Refresh Token  
- ROLE_ADMIN / ROLE_CLIENT 접근 분리

---

## 📘 Swagger 문서화
- @Operation 기반 API 문서 자동 생성  
- DTO에 @Schema 적용  
- 협업 및 테스트 효율 극대화

---

## 🧭 QueryDSL 도입
- 검색 조건 필터링 기능 강화  
- 동적 쿼리 지원  
- fetch join 기반 조회 속도 최적화  

---

## 🧪 테스트 코드 보강
- 도메인 단위 테스트  
- MockMvc 사용한 API 통합 테스트  
- 인증/인가 테스트 추가

---

# 🗂 ERD 구조

아래는 K-SoolMate의 주요 도메인 관계를 보여주는 ERD입니다.

> ⚠ 깃허브에 이미지를 올린 뒤 아래 이미지 링크에 파일 URL만 붙여 넣으면 완성됩니다.

```markdown
![K-SoolMate ERD]<img width="656" height="559" alt="image" src="https://github.com/user-attachments/assets/220498ba-7d25-49af-9068-6a15ee340c98" />

