package org.example.stomp.service;

import org.example.stomp.dto.LoginReqeust;
import org.example.stomp.dto.TokenResponse;
import org.example.stomp.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    //인증과 관련된 서비스 처리
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public  AuthService(PasswordEncoder passwordEncoder
            , JwtTokenProvider jwtTokenProvider) {

        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //1) 이메일 찾기
    public TokenResponse login(LoginReqeust loginReqeust){
        return null;
    }


}
