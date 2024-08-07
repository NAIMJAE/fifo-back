package kr.co.fifoBack.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@RequiredArgsConstructor
@Component
public class CalendarSocketHandler extends TextWebSocketHandler {
    // 방 별로 세션을 관리하기 위한 ConcurrentMap
    private static final ConcurrentMap<String, Map<String, WebSocketSession>> roomSessions = new ConcurrentHashMap<>();

    // JSON 변환을 위한 ObjectMapper
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 웹 소켓이 클라이언트와 연결된 후 실행되는 메서드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 소켓 연결 URI에서 방 번호와 회원 번호 추출
        String roomId = getRoomId(session);

        // roomSessions에 방 정보 세션 저장
        roomSessions.computeIfAbsent(roomId, k -> new ConcurrentHashMap<>()).put(session.getId(), session);

        log.info("연결된 소켓 세션 아이디 : " + session.getId() + " 방 번호 : " + roomId);
    }

    // 웹 소켓이 클라이언트로부터 메세지를 전송받았을 때 실행되는 메서드
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String roomId = getRoomId(session);
        System.out.println("받은 메세지 : " + message.getPayload() + " 방 번호 : " + roomId);

        // 받은 메시지를 JSON으로 파싱하여 type 필드 추가
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("type", "message");
        messageMap.put("payload", message.getPayload());
        String jsonMessage = objectMapper.writeValueAsString(messageMap);

        // 같은 방에 있는 모든 클라이언트에게 메시지 방송
        for (WebSocketSession webSocketSession : roomSessions.get(roomId).values()) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(new TextMessage(jsonMessage));
            }
        }
    }

    // 웹 소켓이 클라이언트와 연결이 끊겼을 때 실행되는 메서드
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String roomId = getRoomId(session);

        // roomSessions에서 세션 제거
        roomSessions.getOrDefault(roomId, new ConcurrentHashMap<>()).remove(session.getId());

        log.info("연결을 종료할 소켓 세션 아이디 : " + session.getId() + " 방 번호 : " + roomId);
    }

    // URI에서 roomId를 추출
    private String getRoomId(WebSocketSession session) {
        String path = session.getUri().getPath();
        return path.split("/")[3];
    }
}
