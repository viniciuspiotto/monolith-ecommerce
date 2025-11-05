package edu.unifalmg.monolithecommerce.iam.application.useCase;

import edu.unifalmg.monolithecommerce.iam.application.DTO.RoleDTO;
import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.CreateRoleCommand;
import edu.unifalmg.monolithecommerce.iam.application.mapper.RoleMapper;
import edu.unifalmg.monolithecommerce.iam.application.port.in.CreateRolePort;
import edu.unifalmg.monolithecommerce.iam.application.port.out.RoleRepositoryPort;
import edu.unifalmg.monolithecommerce.iam.domain.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateRoleUseCase implements CreateRolePort {

    private final RoleRepositoryPort roleRepository;
    private final RoleMapper roleMapper;

    @Override
    @Transactional
    public RoleDTO execute(CreateRoleCommand cmd){
        Boolean isExistRole = roleRepository.existsByName(cmd.name());
        if (isExistRole) {
            throw new IllegalArgumentException("A role with the name already exists.");
        }
        Role role = Role.create(
                cmd.name(),
                cmd.description()
        );
        Role savedRole = roleRepository.save(role);
        return roleMapper.toDTO(savedRole);
    }

}
