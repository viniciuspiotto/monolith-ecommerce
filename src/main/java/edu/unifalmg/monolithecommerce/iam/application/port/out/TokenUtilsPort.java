package edu.unifalmg.monolithecommerce.iam.application.port.out;

import edu.unifalmg.monolithecommerce.iam.domain.model.User;

public interface TokenUtilsPort {
    String generateToken(User user);
    String extractEmail(String token);
}
