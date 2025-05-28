package project.k_SoolMate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //초기 개발 단계에서 테스트를 쉽게하기 위해 작성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF 비활성화
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // 모든 요청 허용
            )
            .formLogin(Customizer.withDefaults()) // 기본 로그인 폼 활성화 (비활성화하려면 .disable())
            .httpBasic(Customizer.withDefaults()); // 기본 HTTP 인증

        return http.build();
    }
}
