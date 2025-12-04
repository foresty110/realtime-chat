package org.example.stomp.websocket;

import lombok.RequiredArgsConstructor;
import org.example.stomp.security.JwtTokenProvider;
import org.jspecify.annotations.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {
    //jwt인증
    private final JwtTokenProvider jwtTokenProvider;

    //채팅방 입장할때 토큰검사하고 그 토큰 속 userId Websocket 정보에 넣는 과정
    @Override
    public @Nullable Message<?> preSend(Message<?> message, MessageChannel channel) {

        //메세지 안에 스톰프가 있으면 그걸 꺼내기 위해서 래핑한다.
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        //입장할 때만 검사하겠다 //웹 소켓 연결 시도인지 확인
        if(StompCommand.CONNECT.equals(accessor.getCommand())) {
            //프론트에서 Authorization 헤더에서 토큰 꺼내기
            String token = accessor.getFirstNativeHeader("Authorization");

            if(token != null){
                token = token.replace("Bearer ","");
                // 누구의 토큰인지 확인해서 토큰 속 userId를 꺼낸다
                Long userId = jwtTokenProvider.getUserId(token);

                // 서버는 어떤 사용자가 구독했는지, 누가 보낸 메세지인지 알 수 있게 된다.
                accessor.setUser(new CustomePrincipal(userId));
            }
        }

        // 중간에서 인터셉터했기 때문에 다음 단계로 전달하기 위함
        return message;
    }
}
