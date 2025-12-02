package org.example.realtimechat.service;

import lombok.RequiredArgsConstructor;
import org.example.realtimechat.domain.user.User;
import org.example.realtimechat.domain.user.UserRepository;
import org.example.realtimechat.web.JwtTokenProvider;
import org.example.realtimechat.web.dto.LoginRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    //이메일 찾기
    public String login(LoginRequest loginRequest) {

        //user 정보가 없을 경우 예외처리
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
                ()-> new IllegalStateException("이메일을 찾을 수 없습니다.")
        );

        //이메일과 비밀번호를 확인
        //회원가입시 입력한 비밀번호와 일치하는지 확인
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new IllegalStateException("잘못된 이메일 또는 비밀번호입니다");
        }

        //토큰 발급
        return jwtTokenProvider.createAccessToken(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );
    }
}
