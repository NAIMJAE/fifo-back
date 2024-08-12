package kr.co.fifoBack.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
@Slf4j
@RequiredArgsConstructor
@Component
public class DocumentSocketHandler extends TextWebSocketHandler {
    // 현재 연결된 세션들
    private final Set<WebSocketSession> sessions = new HashSet<>();

    // roomId:{session1, session2}
    private final Map<String, Set<WebSocketSession>> documentSessionMap = new HashMap<>();

    /**세션 연결 확인*/
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("연결됨 + " + session.getId());
        log.info("연결됨..22 + " + session);
        sessions.add(session);

        String roomId = getRoomId(session);
        log.info("문서 방 번호:" + roomId);

        // roomId로 키 값을 탐색하여 존재하지 않으면 맵에 추가
        if(roomId != null) documentSessionMap.computeIfAbsent(roomId, k-> new HashSet<>()).add(session);

    }

    /**소켓 통신시 메시지 전송*/
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("문서 페이로드" + payload);

        // 방 정보를 추출, 메시지를 방에 있는 모든 세션으로 전달
        String roomId = getRoomId(session);
        if(roomId != null){
            Set<WebSocketSession> sessionsInroom = documentSessionMap.get(roomId);

            if(sessionsInroom !=null){
                for(WebSocketSession webSocketSession : sessionsInroom){
                    if(webSocketSession.isOpen()) webSocketSession.sendMessage(message);
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        log.info("문서 연결종료", session.getId());
        String roomId = getRoomId(session);

        if(roomId !=null){
            Set<WebSocketSession> sessionsInRoom = documentSessionMap.get(roomId);

            if(sessionsInRoom != null) sessionsInRoom.remove(session);
            if(sessionsInRoom.isEmpty()) documentSessionMap.remove(roomId);
        }
    }

    private String getRoomId(WebSocketSession session) {
        String path = session.getUri().getPath();
        return path.split("/")[3];
    }
}
