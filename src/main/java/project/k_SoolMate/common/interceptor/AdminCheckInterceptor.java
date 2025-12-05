package project.k_SoolMate.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import project.k_SoolMate.domain.user.dto.UserDTO;
import project.k_SoolMate.domain.user.entity.UserRole;

@Component
public class AdminCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
            return false;
        }

        SessionUserDTO user = (SessionUserDTO) session.getAttribute("LOGIN_USER");

        if (user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
            return false;
        }

        if (user.getRole() != UserRole.ADMIN) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자 권한이 필요합니다.");
            return false;
        }

        return true;
    }
}
