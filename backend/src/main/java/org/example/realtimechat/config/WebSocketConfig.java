package org.example.realtimechat.config;

import lombok.RequiredArgsConstructor;
import org.example.realtimechat.handler.ChatHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
//웹소켓 활성화 어노테이션
// 어떤 경로를 받을지 어떤 핸들러에 연결할지 정의하는 클래
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatHandler chatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //react에서 요청하는 엔드포인트로 웹소켓을 연결하겠습니다
        registry.addHandler(chatHandler,"/ws/chat")
                .setAllowedOrigins("*");//cors때문에 요청이 들어오다가 막히는 것을 방지
    }

}
