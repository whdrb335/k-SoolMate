package project.k_SoolMate.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.k_SoolMate.common.jwt.AdminAuthorizationFilter;
import project.k_SoolMate.common.jwt.JwtAuthFilter;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtAuthFilter jwtAuthFilter;
    private final AdminAuthorizationFilter adminAuthFilter;

    @Bean
    public FilterRegistrationBean<JwtAuthFilter> jwtFilter() {
        FilterRegistrationBean<JwtAuthFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(jwtAuthFilter);
        bean.addUrlPatterns("/*");
        bean.setOrder(1);
        return bean;
    }

    @Bean
    public FilterRegistrationBean<AdminAuthorizationFilter> adminFilter() {
        FilterRegistrationBean<AdminAuthorizationFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(adminAuthFilter);
        bean.addUrlPatterns("/api/admin/*"); // 관리자 API만 필터 적용
        bean.setOrder(2);
        return bean;
    }
}

