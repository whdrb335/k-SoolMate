package project.k_SoolMate.domain.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 비밀번호 일치 여부 확인 요청 DTO
 * - 클라이언트에서 입력한 비밀번호가
 *   서버에 저장된 비밀번호와 같은지 검증할 때 사용
 * - @NotBlank 로 빈 문자열 요청 방지
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class isMatchPasswdRequest {

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
}
