package project.k_SoolMate.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import project.k_SoolMate.domain.address.Address;
import project.k_SoolMate.domain.user.entity.User;
import project.k_SoolMate.domain.user.entity.UserRole;
import project.k_SoolMate.domain.user.entity.UserStatus;

@Getter
@AllArgsConstructor
@Schema(description = "회원 정보 DTO")
public class UserDTO {

    @Schema(description = "회원 ID", example = "1")
    private Long id;

    @Schema(description = "로그인 ID", example = "soolmate01")
    private String loginId;

    @Schema(description = "회원 이름", example = "김종규")
    private String memberName;

    @Schema(description = "회원 이메일", example = "sool@ksoolmate.com")
    private String email;

    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phoneNumber;

    @Schema(description = "역할 (ADMIN 또는 CLIENT)", example = "CLIENT")
    private UserRole role;

    @Schema(description = "주소 정보")
    private Address address;

    @Schema(description = "회원 상태 (ACTIVE / DELETED)", example = "ACTIVE")
    private UserStatus userStatus;

    public UserDTO(User user) {
        this.id = user.getId();
        this.loginId = user.getLoginId();
        this.memberName = user.getName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.role = user.getRole();
        this.address = user.getAddress();
        this.userStatus = user.getStatus();
    }
}
