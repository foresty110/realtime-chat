package org.example.realtimechat.web;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.realtimechat.service.CustomUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//OncePerRequestFilter
//http하나의 요청 마다 한 번만 실행되는 필터를 만들기 위해 상속받은 추상클래스

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    //모든 요청이 들어올 때마다 실행하는 매서드
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //요청 헤더에서 jwt 뽑기
        String token = resolve(request);

        //토큰이 만료되었는지 확인
        if (token != null && jwtTokenProvider.validate(token)) {
            // 유효한 토큰인 경우 사용자 정보 꺼내기
            Claims claims = jwtTokenProvider.parse(token);
            String email = claims.get("email", String.class);
            CustomUserDetails user = (CustomUserDetails)
                    userDetailsService.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        // 다음 요청으로 넘기기
        filterChain.doFilter(request, response);

    }
    private String resolve(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        return (bearer!= null && bearer.startsWith("Bearer "))
                ? bearer.substring(7)
                : null;
    }
}