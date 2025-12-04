package org.example.stomp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
//    private final CustomUserDetailsService uds;


    public  JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //1) 토큰 꺼내기 확인
        String token = resolve(request); //토큰을 꺼내는 메서드

        //2) 토큰 있어? 유효성 검사
        if(token != null && jwtTokenProvider.isTokenValid(token)){

            //3)  Jwt payload에서 userId항목을 꺼낸다.
            // 이 값이 요청을 보내는 사용자 ID
            Long userId = jwtTokenProvider.getUserId(token);

            //4) user대한 정보 출력하기
            System.out.println("userId =" + userId);
        }

        //3) 다음 요청으로 넘겨라!
        //   만약 이게 없으면 에러가 터진다!
        filterChain.doFilter(request, response);

    }

    //실제 토큰을 깔끔하게 꺼내는 과정
    // 그러면서 혹시 토큰이 없으면 false를 반환해서 더 이상 검증할 필요가 없다!
    private String resolve(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        // Authorization : Bearer {AccessToken}
        if(header != null && header.startsWith("Bearer ")){
            return header.substring(7);
        }
        return null;
    }

}
