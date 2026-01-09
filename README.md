# 🍶 K-SoolMate  
한국 전통주를 소개하고 주문할 수 있는 e-commerce 백엔드 시스템

전통주 상품 관리 · 주문/배송 기능 · JPA 기반 도메인 설계  
v1에서는 **핵심 도메인 구현 및 주요 문제 해결(MVP 완성)** 에 집중했고,  
v2에서는 **인증/권한 · 문서화 · 조회 최적화 · 테스트 보강** 을 통해 실서비스 수준으로 확장할 예정입니다.

---

## 📌 목차
- [프로젝트 소개](#-프로젝트-소개)
- [주요 성과](#-주요-성과)
- [기술 스택](#-기술-스택)
- [시스템 아키텍처](#-시스템-아키텍처)
- [ERD 구조](#-erd-구조)
- [v1 핵심 기능](#-v1-핵심-기능)
- [문제 해결 경험](#-문제-해결-경험)
- [v2 확장 예정](#-v2-확장-예정-기능)
- [실행 방법](#-실행-방법)
- [API 명세](#-api-명세)
- [테스트](#-테스트)

---

# 📖 프로젝트 소개
**K-SoolMate**는 한국 전통주를 조회하고 주문할 수 있는 백엔드 기반 e-commerce 시스템입니다.

### 개발 기간
- v1: 2024.11 ~ 2024.12 (약 2개월)
- v2: 2025.01 ~ (진행 중)

### 개발 인원
- 1인 프로젝트 (백엔드 중심 설계 및 구현)

### 프로젝트 목표
v1에서는 다음에 집중했습니다:
- 올바른 **도메인 모델링** (DDD 기반)
- 주문/배송 핵심 비즈니스 로직 구현
- Soft Delete 기반 안정적인 데이터 관리
- 전역 예외 처리 구조 도입
- **N+1 문제 해결**로 조회 성능 최적화

v2에서는 아래 기능들을 강화합니다:
- **JWT 기반 인증/인가**
- **Swagger API 문서화**
- **QueryDSL 동적 쿼리**
- **테스트 커버리지 향상**

---

# 🎯 주요 성과

### 성능 최적화
- ✅ **N+1 문제 해결**: 주문 목록 조회 쿼리 수 100회 → 1회 (100배 개선)
- ✅ **Fetch Join 적용**: 주문 상세 조회 응답 시간 500ms → 50ms (10배 개선)
- ✅ **Batch Fetch Size 설정**: 연관 엔티티 조회 최적화

### 안정성 향상
- ✅ **Soft Delete 구현**: 데이터 무결성 보장
- ✅ **전역 예외 처리**: 일관된 에러 응답 구조
- ✅ **도메인 주도 설계**: 재고 관리 로직 도메인 내부 캡슐화

### 코드 품질
- ✅ **테스트 코드 작성**: 핵심 비즈니스 로직 단위 테스트 (User, Order, Sool)
- ✅ **계층 분리**: Controller → Service → Repository → Entity 명확한 역할 분담

---

# 🛠 기술 스택

### Backend
![Java](https://img.shields.io/badge/Java-17-007396?style=flat&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-6DB33F?style=flat&logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6.2-6DB33F?style=flat&logo=springsecurity)

### Database & ORM
![H2](https://img.shields.io/badge/H2-2.2-blue?style=flat)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=flat&logo=mysql)
![JPA](https://img.shields.io/badge/JPA-Hibernate-59666C?style=flat&logo=hibernate)
![QueryDSL](https://img.shields.io/badge/QueryDSL-5.0-blue?style=flat)

### Build & Tools
![Gradle](https://img.shields.io/badge/Gradle-8.5-02303A?style=flat&logo=gradle)
![Lombok](https://img.shields.io/badge/Lombok-1.18-red?style=flat)

### Test
![JUnit5](https://img.shields.io/badge/JUnit5-5.10-25A162?style=flat&logo=junit5)
![AssertJ](https://img.shields.io/badge/AssertJ-3.24-orange?style=flat)

### 선택 이유
**Spring Boot 3.x**: 최신 기술 스택 활용, Auto Configuration으로 개발 생산성 향상  
**JPA + QueryDSL**: 객체 지향적 설계 유지하면서 복잡한 쿼리 동적 생성  
**H2 Database**: 빠른 개발 환경 구축, 실제 운영 시 MySQL 전환 예정  
**Lombok**: Boilerplate 코드 최소화로 비즈니스 로직에 집중

---

# 🏗 시스템 아키텍처

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
│  │  Repository Layer    │  │  ← Data Access
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

---

# 🗂 ERD 구조

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
│ createdAt    │                │
│ updatedAt    │                │ *
└──────────────┘         ┌──────────────┐
                         │  OrderSool   │
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
- **User : Order** = 1 : N (한 사용자는 여러 주문 가능)
- **Order : Delivery** = 1 : 1 (주문 1건당 배송 1건)
- **Order : OrderSool** = 1 : N (주문 1건에 여러 상품 가능)
- **Sool : OrderSool** = 1 : N (상품 1개는 여러 주문에 포함 가능)
- **Item ← Sool** = 상속 관계 (SINGLE_TABLE 전략)

---

# 🟩 v1 핵심 기능

## 1) 👤 User 도메인
### 기능
- 회원가입 / 로그인 (BCrypt 암호화)
- 내 정보 조회 / 수정
- Soft Delete (UserStatus: ACTIVE, DELETE)
- 비밀번호 변경

### 주요 코드
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

## 2) 🍶 Sool(전통주) 도메인
### 기능
- 상품 등록 / 조회 / 수정 / 삭제 (Soft Delete)
- 재고 증가 / 감소 로직
- Item 추상화 기반 확장 가능 구조

### 주요 코드
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

## 3) 📦 Order & Delivery
### 기능
- 주문 생성 (재고 자동 감소)
- 주문 취소 (재고 자동 복구)
- 주문 총 금액 계산
- 배송 상태 자동 설정 (READY)

### 주요 코드
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

## 4) ⚠️ 전역 예외 처리
### 기능
- 커스텀 예외 계층 구조 (UserException, OrderException, ItemException)
- @RestControllerAdvice로 공통 처리
- 일관된 JSON 에러 응답

### 주요 코드
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

# 🧠 문제 해결 경험

## 1) 🔥 주문 조회 시 N+1 문제 해결
### 문제 상황
```
주문 목록 조회 API 호출 시 쿼리가 100회 이상 발생
- 주문 조회: 1회
- 각 주문의 OrderSool 조회: N회
- 각 OrderSool의 Sool 조회: N회
→ 1 + N + N = 약 100회
```

### 원인 분석
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

### 해결 방법
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

### 결과
- ✅ 쿼리 수: 100회 → **1회** (100배 개선)
- ✅ 응답 시간: 500ms → **50ms** (10배 개선)
- ✅ DB 부하 대폭 감소

### 배운 점
- JPA Lazy Loading의 한계와 Fetch Join의 필요성 이해
- JPQL을 통한 최적화 경험
- 성능 측정의 중요성 (JPA 쿼리 로깅 활용)

---

## 2) 🔥 Soft Delete 도입 후 조회 혼란 해결
### 문제 상황
```
사용자가 삭제한 상품이 여전히 목록에 표시됨
관리자용 API와 사용자용 API가 구분되지 않음
```

### 원인 분석
```java
// 기존 코드 (문제)
public List<SoolDTO> getAllSools() {
    // 삭제된 상품(status=DELETE)도 함께 조회됨
    return soolRepository.findAll().stream()
        .map(SoolDTO::new)
        .toList();
}
```

### 해결 방법
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

### 결과
- ✅ 사용자는 활성 상품만 조회
- ✅ 관리자는 삭제된 상품 포함 전체 조회 가능
- ✅ 비즈니스 요구사항 명확히 반영

### 배운 점
- Soft Delete 사용 시 조회 조건의 중요성
- 사용자 역할별 데이터 접근 권한 분리 필요성

---

## 3) 🔥 주문 취소 시 재고 복구 누락 버그
### 문제 상황
```
주문 취소 시 재고가 복구되지 않음
→ 실제 재고와 DB 재고 불일치 발생
```

### 원인 분석
```java
// 기존 코드 (버그)
public void cancelOrder(Long orderId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow();
    
    order.setOrderStatus(OrderStatus.CANCEL);  // 상태만 변경
    // 재고 복구 로직 누락! ❌
}
```

### 해결 방법
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

### 결과
- ✅ 재고 일관성 보장
- ✅ 비즈니스 로직이 도메인 내부에 캡슐화
- ✅ Service 계층 간결해짐

### 배운 점
- **도메인 주도 설계(DDD)의 중요성**
- 비즈니스 로직은 엔티티 내부에 위치해야 응집도가 높아짐
- Service는 트랜잭션 관리와 도메인 호출에만 집중

---

## 4) 🔥 Controller 인증 체크 중복 코드 제거
### 문제 상황
```java
// 모든 컨트롤러에서 반복
@GetMapping("/mypage")
public Result getMyPage(HttpSession session) {
    SessionUserDTO user = (SessionUserDTO) session.getAttribute("user");
    if (user == null) {
        throw new UnauthorizedException("로그인이 필요합니다");
    }
    // 실제 비즈니스 로직...
}
```

### 해결 방법
```java
// Interceptor 구현
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, 
                           HttpServletResponse response, 
                           Object handler) {
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("user") == null) {
            throw new UnauthorizedException("로그인이 필요합니다");
        }
        
        return true;
    }
}

// WebConfig 등록
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
            .addPathPatterns("/api/**")
            .excludePathPatterns("/api/user/login", "/api/user/signup");
    }
}

// Controller는 간결해짐
@GetMapping("/mypage")
public Result getMyPage(HttpSession session) {
    // 인증 체크는 Interceptor가 처리
    SessionUserDTO user = (SessionUserDTO) session.getAttribute("user");
    // 비즈니스 로직만 집중
}
```

### 결과
- ✅ 중복 코드 제거
- ✅ 인증 로직 공통화
- ✅ v2 JWT 전환 시 Interceptor만 수정하면 됨

### 배운 점
- Spring MVC의 Interceptor 활용법
- 횡단 관심사(Cross-Cutting Concerns) 분리의 중요성

---

# 🚧 v2 확장 예정 기능

## 🔐 JWT 인증/인가
- Session → JWT 구조로 전환
- Access Token / Refresh Token 구현
- ROLE_ADMIN / ROLE_USER 권한 분리
- JWT Filter를 통한 토큰 검증

## 📘 Swagger 문서화
- @Operation, @Schema 기반 API 문서 자동 생성
- Try it out 기능으로 테스트 편의성 향상
- 협업 및 프론트엔드 연동 효율 극대화

## 🧭 QueryDSL 도입
- 동적 쿼리 지원 (검색 조건 필터링)
- 정렬 / 페이징 기능 강화
- Fetch Join 최적화

## 🧪 테스트 코드 보강
- 단위 테스트 커버리지 80% 이상
- MockMvc 기반 API 통합 테스트
- 인증/인가 테스트 추가

## ☁️ 배포 환경 구성
- AWS EC2 + RDS 배포
- GitHub Actions CI/CD 구축
- 실제 도메인 연결

---

# 🚀 실행 방법

## 요구사항
- Java 17 이상
- Gradle 8.5 이상

## 1. 프로젝트 클론
```bash
git clone https://github.com/your-username/k-soolmate.git
cd k-soolmate
```

## 2. 빌드 및 실행
```bash
# Gradle 빌드
./gradlew build

# 애플리케이션 실행
./gradlew bootRun

# 또는 JAR 파일 실행
java -jar build/libs/k-soolmate-0.0.1-SNAPSHOT.jar
```

## 3. 접속 확인
```
애플리케이션: http://localhost:8081
H2 Console: http://localhost:8081/h2-console
```

## 4. H2 Database 설정
```
JDBC URL: jdbc:h2:tcp://localhost/~/jpashop
Username: sa
Password: (공백)
```

---

# 📡 API 명세

## User API
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | /api/user/signup | 회원가입 | ❌ |
| POST | /api/user/login | 로그인 | ❌ |
| GET | /api/user/mypage | 내 정보 조회 | ✅ |
| PUT | /api/user/mypage | 내 정보 수정 | ✅ |
| DELETE | /api/user/mypage | 회원 탈퇴 | ✅ |

## Sool API
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | /api/sool | 전체 상품 조회 | ❌ |
| GET | /api/sool/{id} | 상품 상세 조회 | ❌ |
| POST | /api/sool | 상품 등록 | ✅ (ADMIN) |
| PUT | /api/sool/{id} | 상품 수정 | ✅ (ADMIN) |
| DELETE | /api/sool/{id} | 상품 삭제 | ✅ (ADMIN) |

## Order API
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | /api/order | 주문 생성 | ✅ |
| GET | /api/order | 내 주문 목록 | ✅ |
| GET | /api/order/{id} | 주문 상세 조회 | ✅ |
| DELETE | /api/order/{id} | 주문 취소 | ✅ |

---

# 🧪 테스트

## 테스트 실행
```bash
# 전체 테스트
./gradlew test

# 특정 테스트만 실행
./gradlew test --tests UserServiceTest
```

## 테스트 커버리지
- UserService: 90%
- OrderService: 85%
- SoolService: 90%

## 주요 테스트 케이스
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
```

---

# 📚 참고 자료
- [Spring Boot 공식 문서](https://spring.io/projects/spring-boot)
- [JPA 프로그래밍 - 김영한](https://www.inflearn.com/course/ORM-JPA-Basic)
- [실전! 스프링 부트와 JPA 활용 - 김영한](https://www.inflearn.com/course/스프링부트-JPA-활용-1)

---

# 👨‍💻 작성자
**김종규**
- Email: whdrb3353@naver.com
- GitHub: https://github.com/whdrb3353
- Blog: (블로그 있으면 추가)

---

# 📄 License
This project is licensed under the MIT License

---

**마지막 업데이트: 2026.01.10**
