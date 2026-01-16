package project.k_SoolMate.domain.user.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.k_SoolMate.domain.address.Address;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회원 가입 요청 DTO")
public class CreateUserRequest {

    @Schema(description = "로그인 아이디", example = "whdrb3353")
    @NotBlank(message = "아이디는 필수 입니다.")
    private String loginId;

    @Schema(description = "로그인 비밀번호", example = "1234!")
    @NotBlank(message = "비밀번호는 필수 입니다.")
    private String loginPw;

    @Schema(description = "회원 이름", example = "김종규")
    @NotBlank(message = "이름은 필수 입니다.")
    private String memberName;

    @Schema(description = "핸드폰 번호 (010-XXXX-XXXX 형식)", example = "010-1234-5678")
    @NotBlank(message = "핸드폰번호는 필수 입니다.")
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "핸드폰 번호는 010-XXXX-XXXX 형식이어야 합니다.")
    private String phoneNumber;

    @Schema(description = "이메일 주소", example = "jonggyu@example.com")
    @Email(message = "올바른 이메일 주소를 입력하세요.")
    private String email;

    @Schema(description = "회원 주소 정보")
    @Valid
    private Address address;
}
