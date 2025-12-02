package org.example.realtimechat.web;

import lombok.RequiredArgsConstructor;
import org.example.realtimechat.service.UserService;
import org.example.realtimechat.web.dto.LoginRequest;
import org.example.realtimechat.web.dto.SignRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

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
    public String login(@RequestBody LoginRequest loginRequest) {
        return "login";
    }
}
