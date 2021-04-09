package us.sphor.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import us.sphor.websocket.model.Input;

@Component
public class SocketTextHandler extends TextWebSocketHandler {

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message)
      throws InterruptedException, IOException, JSONException {

    String payload = message.getPayload();
    Input input = new ObjectMapper().readValue(payload, Input.class);
    System.out.println(input.toString());
    JSONObject jsonObject = new JSONObject(payload);
    session.sendMessage(new TextMessage("Hi " + jsonObject.get("user") + " how may we help you?"));
  }
}
