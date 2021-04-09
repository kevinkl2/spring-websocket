package us.sphor.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import us.sphor.websocket.model.Input;
import us.sphor.websocket.repository.ChatNodeRepository;
import us.sphor.websocket.repository.entities.Node;

@Component
@RequiredArgsConstructor
@Slf4j
public class SocketTextHandler extends TextWebSocketHandler {

  private final ChatNodeRepository chatNodeRepository;

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message)
      throws InterruptedException, IOException, JSONException {

    Input input = new ObjectMapper().readValue(message.getPayload(), Input.class);

    Node queryResult;

    try {
      queryResult = chatNodeRepository.findById(input.getUser()).get();
    } catch (RuntimeException runtimeException) {
      log.error("saveNode ERROR: ", runtimeException.toString());
      throw runtimeException;
    }

    if (queryResult == null) {
      session.sendMessage(new TextMessage("NOT FOUND"));
    }

    session.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(queryResult)));
  }
}
