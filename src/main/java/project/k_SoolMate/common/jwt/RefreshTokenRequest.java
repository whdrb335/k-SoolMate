package project.k_SoolMate.common.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RefreshTokenRequest {
    private String refreshToken;
}