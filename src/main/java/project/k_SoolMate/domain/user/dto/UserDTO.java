package project.k_SoolMate.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.k_SoolMate.domain.user.User;

@Getter
@AllArgsConstructor
//DTO로 반환 받는다, 엔티티를 보호하고 보여주고 싶은 dto만 반환해줄수있다.
public class UserDTO {
    private Long id;
    private String loginId;
    private String memberName;
    private String email;
    private String phoneNumber;
    private String role;

    public UserDTO(User user) {
        this.id = user.getId();
        this.loginId = user.getLoginId();
        this.memberName = user.getName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.role = user.getRole().name();
    }
}
