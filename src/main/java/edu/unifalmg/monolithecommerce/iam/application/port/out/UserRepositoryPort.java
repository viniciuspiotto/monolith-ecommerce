package edu.unifalmg.monolithecommerce.iam.application.port.out;

import edu.unifalmg.monolithecommerce.iam.domain.model.User;

import java.util.UUID;

public interface UserRepositoryPort {
    User save(User user);
    Boolean existsByEmail(String email);
    User findByEmail(String email);
    User update(User user);
    User updatePassword(User user);
    Boolean delete (User user);
}
