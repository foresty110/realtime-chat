package org.example.realtimechat.web;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.example.realtimechat.service.AuthService;
import org.example.realtimechat.service.UserService;
import org.example.realtimechat.web.dto.LoginRequest;
import org.example.realtimechat.web.dto.SignRequest;
import org.example.realtimechat.web.dto.TokenResponse;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    //회원가입시 데이터 가져오기
    @PostMapping("/signup")
    public String signup(@RequestBody SignRequest signRequest) {

        System.out.println("signup :"+ signRequest.toString());

        //회원가입을 처리하는 서비스 호출하기
        Long response = userService.signup(signRequest);
        System.out.println("signup response:"+ response.toString());
        return "signup";
    }

    //로그인시 데이터 가져오기
    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest loginRequest) {
        TokenResponse response = authService.login(loginRequest);

        System.out.println("login. token :"+ response.toString());

       return response;
    }

    @GetMapping("/test")
    public String test(
            @RequestHeader("Authorization") String authorization
    ){
        // "Bearer {accessToken} -> 실제 부분만 추출해야함
        String token = authorization.replace("Bearer ", "");

        //userId 가져오기
        Claims claims = jwtTokenProvider.parse(token);
        String email = claims.get("email", String.class);
        return "email = " +email;
    }
}
