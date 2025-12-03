package org.example.realtimechat.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;


// TextWebSocketHandler
// 메세지를 처리하는 핵심 엔진
// 문자열 기반 메세지를 처리할 수 있다.
// 브라우저가 메세지를 보내면 여기로 들어온다. 서버가 브라우저로 메세지를 보내는 것도 여기서 이루어짐
//채팅 메세지를 관리하는 클래스
@Component
public class ChatHandler extends TextWebSocketHandler {

    //여러명이 접속할 수 있으므로 손님 리스트를 저장한다
    private final List<WebSocketSession> sessions = new ArrayList<>();

    //손님이 카카오톡 채팅방에 입장했을때와 같은 요청
    //웹소켓은 연결이 맺어지는 순간 이 매서드를 자동으로 실행한다.
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session); // 세션으로 들어온 손님을 연결한다
        System.out.println("Connected to " + session.getId());
    }

    //메세지를 보내면 가장 먼저 실행되는 핵심 매서드
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //웹 소켓의 심장
        //서버는 그 메세지를 현재 접속한 모든사람에게 보내줍니다.
        //message.getPayload(): 실제 전송된 텍스트(json)
        //브로드캐스트 구조(모든 사용자에게 메세지 보냄)
        System.out.println("수신 메세지:"+message.getPayload());

        for (WebSocketSession s : sessions) {
            s.sendMessage(new TextMessage(message.getPayload()));
        }

        //포스트맨으로 보내기
        //session.sendMessage(new TextMessage("서버가 받음: "+ message.getPayload()));
        session.sendMessage(new TextMessage(message.getPayload()));

        //jwt 인증 실패,
    }
    //채팅방에서 손님이 나가면 리스트에서 제거한다
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
       sessions.remove(session);
        System.out.println("클라이언트 종료");
    }
}
