package project.k_SoolMate.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.k_SoolMate.common.interceptor.LoginCheckInterceptor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final LoginCheckInterceptor loginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/api/user/**", "/api/order/**")
                .excludePathPatterns(
                        "/api/user/login",
                        "/api/user/save"
                );
    }
}
