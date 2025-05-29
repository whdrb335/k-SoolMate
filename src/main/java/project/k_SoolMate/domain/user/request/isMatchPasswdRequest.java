package project.k_SoolMate.domain.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class isMatchPasswdRequest {

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String passwd;
}
