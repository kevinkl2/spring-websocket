package us.sphor.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.json.JSONException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import us.sphor.websocket.model.Input;
import us.sphor.websocket.repository.entities.Node;

@Component
public class SocketTextHandler extends TextWebSocketHandler {

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message)
      throws InterruptedException, IOException, JSONException {

    Input input = new ObjectMapper().readValue(message.getPayload(), Input.class);
    Node node = Node.builder().response("test123").build();
    session.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(node)));
    session.sendMessage(new TextMessage("Hi " + input.getUser() + " how may we help you?"));
  }
}
