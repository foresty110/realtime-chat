package org.example.realtimechat.service;

import lombok.RequiredArgsConstructor;
import org.example.realtimechat.domain.user.User;
import org.example.realtimechat.domain.user.UserRepository;
import org.example.realtimechat.web.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. email=" + email)
                );

        // 🔥 여기서 CustomUserDetails 반환
        return new CustomUserDetails(user);
    }
}
