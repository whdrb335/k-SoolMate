package project.k_SoolMate.common.interceptor;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.k_SoolMate.domain.user.entity.UserRole;
@Schema(description = "세션에 저장되는 로그인 사용자 정보")
@Getter
@AllArgsConstructor
public class SessionUserDTO {
    private Long id;
    private String loginId;
    private UserRole role;
}

