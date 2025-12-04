package org.example.stomp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
//웹 소켓은 열어주고 클라이언트가 보내는 메세지를 가로채서 연결하는 역할
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/chat")
                .setAllowedOrigins("http://localhost:5173")
                .setAllowedOriginPatterns("*") //cors해제 - 프론트에서 접속하는 포트와 서버의 포트가 다른데 접속을 하려고하면 이걸 막는데,
                // 막지않도록 해제하는 방법
                .withSockJS(); //웹 소켓이 지원이 안되는 환경에서 접속을 해야할때 js를 다운받아서 하는것
    }

    //stomp의 메세지를 보내고 받으려면 브로커 아웃바운드 설정이 없으면 메세지 동작하지 않음
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
