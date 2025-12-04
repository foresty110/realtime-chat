package org.example.stomp.controller;

import org.example.stomp.dto.LoginReqeust;
import org.example.stomp.dto.TokenResponse;
import org.example.stomp.security.JwtTokenProvider;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    //    private final AuthService authService;
    private final JwtTokenProvider jwt;

    public AuthController(JwtTokenProvider jwt) {
        this.jwt = jwt;

    }


    //로그인시 데이터 가져오기
    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginReqeust login) {

        Long userId = 1L;
        // 기존 userId를 이용해서 새로운 토큰 생성
        String token = jwt.generateAccessToken(userId);
        System.out.println("토큰값: " + token);
        return new TokenResponse(token,userId);
    }
}
