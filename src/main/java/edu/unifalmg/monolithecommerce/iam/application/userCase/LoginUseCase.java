package edu.unifalmg.monolithecommerce.iam.application.userCase;

import edu.unifalmg.monolithecommerce.iam.application.DTO.LoginDTO;
import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.LoginCommand;
import edu.unifalmg.monolithecommerce.iam.application.port.in.LoginPort;
import edu.unifalmg.monolithecommerce.iam.application.port.out.TokenUtilsPort;
import edu.unifalmg.monolithecommerce.iam.application.port.out.UserRepositoryPort;
import edu.unifalmg.monolithecommerce.iam.domain.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCase implements LoginPort {

    private final UserRepositoryPort userRepository;
    private final TokenUtilsPort tokenUtils;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public LoginDTO execute(LoginCommand cmd){

        User user = userRepository.findByEmail(cmd.email());

        if (!passwordEncoder.matches(cmd.password(), user.getPassword().getPassword())) {
            throw new IllegalArgumentException("Invalid credentials.");
        }

        String token = tokenUtils.generateToken(user);
        return new LoginDTO(token, user.getEmail().getEmail(), user.getPassword().getPassword());

    }

}
