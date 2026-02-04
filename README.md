# 🍶 K-SoolMate

한국 전통주를 소개하고 주문할 수 있는 e-commerce 백엔드 시스템

**개발 기간:** 2025.06 ~ 2026.01 (8개월)

- ✅ v1 (2025.06~10): 핵심 도메인 구현 및 N+1 문제 해결
- ✅ v2 (2025.11~2026.01): JWT, QueryDSL, Swagger, 테스트 80%, **성능 검증**
- 🚧 v3 (예정): Redis 캐싱, 비동기 처리, AWS 재배포

---

## 📌 목차

- [프로젝트 소개](#프로젝트-소개)
- [주요 성과](#주요-성과)
- [성능 테스트 및 병목 개선](#성능-테스트-및-병목-개선)
- [기술 스택](#기술-스택)
- [시스템 아키텍처](#시스템-아키텍처)
- [ERD 구조](#erd-구조)
- [v1 핵심 기능](#v1-핵심-기능)
- [v2 완성 기능](#v2-완성-기능)
- [문제 해결 경험](#문제-해결-경험)
- [v3 향후 계획](#v3-향후-계획)
- [실행 방법](#실행-방법)
- [API 명세](#api-명세)
- [테스트](#테스트)

---

## 📖 프로젝트 소개

K-SoolMate는 한국 전통주를 조회하고 주문할 수 있는 백엔드 기반 e-commerce 시스템입니다.

### 개발 기간
- v1 (2025.06~10): MVP 완성 - 5개월
- v2 (2025.11~2026.01): 실무 기능 강화 - 3개월
- **총 개발 기간: 8개월**

### 개발 인원
- 1인 프로젝트 (백엔드 중심 설계 및 구현)

### 버전별 목표

#### ✅ v1 완성 - 핵심 도메인 구현 및 주요 문제 해결
- 올바른 도메인 모델링 (DDD 기반)
- 주문/배송 핵심 비즈니스 로직 구현
- Soft Delete 기반 안정적인 데이터 관리
- 전역 예외 처리 구조 도입
- N+1 문제 해결로 조회 성능 최적화
- AWS EC2 + RDS 배포 및 도메인 연결 완료

#### ✅ v2 완성 - 실무 수준으로 확장
- JWT 기반 인증/인가 구현 완료
- Swagger API 문서화 완료
- QueryDSL 동적 쿼리 도입 완료
- 테스트 커버리지 80% 달성
- **k6 부하 테스트로 성능 병목 검증 및 개선**

#### 🚧 v3 계획 - 대용량 트래픽 대응 및 재배포
- Redis 캐싱 도입
- 비동기 처리 (CompletableFuture)
- Docker 컨테이너화
- AWS 재배포 또는 대체 플랫폼 배포

---

## 🎯 주요 성과

### v1 성과 (2025.06~10)

#### 성능 최적화
- ✅ **N+1 문제 해결**: 주문 목록 조회 쿼리 수 100회 → 1회 (100배 개선)
- ✅ **Fetch Join 적용**: 주문 상세 조회 응답 시간 500ms → 50ms (10배 개선)
- ✅ **Batch Fetch Size 설정**: 연관 엔티티 조회 최적화

#### 안정성 향상
- ✅ **Soft Delete 구현**: 데이터 무결성 보장
- ✅ **전역 예외 처리**: 일관된 에러 응답 구조
- ✅ **도메인 주도 설계**: 재고 관리 로직 도메인 내부 캡슐화

#### 코드 품질
- ✅ **테스트 코드 작성**: 핵심 비즈니스 로직 단위 테스트
- ✅ **계층 분리**: Controller → Service → Repository → Entity 명확한 역할 분담

#### 배포 경험
- ✅ **AWS EC2 + RDS 환경 구축 및 배포 완료**
- ✅ **도메인 연결 및 HTTP 서비스 운영**
- ✅ **인프라 구성 경험** (보안 그룹, RDS 설정, 네트워크 구성 등)

### v2 성과 (2025.11~2026.01)

#### 인증/인가 시스템
- ✅ **JWT 기반 인증**: Access/Refresh Token 구현 완료
- ✅ **권한 분리**: ROLE_ADMIN / ROLE_USER 구분
- ✅ **보안 강화**: BCrypt 암호화, Stateless 세션

#### 개발 생산성 향상
- ✅ **Swagger 문서화**: API 자동 문서 생성 및 테스트 환경 구축
- ✅ **QueryDSL 도입**: 동적 쿼리, 검색/정렬/페이징 기능 구현
- ✅ **테스트 커버리지 80%**: 단위/통합 테스트로 코드 안정성 확보

#### 성능 검증 및 개선
- ✅ **k6 부하 테스트**: 동시 사용자 30명 기준 성능 병목 측정
- ✅ **p95 응답 시간**: 4.31s → 66.52ms (약 98% 개선)
- ✅ **처리량 향상**: RPS 7.6 → 29.0 (약 3.8배 증가)

---

## 📊 성능 테스트 및 병목 개선

### 테스트 개요
- **도구**: k6 (오픈소스 부하 테스트 도구)
- **환경**: Local 환경, 동시 사용자 30명, 20초 지속 요청
- **대상 API**: 주문 전체 조회 API (`GET /api/admin/order`)
- **테스트 데이터**: 1,000건 이상의 주문 데이터

### 테스트 시나리오 설계

```javascript
// k6 부하 테스트 스크립트
export let options = {
  vus: 30,          // 동시 사용자 30명
  duration: '20s',  // 20초 동안 지속
};

export default function () {
  http.get('http://localhost:8081/api/test/order/before');
  sleep(1);
}
```

### 측정 결과 비교

#### 🔴 BEFORE (N+1 발생)
```
주요 지표:
- avg response time: 2.09s
- p95 response time: 4.31s
- max response time: 10.17s
- RPS (requests/sec): 7.6

문제점:
- Order 조회 → Delivery N회 조회 → OrderSool N회 조회 → Item N회 조회
- 1 + N + N 쿼리 패턴으로 인한 DB 커넥션 폭증
- 동시 요청 증가 시 응답 시간 기하급수적 증가
```

#### 🟢 AFTER (Fetch Join 적용)
```
주요 지표:
- avg response time: 33.32ms
- p95 response time: 66.52ms
- max response time: 198.28ms
- RPS (requests/sec): 29.0

개선 사항:
- JPQL Fetch Join으로 연관 엔티티를 한 번에 로딩
- 쿼리 1건으로 통합하여 DB 부하 대폭 감소
- 안정적인 응답 시간 유지
```

### 개선 효과 요약

| 지표 | BEFORE | AFTER | 개선율 |
|------|--------|-------|--------|
| **평균 응답 시간** | 2.09s | 33.32ms | **98.4% ↓** |
| **p95 응답 시간** | 4.31s | 66.52ms | **98.5% ↓** |
| **최대 응답 시간** | 10.17s | 198.28ms | **98.0% ↓** |
| **처리량 (RPS)** | 7.6 | 29.0 | **281% ↑** |

### 검증 과정 설계

#### 1️⃣ 테스트 환경 분리
```java
// 운영 API와 완전히 분리된 테스트 전용 컨트롤러
@RestController
@RequestMapping("/api/test")
public class OrderLoadTestController {
    
    @GetMapping("/order/before")  // N+1 발생 버전
    public List<OrderDTO> before() {
        return orderService.getAllOrdersWithoutFetch();
    }
    
    @GetMapping("/order/after")   // Fetch Join 적용 버전
    public List<OrderDTO> after() {
        return orderService.getAllOrdersWithFetch();
    }
}
```

#### 2️⃣ 테스트 데이터 생성
```java
// 실서비스 도메인 로직(재고 차감)과 분리된 테스트용 생성 메서드
@Service
public class TestOrderService {
    
    @Transactional
    public void bulkCreateOrders(int count) {
        for (int i = 0; i < count; i++) {
            // 재고 차감 없이 주문 데이터만 생성
            OrderSool orderSool = 
                OrderSool.createOrderSoolForTest(sool, price, 1);
            Order order = Order.createOrder(user, delivery, orderSool);
            orderRepository.save(order);
        }
    }
}
```

#### 3️⃣ BEFORE/AFTER 비교 측정
- **BEFORE**: `findAllWithoutFetch()` 사용 → N+1 발생
- **AFTER**: `findAllWithItems()` 사용 → Fetch Join 적용
- **동일 조건**: 같은 데이터, 같은 부하, 같은 환경

### 기술적 개선 사항

#### Repository 계층
```java
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    // 🔴 BEFORE: N+1 발생
    @Query("select o from Order o")
    List<Order> findAllWithoutFetch();
    
    // 🟢 AFTER: Fetch Join 적용
    @Query("""
        select distinct o from Order o
        join fetch o.delivery
        join fetch o.orderSools os
        join fetch os.item
    """)
    List<Order> findAllWithItems();
}
```

#### Service 계층
```java
@Service
@Transactional(readOnly = true)
public class OrderService {
    
    // BEFORE 버전
    public List<OrderDTO> getAllOrdersWithoutFetch() {
        List<Order> orders = orderRepository.findAllWithoutFetch();
        return orders.stream()
            .map(OrderDTO::new)  // 여기서 N+1 발생
            .toList();
    }
    
    // AFTER 버전
    public List<OrderDTO> getAllOrdersWithFetch() {
        List<Order> orders = orderRepository.findAllWithItems();
        return orders.stream()
            .map(OrderDTO::new)  // 이미 로딩된 데이터 사용
            .toList();
    }
}
```

### 주요 학습 포인트

#### 1. 성능 측정의 중요성
- 추측이 아닌 **실제 측정**을 통한 병목 확인
- p95, p99 같은 **퍼센타일 지표**의 중요성 이해
- 평균보다 **최악의 경우(max)**를 고려한 설계

#### 2. 테스트 환경 설계
- 운영 코드와 **완전히 분리된 테스트 API** 구성
- 실서비스 도메인 로직(재고 차감)을 **침범하지 않는 설계**
- 동일 조건 비교를 위한 **재현 가능한 테스트 시나리오**

#### 3. 실무형 문제 해결 프로세스
- **문제 인식** → N+1 쿼리 패턴 식별
- **재현 및 측정** → k6 부하 테스트로 병목 수치화
- **개선** → JPQL Fetch Join 적용
- **검증** → 동일 조건에서 재측정 및 비교

---

## 🛠 기술 스택

### Backend
![Java](https://img.shields.io/badge/Java-17-007396?style=flat&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=flat&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=flat&logo=springsecurity&logoColor=white)

### Database & ORM
![H2](https://img.shields.io/badge/H2-Database-blue?style=flat)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-Hibernate-59666C?style=flat&logo=hibernate&logoColor=white)
![QueryDSL](https://img.shields.io/badge/QueryDSL-0078D4?style=flat)

### Build & Tools
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=flat&logo=gradle&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-BC4521?style=flat)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=flat&logo=swagger&logoColor=black)

### Performance Testing
![k6](https://img.shields.io/badge/k6-7D64FF?style=flat&logo=k6&logoColor=white)

### Infrastructure
![AWS EC2](https://img.shields.io/badge/AWS%20EC2-FF9900?style=flat&logo=amazonec2&logoColor=white)
![AWS RDS](https://img.shields.io/badge/AWS%20RDS-527FFF?style=flat&logo=amazonrds&logoColor=white)

### Test
![JUnit5](https://img.shields.io/badge/JUnit5-25A162?style=flat&logo=junit5&logoColor=white)
![AssertJ](https://img.shields.io/badge/AssertJ-25A162?style=flat)

### 선택 이유
- **Spring Boot 3.x**: 최신 기술 스택 활용, Auto Configuration으로 개발 생산성 향상
- **JPA + QueryDSL**: 객체 지향적 설계 유지하면서 복잡한 쿼리 동적 생성
- **k6**: 경량화된 부하 테스트 도구, 스크립트 기반 시나리오 작성 용이
- **H2 Database**: 빠른 개발 환경 구축, 실제 운영 시 MySQL 전환 예정
- **AWS EC2 + RDS**: 클라우드 인프라 구축 및 배포 경험
- **Lombok**: Boilerplate 코드 최소화로 비즈니스 로직에 집중
- **Swagger**: API 문서 자동 생성 및 테스트 편의성 향상

---

## 🏗 시스템 아키텍처
```
┌─────────────────┐
│   Client        │
│  (Postman/Web)  │
└────────┬────────┘
         │ HTTP Request
         ▼
┌─────────────────────────────┐
│   Spring Boot Application   │
│                             │
│  ┌──────────────────────┐  │
│  │  Controller Layer    │  │  ← REST API Endpoint
│  │  - UserController    │  │
│  │  - OrderController   │  │
│  │  - SoolController    │  │
│  └──────────┬───────────┘  │
│             │               │
│  ┌──────────▼───────────┐  │
│  │   Service Layer      │  │  ← Business Logic
│  │  - UserService       │  │
│  │  - OrderService      │  │
│  │  - SoolService       │  │
│  └──────────┬───────────┘  │
│             │               │
│  ┌──────────▼───────────┐  │
│  │  Repository Layer    │  │  ← Data Access (QueryDSL)
│  │  - UserRepository    │  │
│  │  - OrderRepository   │  │
│  │  - SoolRepository    │  │
│  └──────────┬───────────┘  │
│             │               │
│  ┌──────────▼───────────┐  │
│  │   Domain Layer       │  │  ← Entity & Business Rule
│  │  - User              │  │
│  │  - Order             │  │
│  │  - Sool              │  │
│  │  - Delivery          │  │
│  └──────────────────────┘  │
└─────────────┬───────────────┘
              │ JPA/Hibernate
              ▼
┌─────────────────────────────┐
│      H2 Database            │
│   (Dev: In-Memory)          │
│   (Prod: MySQL 예정)        │
└─────────────────────────────┘
```

### 주요 특징
- **계층형 아키텍처**: 각 계층의 역할과 책임 명확히 분리
- **도메인 주도 설계**: 비즈니스 로직을 도메인 엔티티 내부에 배치
- **Dependency Injection**: Spring IoC 컨테이너를 통한 느슨한 결합
- **Global Exception Handling**: @RestControllerAdvice로 일관된 예외 처리
- **JWT 인증**: Filter 기반 Stateless 인증 구조

---

## 🗂 ERD 구조
```
┌──────────────┐         ┌──────────────┐         ┌──────────────┐
│     User     │         │    Order     │         │   Delivery   │
├──────────────┤         ├──────────────┤         ├──────────────┤
│ id (PK)      │1       *│ id (PK)      │1       1│ id (PK)      │
│ loginId      │─────────│ user_id (FK) │─────────│ order_id(FK) │
│ loginPw      │         │ delivery_id  │         │ address      │
│ name         │         │ orderDate    │         │ status       │
│ role         │         │ orderStatus  │         └──────────────┘
│ status       │         └──────────────┘
│ phoneNumber  │                │
│ email        │                │ 1
│ address      │                │
│ createdAt    │                │ *
│ updatedAt    │         ┌──────────────┐
└──────────────┘         │  OrderSool   │
                         ├──────────────┤
                         │ id (PK)      │
                         │ order_id(FK) │* 
                         │ sool_id (FK) │───────┐
                         │ orderPrice   │       │ 1
                         │ count        │       │
                         └──────────────┘       │
                                                │
                         ┌──────────────┐       │
                         │     Item     │       │
                         ├──────────────┤       │
                         │ id (PK)      │◄──────┘
                         │ name         │
                         │ description  │
                         │ itemType     │
                         │ itemStatus   │
                         │ createdAt    │
                         │ updatedAt    │
                         └──────┬───────┘
                                │
                                │ (상속: SINGLE_TABLE)
                                ▼
                         ┌──────────────┐
                         │     Sool     │
                         ├──────────────┤
                         │ alcoholPercent│
                         │ price        │
                         │ stockQuantity│
                         │ origin       │
                         │ brand        │
                         └──────────────┘
```

### 주요 관계
- **User : Order = 1 : N** (한 사용자는 여러 주문 가능)
- **Order : Delivery = 1 : 1** (주문 1건당 배송 1건)
- **Order : OrderSool = 1 : N** (주문 1건에 여러 상품 가능)
- **Sool : OrderSool = 1 : N** (상품 1개는 여러 주문에 포함 가능)
- **Item ← Sool = 상속 관계** (SINGLE_TABLE 전략)

---

## 🟩 v1 핵심 기능

### 1) 👤 User 도메인

#### 기능
- 회원가입 / 로그인 (BCrypt 암호화)
- 내 정보 조회 / 수정
- Soft Delete (UserStatus: ACTIVE, DELETE)
- 비밀번호 변경

#### 주요 코드
```java
@Entity
@Table(name = "\"user\"")
public class User {
    @Id @GeneratedValue
    private Long id;
    
    private String loginId;
    private String loginPw;  // BCrypt 암호화
    
    @Enumerated(EnumType.STRING)
    private UserStatus status;  // ACTIVE, DELETE
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    
    // 비즈니스 로직
    public void changePasswd(String oldPasswd, String newPasswd, 
                            BCryptPasswordEncoder encoder) {
        if (!encoder.matches(oldPasswd, this.loginPw)) {
            throw new NotMatchPasswd("비밀번호가 맞지 않습니다");
        }
        this.loginPw = encoder.encode(newPasswd);
    }
}
```

---

### 2) 🍶 Sool(전통주) 도메인

#### 기능
- 상품 등록 / 조회 / 수정 / 삭제 (Soft Delete)
- 재고 증가 / 감소 로직
- Item 추상화 기반 확장 가능 구조

#### 주요 코드
```java
@Entity
@DiscriminatorValue("SOOL")
public class Sool extends Item {
    private double alcoholPercent;
    private int price;
    private int stockQuantity;
    
    // 재고 감소 (비즈니스 로직)
    public void removeStock(int count) {
        int restStock = this.stockQuantity - count;
        if (restStock < 0) {
            throw new NotEnoughStockException("재고가 부족합니다");
        }
        this.stockQuantity = restStock;
    }
    
    // 재고 증가
    public void addStock(int count) {
        this.stockQuantity += count;
    }
}
```

---

### 3) 📦 Order & Delivery

#### 기능
- 주문 생성 (재고 자동 감소)
- 주문 취소 (재고 자동 복구)
- 주문 총 금액 계산
- 배송 상태 자동 설정 (READY)

#### 주요 코드
```java
@Entity
@Table(name = "orders")
public class Order {
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Delivery delivery;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderSool> orderSools = new ArrayList<>();
    
    // 주문 생성 (정적 팩토리 메서드)
    public static Order createOrder(User user, Delivery delivery, 
                                   OrderSool... orderSools) {
        Order order = new Order();
        order.user = user;
        order.delivery = delivery;
        order.orderStatus = OrderStatus.ORDER;
        
        for (OrderSool orderSool : orderSools) {
            order.addOrderSool(orderSool);
        }
        return order;
    }
    
    // 주문 취소 (비즈니스 로직)
    public void cancelOrder() {
        if (this.orderStatus == OrderStatus.CANCEL) {
            throw new AlreadyCancelOrder("이미 취소된 주문입니다");
        }
        
        // 재고 복구
        for (OrderSool orderSool : orderSools) {
            orderSool.cancel();  // 내부적으로 sool.addStock() 호출
        }
        
        this.orderStatus = OrderStatus.CANCEL;
    }
}
```

---

### 4) ⚠️ 전역 예외 처리

#### 기능
- 커스텀 예외 계층 구조 (UserException, OrderException, ItemException)
- @RestControllerAdvice로 공통 처리
- 일관된 JSON 에러 응답

#### 주요 코드
```java
@RestControllerAdvice
@Slf4j
public class GlobalAdvice {
    
    @ExceptionHandler(ItemException.class)
    public ResponseEntity<ErrorResult> handleItemException(ItemException e) {
        log.error("[ItemException] = {}", e.getMessage());
        return new ResponseEntity<>(
            new ErrorResult("ITEM_EX", e.getMessage()), 
            e.getStatus()
        );
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResult> handleValidationException(
            MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .findFirst()
                .orElse("@Validated 검증 실패");
        
        return new ResponseEntity<>(
            new ErrorResult("VALIDATION_ERROR", errorMessage),
            HttpStatus.BAD_REQUEST
        );
    }
}
```

---

## 🟦 v2 완성 기능

### 1) 🔐 JWT 인증/인가 (완성)

#### 구현 내용
- ✅ Session → JWT 구조로 전환 완료
- ✅ Access Token (30분) / Refresh Token (7일) 구현
- ✅ ROLE_ADMIN / ROLE_USER 권한 분리
- ✅ JWT Filter를 통한 토큰 검증
- ✅ Stateless 세션 관리

#### 주요 기능
- 로그인 시 JWT 토큰 발급
- 요청 시 토큰 검증 및 사용자 인증
- Refresh Token으로 Access Token 재발급
- 권한별 API 접근 제어

---

### 2) 📘 Swagger 문서화 (완성)

#### 구현 내용
- ✅ @Operation, @Schema 기반 API 문서 자동 생성
- ✅ Try it out 기능으로 테스트 편의성 향상
- ✅ 협업 및 프론트엔드 연동 효율 극대화
- ✅ JWT 인증 테스트 지원

#### 접속 방법
- **Swagger UI**: `http://localhost:8081/swagger-ui/index.html`
- **API Docs**: `http://localhost:8081/v3/api-docs`

---

### 3) 🧭 QueryDSL 도입 (완성)

#### 구현 내용
- ✅ 동적 쿼리 지원 (검색 조건 필터링)
- ✅ 정렬 / 페이징 기능 강화
- ✅ Fetch Join 최적화
- ✅ 타입 안전한 쿼리 작성

#### 주요 기능
- 상품 검색 (이름, 가격, 도수 등)
- 주문 목록 정렬 (날짜, 금액 등)
- 페이징 처리 (Page, Slice)

---

### 4) 🧪 테스트 코드 보강 (완성)

#### 구현 내용
- ✅ 단위 테스트 커버리지 80% 달성
- ✅ MockMvc 기반 API 통합 테스트
- ✅ 인증/인가 테스트 추가
- ✅ Repository QueryDSL 테스트

#### 테스트 커버리지
- UserService: 90%
- OrderService: 85%
- SoolService: 90%
- Repository: 80%

---

## 🧠 문제 해결 경험

### 1) 🔥 주문 조회 시 N+1 문제 해결 및 성능 검증 (v1~v2)

#### 문제 상황
주문 목록 조회 API 호출 시 쿼리가 100회 이상 발생

- 주문 조회: 1회
- 각 주문의 OrderSool 조회: N회
- 각 OrderSool의 Sool 조회: N회
- **→ 1 + N + N = 약 100회**

#### 원인 분석
```java
// 기존 코드 (N+1 발생)
public List<OrderDTO> getAllOrders() {
    List<Order> orders = orderRepository.findAll();  // 1회
    return orders.stream()
        .map(order -> {
            // 여기서 order.getOrderSools() 호출 시 N회
            // orderSool.getSool() 호출 시 또 N회
            return new OrderDTO(order);
        })
        .toList();
}
```

#### 해결 방법
```java
// OrderRepository에 Fetch Join 적용
@Query("SELECT DISTINCT o FROM Order o " +
       "JOIN FETCH o.orderSools os " +
       "JOIN FETCH os.sool")
List<Order> findAllWithItems();

// Service 수정
public List<OrderDTO> getAllOrders() {
    List<Order> orders = orderRepository.findAllWithItems();  // 1회!
    return orders.stream()
        .map(OrderDTO::new)
        .toList();
}
```

#### 성능 검증 프로세스 (v2 추가)

**1. 테스트 환경 구성**
- 테스트 전용 API (`/api/test`) 분리
- 1,000건 이상의 더미 주문 데이터 생성
- 실서비스 도메인 로직과 분리된 테스트용 메서드 설계

**2. 부하 테스트 수행**
- k6로 동시 사용자 30명, 20초 지속 요청
- BEFORE/AFTER 동일 조건에서 측정

**3. 측정 결과**
- **BEFORE**: p95 4.31s, RPS 7.6
- **AFTER**: p95 66.52ms, RPS 29.0
- **개선율**: p95 기준 약 98% 감소, 처리량 3.8배 향상

#### 배운 점
- JPA Lazy Loading의 한계와 Fetch Join의 필요성 이해
- **추측이 아닌 실제 측정을 통한 성능 검증의 중요성**
- **p95, p99 같은 퍼센타일 지표로 최악의 경우를 고려한 설계**
- 테스트 환경 분리를 통한 실서비스 영향 최소화

---

### 2) 🔥 Soft Delete 도입 후 조회 혼란 해결 (v1)

#### 문제 상황
- 사용자가 삭제한 상품이 여전히 목록에 표시됨
- 관리자용 API와 사용자용 API가 구분되지 않음

#### 원인 분석
```java
// 기존 코드 (문제)
public List<SoolDTO> getAllSools() {
    // 삭제된 상품(status=DELETE)도 함께 조회됨
    return soolRepository.findAll().stream()
        .map(SoolDTO::new)
        .toList();
}
```

#### 해결 방법
```java
// Repository에 조건 추가
public interface SoolRepository extends JpaRepository<Sool, Long> {
    
    // 활성 상품만 조회 (사용자용)
    @Query("SELECT s FROM Sool s WHERE s.itemStatus = 'ACTIVE'")
    List<Sool> findAllActive();
    
    // 전체 조회 (관리자용)
    List<Sool> findAll();
}

// Service 분리
public List<SoolDTO> getAllSools() {
    return soolRepository.findAllActive().stream()  // 활성만
        .map(SoolDTO::new)
        .toList();
}

public List<SoolDTO> getAllSoolsForAdmin() {
    return soolRepository.findAll().stream()  // 전체
        .map(SoolDTO::new)
        .toList();
}
```

#### 결과
- ✅ 사용자는 활성 상품만 조회
- ✅ 관리자는 삭제된 상품 포함 전체 조회 가능
- ✅ 비즈니스 요구사항 명확히 반영

#### 배운 점
- Soft Delete 사용 시 조회 조건의 중요성
- 사용자 역할별 데이터 접근 권한 분리 필요성

---

### 3) 🔥 주문 취소 시 재고 복구 누락 버그 (v1)

#### 문제 상황
- 주문 취소 시 재고가 복구되지 않음
- **→ 실제 재고와 DB 재고 불일치 발생**

#### 원인 분석
```java
// 기존 코드 (버그)
public void cancelOrder(Long orderId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow();
    
    order.setOrderStatus(OrderStatus.CANCEL);  // 상태만 변경
    // 재고 복구 로직 누락! ❌
}
```

#### 해결 방법
```java
// Order 엔티티 내부에 비즈니스 로직 캡슐화
public class Order {
    public void cancelOrder() {
        if (this.orderStatus == OrderStatus.CANCEL) {
            throw new AlreadyCancelOrder("이미 취소된 주문입니다");
        }
        
        // 재고 복구
        for (OrderSool orderSool : orderSools) {
            orderSool.cancel();  // ← 여기서 재고 증가
        }
        
        this.orderStatus = OrderStatus.CANCEL;
    }
}

// OrderSool
public class OrderSool {
    public void cancel() {
        sool.addStock(count);  // 재고 복구!
    }
}

// Service는 단순히 호출만
public void cancelOrder(Long orderId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow();
    order.cancelOrder();  // 도메인 로직에 위임
}
```

#### 결과
- ✅ 재고 일관성 보장
- ✅ 비즈니스 로직이 도메인 내부에 캡슐화
- ✅ Service 계층 간결해짐

#### 배운 점
- 도메인 주도 설계(DDD)의 중요성
- 비즈니스 로직은 엔티티 내부에 위치해야 응집도가 높아짐
- Service는 트랜잭션 관리와 도메인 호출에만 집중

---

### 4) 🔥 JWT 토큰 검증 로직 중복 제거 (v2)

#### 문제 상황
- 모든 Controller에서 JWT 토큰 검증 로직 중복
- 코드 가독성 저하 및 유지보수 어려움

#### 해결 방법
```java
// JwtAuthenticationFilter 구현
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) {
        String token = resolveToken(request);
        
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        
        filterChain.doFilter(request, response);
    }
}

// SecurityConfig 등록
@Configuration
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
            .addFilterBefore(jwtAuthenticationFilter, 
                           UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/user/login", "/api/user/signup").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            );
        return http.build();
    }
}
```

#### 결과
- ✅ 중복 코드 제거
- ✅ Filter 기반 인증 처리
- ✅ Controller 코드 간결해짐

#### 배운 점
- Spring Security Filter 체인 활용
- 횡단 관심사 분리의 중요성

---

## 🚧 v3 향후 계획

### ☁️ 배포 이력 및 계획

#### v1 배포 경험 (완료)
- ✅ AWS EC2 + RDS 환경 구축 및 배포 완료
- ✅ 도메인 연결 및 HTTP 서비스 운영
- ✅ 인프라 구성 경험 (보안 그룹, RDS 설정, 네트워크 구성 등)
- ⚠️ AWS 프리티어 과금 이슈로 현재 중단 (로컬 환경에서 기능 확장 중)
- 📝 클라우드 인프라 구축 및 배포 프로세스 학습 완료

#### v3 재배포 계획 (예정)
- Docker 컨테이너화로 비용 효율화
- GitHub Actions CI/CD 자동 배포 구축
- Railway/Render 등 대체 플랫폼 검토
- HTTPS 적용 (Let's Encrypt)

### 🚀 성능 최적화
- Redis 캐싱 도입 (인기 상품 조회)
- 비동기 처리 (CompletableFuture)
- 부하 테스트 확장 (JMeter)
- DB Connection Pool 최적화

---

## 🚀 실행 방법

### 요구사항
- Java 17 이상
- Gradle 8.5 이상

### 1. 프로젝트 클론
```bash
git clone https://github.com/whdrb3353/k-soolmate.git
cd k-soolmate
```

### 2. 빌드 및 실행
```bash
# Gradle 빌드
./gradlew build

# 애플리케이션 실행
./gradlew bootRun

# 또는 JAR 파일 실행
java -jar build/libs/k-soolmate-0.0.1-SNAPSHOT.jar
```

### 3. 접속 확인
- **애플리케이션**: `http://localhost:8081`
- **H2 Console**: `http://localhost:8081/h2-console`
- **Swagger UI**: `http://localhost:8081/swagger-ui/index.html`

### 4. H2 Database 설정
- **JDBC URL**: `jdbc:h2:tcp://localhost/~/jpashop`
- **Username**: `sa`
- **Password**: (공백)

---

## 📡 API 명세

### User API

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/api/user/signup` | 회원가입 | ❌ |
| POST | `/api/user/login` | 로그인 (JWT 발급) | ❌ |
| POST | `/api/user/refresh` | Access Token 재발급 | ✅ |
| GET | `/api/user/mypage` | 내 정보 조회 | ✅ |
| PUT | `/api/user/mypage` | 내 정보 수정 | ✅ |
| DELETE | `/api/user/mypage` | 회원 탈퇴 | ✅ |

### Sool API

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/sool` | 전체 상품 조회 (검색/정렬) | ❌ |
| GET | `/api/sool/{id}` | 상품 상세 조회 | ❌ |
| POST | `/api/sool` | 상품 등록 | ✅ (ADMIN) |
| PUT | `/api/sool/{id}` | 상품 수정 | ✅ (ADMIN) |
| DELETE | `/api/sool/{id}` | 상품 삭제 | ✅ (ADMIN) |

### Order API

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/api/order` | 주문 생성 | ✅ |
| GET | `/api/order` | 내 주문 목록 (페이징) | ✅ |
| GET | `/api/order/{id}` | 주문 상세 조회 | ✅ |
| DELETE | `/api/order/{id}` | 주문 취소 | ✅ |

---

## 🧪 테스트

### 테스트 실행
```bash
# 전체 테스트
./gradlew test

# 특정 테스트만 실행
./gradlew test --tests UserServiceTest
```

### 테스트 커버리지
- UserService: 90%
- OrderService: 85%
- SoolService: 90%
- Repository: 80%
- **전체: 80%**

### 주요 테스트 케이스
```java
@Test
@DisplayName("주문 취소 시 재고 복구 확인")
void cancelOrder_shouldRestoreStock() {
    // given
    Sool sool = createSool(100);  // 재고 100개
    Order order = createOrder(sool, 10);  // 10개 주문
    
    // when
    orderService.cancelOrder(order.getId());
    
    // then
    assertThat(sool.getStockQuantity()).isEqualTo(100);  // 재고 복구 확인
}

@Test
@DisplayName("JWT 토큰 검증 성공")
void validateToken_success() {
    // given
    String token = jwtTokenProvider.createAccessToken(userId, role);
    
    // when
    boolean isValid = jwtTokenProvider.validateToken(token);
    
    // then
    assertThat(isValid).isTrue();
}
```

---

## 📚 참고 자료

- [Spring Boot 공식 문서](https://spring.io/projects/spring-boot)
- [JPA 프로그래밍 - 김영한](https://www.inflearn.com/course/ORM-JPA-Basic)
- [실전! 스프링 부트와 JPA 활용 - 김영한](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-JPA-%ED%99%9C%EC%9A%A9-1)
- [QueryDSL 공식 문서](http://querydsl.com/)
- [JWT 소개](https://jwt.io/introduction)
- [k6 Documentation](https://k6.io/docs/)

---

## 👨‍💻 작성자

**김종규**

- Email: whdrb3353@naver.com
- GitHub: [https://github.com/whdrb3353](https://github.com/whdrb3353)

---

## 📄 License

This project is licensed under the MIT License

---

**마지막 업데이트**: 2026.02.04
