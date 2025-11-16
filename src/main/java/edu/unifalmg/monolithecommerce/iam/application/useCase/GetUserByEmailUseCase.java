package edu.unifalmg.monolithecommerce.iam.application.useCase;


import edu.unifalmg.monolithecommerce.iam.application.DTO.UserDTO;
import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.GetUserByEmailCommand;
import edu.unifalmg.monolithecommerce.iam.application.mapper.UserMapper;
import edu.unifalmg.monolithecommerce.iam.application.port.in.GetUserByEmailPort;
import edu.unifalmg.monolithecommerce.iam.application.port.out.UserRepositoryPort;
import edu.unifalmg.monolithecommerce.iam.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetUserByEmailUseCase implements GetUserByEmailPort {

    private final UserRepositoryPort useRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UserDTO execute (GetUserByEmailCommand cmd){
        User userFound = useRepository.findByEmail(cmd.email());
        return userMapper.toDTO(userFound);
    }

}
