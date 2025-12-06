package project.k_SoolMate.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.k_SoolMate.common.interceptor.AdminCheckInterceptor;
import project.k_SoolMate.common.interceptor.LoginCheckInterceptor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final LoginCheckInterceptor loginCheckInterceptor;
    private final AdminCheckInterceptor adminCheckInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/api/user/**", "/api/order/**")
                .excludePathPatterns(
                        "/api/user/login",
                        "/api/user/save"
                );
        // 관리자 체크
        registry.addInterceptor(adminCheckInterceptor)
                .addPathPatterns("/api/admin/**");
    }


}
