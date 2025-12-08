# 🍶 K-SoolMate  
**한국 전통주를 소개하고 주문할 수 있는 e-commerce 백엔드 시스템**

세션 기반 인증 · 전통주 상품 관리 · 주문/배송 관리 ·  
JPA 기반 도메인 설계 · Spring Boot 실무 구조로 구현한 주문 플랫폼

---

## 📌 목차
- [프로젝트 소개](#-프로젝트-소개)
- [기능 요약](#-기능-요약)
- [v1 핵심 기능 상세](#-v1-핵심-기능-상세)
- [ERD 구조](#-erd-구조)
- [기술 스택](#-기술-스택)
- [실행 방법](#-실행-방법)
- [버전 로드맵](#-버전-로드맵)
- [작성자](#-작성자)

---

## 📖 프로젝트 소개
**K-SoolMate**는 한국 전통주를 소개하고 주문할 수 있는 e-commerce 형태의  
백엔드 시스템입니다.  
도메인 중심 설계, 세션 기반 인증, JPA 성능 최적화 적용을 목표로 개발되었습니다.

---

## ⚙️ 기능 요약
- 사용자 회원가입 / 로그인 (세션 기반 인증)
- 전통주 상품 등록, 조회, 삭제(Soft Delete)
- 주문 생성 및 취소
- 배송 상태 관리
- 관리자 / 일반 사용자 권한 분리

---

## 🔍 v1 핵심 기능 상세
### 1) **User**
- Soft Delete 적용
- JPA 엔티티 생명주기 콜백으로 생성/수정 시간 자동 관리
- 세션 기반 인증 및 권한 확인

### 2) **Sool (전통주 상품)**
- ENUM 기반 삭제 상태 관리
- 권한별 접근 제어 (ADMIN / CLIENT)

### 3) **Order & Delivery**
- 주문 생성 시 Delivery READY 상태 자동 설정
- 본인 주문만 조회 가능
- 주문 취소 시 재고/상태 반영

---

## 🗂 ERD 구조
(이미지 추가 예정)

---

## 🛠 기술 스택
- Java 17  
- Spring Boot 3.x  
- Spring Data JPA  
- H2 / MySQL  
- Gradle  
- Lombok  

---

## 🚀 실행 방법
```bash
git clone https://github.com/whdrb335/k-SoolMate.git
cd k-SoolMate
./gradlew bootRun
