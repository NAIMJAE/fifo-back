package kr.co.fifoBack.config;

import kr.co.fifoBack.service.ChatSocketHandler;
import kr.co.fifoBack.service.WebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class WebSocketConfig  implements WebSocketConfigurer {
    private final WebSocketHandler websocketHandler;
    private final ChatSocketHandler chatSocketHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // handler 등록,   js에서 new Websocket할 때 경로 지정
        //다른 url에서도 접속할 수있게(CORS방지)
        registry.addHandler(websocketHandler, "/question").setAllowedOrigins("*");
        registry.addHandler(chatSocketHandler, "/chat/{roomId}/{userno}").setAllowedOrigins("*");
    }
}