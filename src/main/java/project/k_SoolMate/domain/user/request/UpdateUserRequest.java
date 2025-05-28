package project.k_SoolMate.domain.user.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.k_SoolMate.domain.address.Address;

@Data
@NoArgsConstructor
public class UpdateUserRequest {

    @NotBlank(message = "휴대폰 번호는 필수 입니다.")
    private String phoneNumber;
    @Email
    private String email;
    @Valid
    private Address address;
}
