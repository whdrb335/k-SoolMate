package project.k_SoolMate.domain.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 비밀번호 변경 요청 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "비밀번호 변경 요청 모델")
public class UpdatePasswdRequest {

    @Schema(description = "현재(기존) 비밀번호", example = "oldPassword123!")
    @NotBlank(message = "원래 비밀번호를 입력하십시오.")
    private String oldPasswd;

    @Schema(description = "새로 설정할 비밀번호", example = "newPassword456!")
    @NotBlank(message = "새로운 비밀번호를 입력하십시오.")
    private String newPasswd;
}
