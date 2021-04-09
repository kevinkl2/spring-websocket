package us.sphor.websocket.handlers.db;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.sphor.websocket.repository.ChatNodeRepository;
import us.sphor.websocket.repository.entities.Node;

@Component
@AllArgsConstructor
@Slf4j
public class NodeHandler {

  private final ChatNodeRepository chatNodeRepository;

  public Node get(String id) {
    try {
      return chatNodeRepository.findById(id).get();
    } catch (RuntimeException runtimeException) {
      return null;
    }
  }
}
