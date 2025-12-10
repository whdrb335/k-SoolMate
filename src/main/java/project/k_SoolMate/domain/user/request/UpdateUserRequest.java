package project.k_SoolMate.domain.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.k_SoolMate.domain.address.Address;

/**
 * 회원 정보 수정 요청 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회원 정보 수정 요청 모델")
public class UpdateUserRequest {

    @Schema(description = "휴대폰 번호", example = "010-1234-5678")
    @NotBlank(message = "휴대폰 번호는 필수 입니다.")
    private String phoneNumber;

    @Schema(description = "이메일 주소", example = "user@example.com")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email;

    @Schema(description = "주소 정보", implementation = Address.class)
    @Valid
    private Address address;
}
