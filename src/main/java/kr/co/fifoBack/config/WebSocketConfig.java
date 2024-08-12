package kr.co.fifoBack.config;

import kr.co.fifoBack.service.CalendarSocketHandler;
import kr.co.fifoBack.service.ChatSocketHandler;
import kr.co.fifoBack.service.DocumentSocketHandler;
import kr.co.fifoBack.service.WebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.eclipse.jdt.internal.compiler.classfmt.FieldInfoWithAnnotation;
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
    private final CalendarSocketHandler calendarSocketHandler;
    private final DocumentSocketHandler documentSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // handler 등록, js에서 new Websocket할 때 경로 지정
        //다른 url에서도 접속할 수있게(CORS방지)
        registry.addHandler(websocketHandler, "/question").setAllowedOrigins("*");
        registry.addHandler(chatSocketHandler, "/chat/{roomId}/{userno}").setAllowedOrigins("*");
        registry.addHandler(calendarSocketHandler, "/cal/{roomId}").setAllowedOrigins("*");
        registry.addHandler(calendarSocketHandler, "/kan/{roomId}").setAllowedOrigins("*");
        registry.addHandler(documentSocketHandler, "/document/{mooimno}").setAllowedOrigins("*");
    }
}