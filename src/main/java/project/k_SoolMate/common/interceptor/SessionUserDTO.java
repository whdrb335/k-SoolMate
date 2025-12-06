package project.k_SoolMate.common.interceptor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.k_SoolMate.domain.user.entity.UserRole;

@Getter
@AllArgsConstructor
public class SessionUserDTO {
    private Long id;
    private String loginId;
    private UserRole role;
}

