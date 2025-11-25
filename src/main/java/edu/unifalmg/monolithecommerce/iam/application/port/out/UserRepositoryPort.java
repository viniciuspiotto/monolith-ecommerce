package edu.unifalmg.monolithecommerce.iam.application.port.out;

import edu.unifalmg.monolithecommerce.iam.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {
    User save(User user);
    Boolean existsByEmail(String email);
    User findByEmail(String email);
    Optional<User> findById(UUID id);
    Boolean delete (User user);
    Boolean count();
}
