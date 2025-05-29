package project.k_SoolMate.domain.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginUserRequest {
    @NotBlank(message = "아이디는 필수 입니다.")
    private String loginId;
    @NotBlank(message = "비밀번호는 필수 입니다.")
    private String loginPw;
}
