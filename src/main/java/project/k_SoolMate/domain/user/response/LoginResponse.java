package project.k_SoolMate.domain.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private Long userId;
    private String loginId;
    private String role;
    private String accessToken;
    private String refreshToken;
}
