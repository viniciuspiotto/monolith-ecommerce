package edu.unifalmg.monolithecommerce.iam.application.port.out;

import edu.unifalmg.monolithecommerce.iam.domain.model.User;

public interface UserRepositoryPort {
    User save(User user);
    Boolean existsByEmail(String email);
    User findByEmail(String email);
    Boolean delete (User user);
}
