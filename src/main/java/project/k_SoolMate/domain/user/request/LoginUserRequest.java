package project.k_SoolMate.domain.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자 로그인 요청 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "로그인 요청 모델")
public class LoginUserRequest {

    @Schema(description = "사용자 아이디", example = "jongkyu123")
    @NotBlank(message = "아이디는 필수 입니다.")
    private String loginId;

    @Schema(description = "사용자 비밀번호", example = "1234")
    @NotBlank(message = "비밀번호는 필수 입니다.")
    private String loginPw;
}
