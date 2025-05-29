package project.k_SoolMate.domain.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdatePasswdRequest {

    @NotBlank(message = "원래 비밀번호를 입력하십시오.")
    private String oldPasswd;
    @NotBlank(message = "새로운 비밀번호를 입력하십시오.")
    private String newPasswd;
}
