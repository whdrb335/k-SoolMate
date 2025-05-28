package project.k_SoolMate.domain.user.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.k_SoolMate.domain.address.Address;

@Data
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "아이디는 필수 입니다.")
    private String loginId;
    @NotBlank(message = "비밀번호는 필수 입니다.")
    private String loginPw;
    @NotBlank(message = "이름은 필수 입니다.")
    private String memberName;
    @NotBlank(message = "핸드폰번호는 필수 입니다.")
    private String phoneNumber;
    @Email
    private String email;
    @Valid // Address 검증을 @Valid로 한다.
    private Address address;


}
