package project.k_SoolMate.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.LifecycleState;
import project.k_SoolMate.domain.address.Address;
import project.k_SoolMate.domain.order.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자 protected로 막고 기본생성자 만들기
public class User {
    @Id @GeneratedValue
    @Column(name = "member_id")
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

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    @Embedded
    private Address address;

}
