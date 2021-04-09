package us.sphor.websocket.handlers.db;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.sphor.websocket.repository.UserRepository;
import us.sphor.websocket.repository.entities.User;

@Component
@AllArgsConstructor
@Slf4j
public class UserHandler {

  private final UserRepository userRepository;

  public User save(User user) {
    try {
      return userRepository.save(user);
    } catch (RuntimeException runtimeException) {
      return null;
    }
  }

  public User get(String userId) {
    try {
      return userRepository.findById(userId).get();
    } catch (RuntimeException runtimeException) {
      return null;
    }
  }
}
