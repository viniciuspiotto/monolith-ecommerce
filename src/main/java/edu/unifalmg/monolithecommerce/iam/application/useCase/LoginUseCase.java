package edu.unifalmg.monolithecommerce.iam.application.useCase;

import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.LoginCommand;
import edu.unifalmg.monolithecommerce.iam.application.port.in.LoginPort;
import edu.unifalmg.monolithecommerce.iam.application.port.out.UserRepositoryPort;
import edu.unifalmg.monolithecommerce.iam.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class LoginUseCase implements LoginPort {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public User execute(LoginCommand cmd) {
        log.debug("Attempting login for user: {}", cmd.email());

        User user = userRepository.findByEmail(cmd.email());

        if (!passwordEncoder.matches(cmd.password(), user.getHashedPassword().getHashedPassword())) {
            log.warn("Invalid credentials for user: {}", cmd.email());
            throw new IllegalArgumentException("Invalid credentials.");
        }

        log.info("User {} authenticated successfully.", user.getEmail().getEmail());
        return user;
    }
}
