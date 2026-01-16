//package project.k_SoolMate.common.jwt;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class AdminAuthorizationFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest request,
//                         ServletResponse response,
//                         FilterChain chain)
//                         throws IOException, ServletException {
//
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res = (HttpServletResponse) response;
//
//        String uri = req.getRequestURI();
//
//        // 여기 반드시 /api/admin
//        if (uri.startsWith("/api/admin")) {
//
//            String role = (String) req.getAttribute("role");
//
//            if (role == null || !role.equals("ADMIN")) {
//                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                return;
//            }
//        }
//
//        chain.doFilter(request, response);
//    }
//}
//
