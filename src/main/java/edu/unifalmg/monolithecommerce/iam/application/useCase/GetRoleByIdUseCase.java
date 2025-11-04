package edu.unifalmg.monolithecommerce.iam.application.useCase;

import edu.unifalmg.monolithecommerce.iam.application.DTO.RoleDTO;
import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.GetRoleByIdCommand;
import edu.unifalmg.monolithecommerce.iam.application.mapper.RoleMapper;
import edu.unifalmg.monolithecommerce.iam.application.port.in.GetRoleByIdPort;
import edu.unifalmg.monolithecommerce.iam.application.port.out.RoleRepositoryPort;
import edu.unifalmg.monolithecommerce.iam.domain.model.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetRoleByIdUseCase implements GetRoleByIdPort {
    private final RoleRepositoryPort roleRepository;
    private final RoleMapper roleMapper;

    @Override
    @Transactional
    public RoleDTO execute (GetRoleByIdCommand cmd){
        Role roleFound = roleRepository.findById(cmd.id());
        return roleMapper.toDTO(roleFound);
    }
}
