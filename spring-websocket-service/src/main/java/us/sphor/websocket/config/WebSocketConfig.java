package us.sphor.websocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import us.sphor.websocket.services.ChatService;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

  @Autowired private ChatService chatService;

  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(chatService, "/chat").setAllowedOrigins("https://dadmin.sphor.us");
  }
}
