package edu.unifalmg.monolithecommerce.iam.application.useCase;

import edu.unifalmg.monolithecommerce.iam.application.DTO.UserDTO;
import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.CreateUserCommand;
import edu.unifalmg.monolithecommerce.iam.application.mapper.UserMapper;
import edu.unifalmg.monolithecommerce.iam.application.port.in.CreateUserPort;
import edu.unifalmg.monolithecommerce.iam.application.port.out.RoleRepositoryPort;
import edu.unifalmg.monolithecommerce.iam.application.port.out.UserRepositoryPort;
import edu.unifalmg.monolithecommerce.iam.domain.model.Role;
import edu.unifalmg.monolithecommerce.iam.domain.model.User;
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.Address;
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.Email;
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.HashedPassword;
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.NationalId;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class CreateUserUseCase implements CreateUserPort {

    private final UserRepositoryPort userRepository;
    private final RoleRepositoryPort roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private static final String PASSWORD_STRENGTH_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    @Override
    @Transactional
    public UserDTO execute(CreateUserCommand cmd) {
        log.info("Initiating user creation process for email: {}", cmd.email());

        if (userRepository.existsByEmail(cmd.email())) {
            log.warn("User creation failed: Email {} already exists.", cmd.email());
            throw new IllegalArgumentException("A user with the email " + cmd.email() + " already exists.");
        }

        if (!cmd.password().equals(cmd.confirmPassword())) {
            log.warn("User creation failed for {}: Passwords do not match.", cmd.email());
            throw new IllegalArgumentException("The provided passwords do not match.");
        }

        log.debug("Creating Value Objects for user {}", cmd.email());
        Address address = Address.create(
                cmd.address().country(),
                cmd.address().city(),
                cmd.address().state(),
                cmd.address().zip(),
                cmd.address().street(),
                cmd.address().number(),
                cmd.address().neighborhood(),
                cmd.address().complement()
        );

        NationalId nationalId = NationalId.create(
                cmd.nationalId()
        );

        validatePasswordStrength(cmd.password());

        String encodedPassword = passwordEncoder.encode(cmd.password());
        HashedPassword password = HashedPassword.create(encodedPassword);
        Email email = Email.create(cmd.email());

        log.debug("Fetching 'CUSTOMER' role for new user {}", cmd.email());
        Role role = roleRepository.findByName("CUSTOMER");

        if (role == null) {
            log.error("Critical configuration error: 'CUSTOMER' role not found in database. Cannot create user.");
            throw new IllegalStateException("Default 'CUSTOMER' role configuration is missing.");
        }

        User user = User.create(
                cmd.name(),
                cmd.lastName(),
                email,
                password,
                role,
                address,
                nationalId
        );

        User savedUser = userRepository.save(user);
        log.info("User {} created successfully with ID: {}", savedUser.getEmail().getEmail(), savedUser.getUserId());

        return userMapper.toDTO(savedUser);
    }

    private void validatePasswordStrength(String plaintextPassword) {
        if (!plaintextPassword.matches(PASSWORD_STRENGTH_REGEX)) {
            log.warn("Password created failed, the password does not meet strength requirements.");
            throw new IllegalArgumentException("Password is not strong enough. It must be at least 8 characters long, contain one uppercase letter, one lowercase letter, one number, and one special character.");
        }
        log.debug("Password strength validation passed");
    }
}
