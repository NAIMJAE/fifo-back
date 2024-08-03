package kr.co.fifoBack.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/*
* [정리]
* 1. ConcurrentMap<String, Map<String, WebSocketSession>> roomSessions
*   - 소켓으로 통신할 방을 관리하기 위한 자료구조
*   - 방 번호를 키로 가지며, 값은 다시 세션 아이디와 세션으로 이루어진 Map으로 구성
        {
            "room1": {
                "session1": session1,
                "session2": session2
            },
        }

* 2. Map<String, List<String>> memberSessions
*   - 같은 소켓에 연결된 사용자 목록을 관리하기 위한 자료구조
*   - 방 번호를 키로 가지며, 값은 사용자 목록을 담은 List로 구성
        {
            "room1": {"userno1", "userno2" ...}
        }

* 3. afterConnectionEstablished 메서드
*   - 웹 소켓이 클라이언트와 연결된 후 실행
*   - 소켓 연결을 위해 사용한 URI에서 방 번호와 회원 번호를 추출해 저장
*   - ex) ws://localhost:8080/fifo-back/chat/1/chatRoom7
*   - sendUserList 메서드를 실행하여 현재 접속 중인 사용자들에게 새로운 사용자 목록 전송

* 4. handleTextMessage 메서드
*   - 방 번호와 소켓으로 전송된 메세지를 추출하여 같은 방에 접속 중인 사용자들에게 메세지 전송
*   - 사용자 목록을 전송하는 데이터와 구별하기 위해 (Key, Value) 값으로 전송
        {
            payload: "JSON으로 파싱한 전송할 메세지 데이터"
            type: "message"
        }

* 5. sendUserList 메서드
*   - memberSessions에 저장된 같은 방에 접속 중인 사용자 목록을 전송
*   - 메세지 전송 데이터와 구별하기 위해 (Key, Value) 값으로 전송
        {
            payload: "회원 목록"
            type: "userList"
        }
*/

@Slf4j
@RequiredArgsConstructor
@Component
public class ChatSocketHandler extends TextWebSocketHandler {
    // 방 별로 세션을 관리하기 위한 ConcurrentMap
    private static final ConcurrentMap<String, Map<String, WebSocketSession>> roomSessions = new ConcurrentHashMap<>();

    // 현재 같은 소켓에 연결된 사용자 목록을 관리하기 위한 ConcurrentMap
    private final Map<String, List<String>> memberSessions = new ConcurrentHashMap<>();

    // JSON 변환을 위한 ObjectMapper
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 웹 소켓이 클라이언트와 연결된 후 실행되는 메서드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 소켓 연결 URI에서 방 번호와 회원 번호 추출
        String roomId = getRoomId(session);
        String userno = getUserNo(session);

        // roomSessions에 방 정보 세션 저장
        roomSessions.computeIfAbsent(roomId, k -> new ConcurrentHashMap<>()).put(session.getId(), session);
        
        // memberSessions에 접속한 방 번호에 회원 번호 저장
        memberSessions.computeIfAbsent(roomId, k -> new ArrayList<>()).add(userno);

        log.info("연결된 소켓 세션 아이디 : " + session.getId() + " 방 번호 : " + roomId + " 회원 번호 : " + userno);

        // 같은 소켓에 접속한 사용자들에게 현재 접속중인 사용자 목록 전송
        sendUserList(roomId);
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
        String userno = getUserNo(session);

        // roomSessions에서 세션 제거
        roomSessions.getOrDefault(roomId, new ConcurrentHashMap<>()).remove(session.getId());

        // memberSessions에서 사용자 제거
        List<String> userList = memberSessions.getOrDefault(roomId, new ArrayList<>());
        userList.remove(userno);

        log.info("연결을 종료할 소켓 세션 아이디 : " + session.getId() + " 방 번호 : " + roomId);

        // 사용자 목록 업데이트 및 방송
        sendUserList(roomId);
    }

    // URI에서 roomId를 추출
    private String getRoomId(WebSocketSession session) {
        String path = session.getUri().getPath();
        return path.split("/")[4];
    }

    // URI에서 userno를 추출
    private String getUserNo(WebSocketSession session) {
        String path = session.getUri().getPath();
        return path.split("/")[3];
    }

    // 방에 접속 중인 사용자 목록을 같은 방의 모든 사용자에게 전송
    private void sendUserList(String roomId) {
        List<String> userList = memberSessions.getOrDefault(roomId, new ArrayList<>());
        Map<String, Object> userListMessage = new HashMap<>();
        userListMessage.put("type", "userList");
        userListMessage.put("payload", userList);
        try {
            String jsonMessage = objectMapper.writeValueAsString(userListMessage);
            for (WebSocketSession session : roomSessions.get(roomId).values()) {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(jsonMessage));
                }
            }
        } catch (Exception e) {
            log.error("사용자 목록 방송 오류: ", e);
        }
    }
}
