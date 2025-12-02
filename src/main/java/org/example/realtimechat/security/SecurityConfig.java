package org.example.realtimechat.security;

import lombok.RequiredArgsConstructor;
import org.example.realtimechat.service.CustomUserDetailsService;
import org.example.realtimechat.web.JwtAuthenticationFilter;
import org.example.realtimechat.web.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//프로젝트가 시작할때 설정으로 쓰겠다.
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwt;
    private final CustomUserDetailsService uds;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        JwtAuthenticationFilter jwtFilter =
                new JwtAuthenticationFilter(jwt, uds);

        http.csrf(csrf -> csrf.disable())//쿠키 세션의 로그인 방식은 사용하지 않도록 설정
                .formLogin(formLogin -> formLogin.disable())//기본 제공 폼 로그인 사용 제거
                .httpBasic(httpBasicAuth -> httpBasicAuth.disable()) //옛날 인증 방식 제외
                .addFilterBefore(new UrlCheckFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth->
                        auth.requestMatchers("/api/auth/**").permitAll()
                                .anyRequest().authenticated()
                )
                //폼 로그인 필터보다 먼저 jwt를 검사해서 사용자를 인증상태로 만들겠다.
                .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    //암호화 하는 매서드
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
