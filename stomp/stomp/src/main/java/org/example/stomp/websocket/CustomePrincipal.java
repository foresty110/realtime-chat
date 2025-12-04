package org.example.stomp.websocket;

import lombok.RequiredArgsConstructor;

import java.security.Principal;

//웹 소켓에서 사용할 나만의 사용자 정보 카드
//스톰프는 필터가 없다 그래서 직접 사용자 정보를 만들어서 넣어줘야한다.
@RequiredArgsConstructor
public class CustomePrincipal implements Principal {

    private final Long userId;

    @Override
    public String getName() {
        return String.valueOf(userId);
    }
    public Long getUserId() {
        return userId;
    }
}
