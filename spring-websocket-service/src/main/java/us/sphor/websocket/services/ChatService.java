package us.sphor.websocket.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import us.sphor.websocket.handlers.db.NodeHandler;
import us.sphor.websocket.handlers.db.UserHandler;
import us.sphor.websocket.model.Input;
import us.sphor.websocket.model.Response;
import us.sphor.websocket.repository.entities.Node;
import us.sphor.websocket.repository.entities.User;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatService extends TextWebSocketHandler {

  private final UserHandler userHandler;
  private final NodeHandler nodeHandler;

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message)
      throws InterruptedException, IOException, JSONException {

    Input input = new ObjectMapper().readValue(message.getPayload(), Input.class);

    User user = userHandler.get(input.getUserId());

    if (Objects.isNull(input.getUserId()) || input.getUserId().isEmpty() || Objects.isNull(user)) {
      user = createNewUser("");
      Node node = nodeHandler.get(user.getNodeId());

      if (Objects.isNull(node)) {
        formatAndSendResponse(session, user, null, "NODE NOT FOUND");
        return;
      }

      formatAndSendResponse(session, user, node, "");
      return;
    }
  }

  private User createNewUser(String userId) {
    User user = User.builder().nodeId("6070147865d3044b6a511532").build();
    if (!(userId.isEmpty())) {
      user.setId(userId);
    }
    return userHandler.createNewUser(user);
  }

  private void formatAndSendResponse(WebSocketSession session, User user, Node node, String error)
      throws IOException {
    Response response =
        Response.builder()
            .userId(user.getId())
            .node(node)
            .error((Objects.isNull(error)) ? "" : error)
            .build();

    session.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(response)));
  }
}
