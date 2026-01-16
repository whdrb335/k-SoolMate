package project.k_SoolMate.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import project.k_SoolMate.domain.address.Address;
import project.k_SoolMate.domain.order.entity.Order;
import project.k_SoolMate.exception.user.NotMatchPasswd;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자 protected로 막고 기본생성자 만들기
@Table(name = "\"user\"") // 큰따옴표 이스케이프
public class User {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String loginId;
    private String loginPw;
    private String name;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String phoneNumber;
    private String email;
    private String refreshToken;

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }


    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    @Embedded
    private Address address;

    /**
     * 연관관계 메서드
     */
    //양방향 매핑을 해야하는데 엔티티는 setter 열리지않아서 order에만 set해줌
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    //==생성 메서드==//

    /**
     * 멤버 생성
     */
    public static User createUser(String loginId, String loginPw, String name, String phoneNumber, String email, Address address,BCryptPasswordEncoder bCryptPasswordEncoder) {
        User user = new User();
        user.loginId = loginId;
        //비밀번호를 암호화시켜서 저장한다.
        user.loginPw = bCryptPasswordEncoder.encode(loginPw);
        user.name = name;
        user.role = UserRole.USER;
        user.status = UserStatus.ACTIVE;
        user.phoneNumber = phoneNumber;
        user.email = email;
        user.address = address;
        return user;
    }

    /**
     * 관리자 생성
     */
    public static User admin(String loginId, String loginPw, String name, BCryptPasswordEncoder bCryptPasswordEncoder) {
        User user = new User();
        user.loginId = loginId;
        //비밀번호를 암호화시켜서 저장한다.
        user.loginPw = bCryptPasswordEncoder.encode(loginPw);
        user.name = name;
        user.role = UserRole.ADMIN;
        user.status = UserStatus.ACTIVE;
        return user;
    }

    /**
     * 멤버 수정
     */
    public void updateUser(String phoneNumber, String email, Address address) {
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    /**
     * 비밀번호 확인 로직
     */
    public void isMatchPasswd(String passwd,BCryptPasswordEncoder bCryptPasswordEncoder) {
        if (!bCryptPasswordEncoder.matches(passwd, this.loginPw)) {
            throw new NotMatchPasswd("비밀번호가 맞지 않습니다", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 비밀번호 변경 로직
     */
    public void changePasswd(String oldPasswd, String newPasswd,BCryptPasswordEncoder bCryptPasswordEncoder) {
        if (!bCryptPasswordEncoder.matches(oldPasswd, this.loginPw)) {
            throw new NotMatchPasswd("비밀번호가 맞지 않습니다", HttpStatus.BAD_REQUEST);
        }
        this.loginPw = bCryptPasswordEncoder.encode(newPasswd);
    }

    /**
     * 멤버 삭제
     */
    //멤버 삭제는 repository 삭제가 아닌 Soft Delete라서 상태 변경만 한다.
    public void deleteMember() {
        this.status = UserStatus.DELETE;
    }
    /**
     * JPA 콜백 메서드로 생성/수정 시간 자동 설정
     */
    @PrePersist // 저장 전 자동으로 createAt, updatedAt 설정
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate // 수정 전 자동으로 updatedAt 갱신
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


}
