package org.example.realtimechat.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

//프로젝트가 시작할때 설정으로 쓰겠다.
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())//쿠키 세션의 로그인 방식은 사용하지 않도록 설정
                .formLogin(formLogin -> formLogin.disable())//기본 제공 폼 로그인 사용 제거
                .httpBasic(httpBasicAuth -> httpBasicAuth.disable()) //옛날 인증 방식 제외
                .authorizeHttpRequests(auth-> auth.requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
