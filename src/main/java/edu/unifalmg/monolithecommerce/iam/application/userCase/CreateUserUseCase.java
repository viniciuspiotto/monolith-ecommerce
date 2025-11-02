package edu.unifalmg.monolithecommerce.iam.application.userCase;

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
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.NationalId;
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.Password;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase implements CreateUserPort {

    private final UserRepositoryPort userRepository;
    private final RoleRepositoryPort roleRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDTO execute(CreateUserCommand cmd){

        if(userRepository.existsByEmail(cmd.email())){
            throw new IllegalArgumentException("A user with the email already exists.");
        }

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

        if(!cmd.password().equals(cmd.confirmPassword())){
            throw new IllegalArgumentException("The passwords don't match");
        }

        Password password = Password.create(cmd.password());
        Email email = Email.create(cmd.email());

        Role role = roleRepository.findByName("CUSTOMER");

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
        return userMapper.toDTO(savedUser);

    }


}
