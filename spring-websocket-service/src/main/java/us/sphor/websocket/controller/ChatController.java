package us.sphor.websocket.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import us.sphor.websocket.repository.ChatNodeRepository;
import us.sphor.websocket.repository.entities.Node;

@RestController
@RequestMapping(path = "/v1/chat/admin", produces = "application/json")
@AllArgsConstructor
@Slf4j
public class ChatController {
  private final ChatNodeRepository chatNodeRepository;

  @PostMapping()
  @ResponseStatus(code = HttpStatus.CREATED)
  public Node saveNode(@RequestBody Node node) {

    Node queryResult = Node.builder().build();

    try {
      queryResult = chatNodeRepository.save(node);
    } catch (RuntimeException runtimeException) {
      log.error("saveNode ERROR: ", runtimeException.toString());
    }

    return queryResult;
  }
}
