package org.example.realtimechat.service;

import lombok.RequiredArgsConstructor;
import org.example.realtimechat.domain.user.Role;
import org.example.realtimechat.domain.user.User;
import org.example.realtimechat.domain.user.UserRepository;
import org.example.realtimechat.web.dto.SignRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입시 실행할 매서드
    public Long signup(SignRequest signRequest) {

        // 회원가입 요청한 이메일의 중복을 확인함
        if(userRepository.existsByEmail(signRequest.getEmail())){
            throw new IllegalArgumentException("중복 이메일");
        }

        // 이메일 중복이 아닐 경우 비밀번호를  암호화해서 보내야한다.
        String encodePassword = passwordEncoder.encode(signRequest.getPassword());

        // 데이터베이스로 보내기 위해서 user엔티티로 정보를 묶는다.
        User user = new User(
                signRequest.getName(),
                signRequest.getEmail(),
                encodePassword,
                Role.USER
        );

        //DB호출을 한 다음에 저장이 완료되면 아이디를 가져와서 컨트롤러에게 결과를 보내준다
        return userRepository.save(user).getId();
    }
}
