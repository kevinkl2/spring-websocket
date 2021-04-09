package us.sphor.websocket.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import us.sphor.websocket.repository.entities.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {}
