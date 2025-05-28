# 🍶 K-SoolMate - 전통주 소개 및 주문 플랫폼

---

## 🏷️ 버전 히스토리

| 버전 | 목표 | 주요 구현 내용 |
|------|------|----------------|
| `v1` | 핵심 도메인 설계 중심 | Item/Sool, User, Delivery, Address 설계 및 재고 로직, soft delete, 시간 자동화 |
| `v2` | 기능 확장 및 API 구현 | 주문(Order), 주문 상세(OrderSool), 예외 처리 통합, 등록/조회 API 개발 |
| `v3` | 안정화 및 배포 | 인증/인가 강화, Swagger 문서화, 테스트, CI/CD, AWS 배포 |

> ✅ 현재 브랜치: `v1`  
> 작업 목표: 핵심 도메인 및 로직 설계 완료 (2025.05.28 기준)

---

## 📌 프로젝트 소개

K-SoolMate는 전통주를 소개하고 회원이 로그인하여 상품을 주문할 수 있는 백엔드 API 플랫폼입니다.  
도메인 주도 설계(DDD) 철학을 바탕으로 확장성과 유지보수성을 고려해 설계하였습니다.

---

## 🛠 기술 스택

| 항목 | 내용 |
|------|------|
| Language | Java 17 |
| Framework | Spring Boot 3.x |
| ORM | Spring Data JPA |
| DB | H2 (dev) / MySQL (prod 예정) |
| Build Tool | Gradle |
| API 문서화 | Swagger / SpringDoc (예정) |

---

## 📁 폴더 구조

```
src
└── main
    ├── domain
    │   ├── item         # 추상 Item + Sool, Goods
    │   ├── order        # Order, OrderSool
    │   ├── user         # 회원 도메인 (soft delete)
    │   ├── delivery     # 배송 도메인 (일대일 매핑)
    │   └── address      # Embeddable 주소
    ├── controller
    ├── service
    ├── repository
```

---

## 🧩 핵심 기능 (v1 기준)

- [x] `Item` 추상화 및 `Sool` 구현  
- [x] `User` 도메인  
  - soft delete 적용 + 생성/수정 시간 자동화  
  - 회원 가입, 정보 수정, 비밀번호 검증/변경 기능 구현  
  - `BCryptPasswordEncoder`를 도메인에 직접 new 하지 않고 Service에서 주입 후 전달  
  - 유저 조회는 `getUserFindById()`로 캡슐화하여 중복 제거 및 예외 처리 일관성 유지  
  - DTO(`UserDTO`)를 활용한 안전한 응답 처리  
  - `@Valid`로 유효성 검증 적용 (`@Embeddable` Address 포함)  
  - 전역 예외 처리 구조 완성 → `GlobalAdvice`에서 `UserException` 통합 처리  

    ```json
    {
      "code": "USER_EX",
      "message": "비밀번호가 맞지 않습니다"
    }
    ```
- [x] `Delivery` 도메인 + `Address` 임베딩  
- [x] `increaseStock`, `decreaseStock` 재고 관리  
- [ ] `Order` 도메인 설계 및 주문 취소 로직 (`v2 목표`)  
- [ ] `@RestControllerAdvice` 기반 예외 처리 통합 (`v2 목표`)  
- [ ] Swagger API 문서화 및 운영환경 전환 (`v3 목표`)



---

## 🛡 인증/인가 계획

- v2: Session 기반 로그인 처리 및 사용자 인증 구현
- v3:
  - JWT 인증 도입 (Access / Refresh 토큰 구조)
  - ADMIN / USER 권한 분리
  - 로그인 사용자만 주문 가능
  - 관리자만 상품 등록 가능
  - 페이지 및 API 접근 제한

---

## ⚙️ 도메인 설계 특징

- 도메인 간 명확한 연관관계 및 책임 분리
- soft delete: 상태값 기반으로 삭제 처리 (`ItemStatus`, `UserStatus`)
- `@PrePersist`, `@PreUpdate`로 시간 필드 자동화
- 연관관계 편의 메서드 + 생성/수정 로직 도메인 내부에 위치

---

## 🔄 ERD (작성 예정)

- User ↔ Order ↔ OrderSool ↔ Item(Sool)
- Delivery ↔ Order
- Address → 내장 타입

---

## 🧪 테스트

- 단위 테스트: 도메인 로직, 서비스 기능 중심
- 통합 테스트: Postman 기반 API 테스트 준비 중

---

## 📦 실행 방법

```bash
./gradlew build
java -jar build/libs/k-soolmate-0.0.1-SNAPSHOT.jar
```

---

## 🙋‍♂️ 작성자

| 이름 | GitHub |
|------|--------|
| 김종규 | [github.com/whdrb335](https://github.com/whdrb335) |

---

## 🧭 향후 계획

### 🔹 v2
- 주문(Order) 도메인 생성 및 주문 생성/취소 메서드 설계
- 중간 테이블 성격의 `OrderSool` 구현 및 재고 감소 반영
- 예외 처리 통합 (`@RestControllerAdvice` + 커스텀 예외)
- Spring Security 인증/인가 강화
- 게시판/댓글, 찜 하기 추가 계획

### 🔹 v3
- Swagger 문서 자동화 및 운영환경 설정
- 테스트 강화 (단위 + 통합 테스트)
- AWS EC2 + RDS 배포, CI/CD 파이프라인 구축
