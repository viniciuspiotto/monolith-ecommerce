package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.startup;

import edu.unifalmg.monolithecommerce.iam.application.DTO.RoleDTO;
import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.CreateRoleCommand;
import edu.unifalmg.monolithecommerce.iam.application.port.in.CreateRolePort;
import edu.unifalmg.monolithecommerce.iam.application.port.out.UserRepositoryPort;
import edu.unifalmg.monolithecommerce.iam.domain.model.Role;
import edu.unifalmg.monolithecommerce.iam.domain.model.User;
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.Email;
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.HashedPassword;
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.RoleId;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IAMInitializer implements CommandLineRunner {

    private final CreateRolePort createRoleUseCase;
    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (UserRepositoryPort.count() > 0) {
            return;
        }

        CreateRoleCommand createCustomerRole = new CreateRoleCommand("CUSTOMER", "Customer in 3D Shop");
        createRoleUseCase.execute(createCustomerRole);

        CreateRoleCommand createArtistRole = new CreateRoleCommand("ARTIST", "Artist in 3D Shop");
        RoleDTO artistRoleDTO = createRoleUseCase.execute(createArtistRole);

        try {
            userRepositoryPort.findByEmail("artist@example.com");
        } catch (RuntimeException e) {
            Role role = Role.rehydrate(new RoleId(artistRoleDTO.roleId()), artistRoleDTO.name(), artistRoleDTO.description());

            String plainTextPassword = "Strong@ss123";
            String hashedPassword = passwordEncoder.encode(plainTextPassword);

            User artistUser = User.create(
                    "Art",
                    "Ist",
                    Email.create("artist@example.com"),
                    HashedPassword.create(hashedPassword),
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

