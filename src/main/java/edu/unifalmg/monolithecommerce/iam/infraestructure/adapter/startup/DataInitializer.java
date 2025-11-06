package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.startup;

import edu.unifalmg.monolithecommerce.iam.application.DTO.RoleDTO;
import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.CreateRoleCommand;
import edu.unifalmg.monolithecommerce.iam.application.port.in.CreateRolePort;
import edu.unifalmg.monolithecommerce.iam.application.port.out.UserRepositoryPort;
import edu.unifalmg.monolithecommerce.iam.domain.model.Role;
import edu.unifalmg.monolithecommerce.iam.domain.model.User;
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.RoleId;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CreateRolePort createRoleUseCase;
    private final UserRepositoryPort userRepositoryPort;

    @Override
    public void run(String... args) throws Exception {

        CreateRoleCommand createCustomerRole = new CreateRoleCommand("CUSTOMER", "Customer in 3D Shop");
        createRoleUseCase.execute(createCustomerRole);

        CreateRoleCommand createArtistRole = new CreateRoleCommand("ARTIST", "Artist in 3D Shop");
        RoleDTO artistRoleDTO = createRoleUseCase.execute(createArtistRole);

        try {
            userRepositoryPort.findByEmail("artist@example.com");
        } catch (RuntimeException e) {
            Role role = Role.rehydrate(new RoleId(artistRoleDTO.roleId()), artistRoleDTO.name(), artistRoleDTO.description());

            User artistUser = User.create(
                    "Art",
                    "Ist",
                    edu.unifalmg.monolithecommerce.iam.domain.model.vo.Email.create("artist@example.com"),
                    edu.unifalmg.monolithecommerce.iam.domain.model.vo.Password.create("StrongP@ss123"),
                    role,
                    edu.unifalmg.monolithecommerce.iam.domain.model.vo.Address.create(
                            "Country", "City", "State", "12345", "Street", 1, "Neighborhood", "Complement"
                    ),
                    edu.unifalmg.monolithecommerce.iam.domain.model.vo.NationalId.create("11144477735")
            );
            userRepositoryPort.save(artistUser);
        }

    }
}

