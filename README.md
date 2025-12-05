🧩 v1 핵심 기능 상세
1️⃣ User (회원)
✔ 주요 기능

회원가입 / 회원 정보 수정

세션 기반 로그인 / 로그아웃

비밀번호 암호화(BCrypt) + 검증/변경

soft delete (UserStatus)

@PrePersist / @PreUpdate 통한 시간 자동화

SessionUserDTO로 민감정보 최소화

✔ 설계 포인트

도메인 내부에서 BCrypt 직접 생성 ❌ → Service에서 인코더 주입 후 전달

getUserFindById()로 중복 제거 + 일관된 예외 처리

인증 실패 시 GlobalAdvice에서 공통 예외 응답 처리

2️⃣ Item & Sool (상품)
✔ 기능

Item 추상화 + Sool 구현

soft delete(ItemStatus)

재고 관리 메서드

increaseStock()

decreaseStock()

✔ 설계 포인트

향후 Goods 등 상품 확장 고려

주문 연동 시 자동 재고 차감

3️⃣ Order & OrderSool (주문)
✔ 주요 기능

주문 생성(create)

주문 취소(cancel)

주문 시 재고 자동 감소 → 취소 시 복구

OrderSool 자동 매핑 (Order ↔ Item 중간 테이블 역할)

주문 단일 조회 / 전체 조회 API

✔ 설계 포인트

Order 내부 메서드 중심으로 로직을 한곳에 모아 응집도 강화

상태 기반(OrderStatus)로 흐름 관리

4️⃣ Delivery (배송)
✔ 기능

주문 생성 시 Delivery 자동 생성

DeliveryStatus = READY

Order ↔ Delivery 1:1

✔ Address

Embeddable로 중복 코드 제거

User, Delivery에서 재사용

🔥 5️⃣ N+1 문제 해결 (조회 성능 최적화)
✔ 구현 방식

주문 전체 조회 시
Order ↔ OrderSool ↔ Item 연관 관계에서 발생하는 N+1 문제를
fetch join으로 해결

일괄 로딩 전략으로 쿼리 최소화

대규모 데이터에도 대응 가능한 구조

✔ 효과

불필요한 반복 쿼리 제거

조회 속도 개선

성능 병목 지점 해소

🔒 6️⃣ 세션 기반 인증 (Session Authentication)
✔ 구현 기능

HttpSession 기반 로그인 처리

로그인 사용자만 주문 가능

SessionUserDTO로 세션 저장 최소화

로그인 여부 체크를 인터셉터에서 공통 처리

🚫 7️⃣ 글로벌 인터셉터 (Interceptor)
✔ 목적

컨트롤러 단에서 매번 인증 검증 X

인터셉터에서 인증 여부 확인 후 차단

✔ 구현 특징

인증이 필요한 URL 패턴만 인터셉터 적용

UnAuthorized 접근 시 공통 예외 발생

인증/인가 로직 분리를 통해 유지보수성 향상

⚠️ 8️⃣ 전역 예외 처리 (GlobalAdvice)
✔ 구성

@RestControllerAdvice

UserException / OrderException 등 도메인 예외 처리

HttpStatus 포함한 통일된 응답 구조

모든 API는 Result 형태로 응답

✔ 예시 응답
{
  "code": "USER_EX",
  "message": "비밀번호가 맞지 않습니다"
}

🔄 ERD (v1)
User 1 --- N Order 1 --- N OrderSool N --- 1 Item
Order 1 --- 1 Delivery
User --- Address (embedded)
Delivery --- Address (embedded)


(원하면 ERD 이미지로 만들어줄게)

🧪 테스트

도메인 단위 테스트 작성

재고 및 주문 로직 중심 검증

v2에서 통합 테스트 강화

📦 실행 방법
./gradlew build
java -jar build/libs/k-soolmate-0.0.1-SNAPSHOT.jar

🧭 v1 → v2 → v3 로드맵
🔹 v2 (기능 확장)

필수

Swagger 문서화

Admin 권한 분리(Role 기반 인가)

조회 성능 보강(fetch join 추가)

응답 통일화 구조 강화

선택

찜하기 기능

게시판/댓글

이미지 업로드

상품 카테고리

🔹 v3 (배포/보안)

JWT 인증/인가 적용 (Access / Refresh)

AWS EC2 + RDS 배포

CI/CD (GitHub Actions)

Redis 캐싱

모니터링

🙋‍♂️ 작성자

김종규
GitHub: https://github.com/whdrb335
