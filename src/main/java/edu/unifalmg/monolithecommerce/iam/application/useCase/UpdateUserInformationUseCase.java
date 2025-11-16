package edu.unifalmg.monolithecommerce.iam.application.useCase;

import edu.unifalmg.monolithecommerce.iam.application.DTO.UpdateUserInformationDTO;
import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.UpdateUserInformationCommand;
import edu.unifalmg.monolithecommerce.iam.application.mapper.UpdateUserInformationMapper;
import edu.unifalmg.monolithecommerce.iam.application.port.in.UpdateUserInformationPort;
import edu.unifalmg.monolithecommerce.iam.application.port.out.UserRepositoryPort;
import edu.unifalmg.monolithecommerce.iam.domain.model.User;
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.Address;
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.NationalId;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@AllArgsConstructor
public class UpdateUserInformationUseCase implements UpdateUserInformationPort {

    private final UserRepositoryPort userRepository;
    private final UpdateUserInformationMapper updateUserInformationMapper;

    @Override
    @Transactional
    public UpdateUserInformationDTO execute(UpdateUserInformationCommand cmd) {
        String email = cmd.email();
        log.info("Initiating information update for user: {}", email);

        User user = userRepository.findByEmail(email);

        if (cmd.address() != null) {
            Address newAddress = createUpdatedAddress(cmd.address(), user.getAddress());

            user.updateAddress(newAddress);
            log.debug("Address updated for user: {}", email);
        }

        if (cmd.name() != null) {
            user.updateName(cmd.name());
        }

        if (cmd.lastName() != null) {
            user.updateLastName(cmd.lastName());
        }

        if (cmd.nationalId() != null) {
            NationalId nationalId = NationalId.create(cmd.nationalId());
            user.updateNationalId(nationalId);
        }

        User userUpdated = userRepository.save(user);
        log.info("User information updated successfully for: {}", email);

        return updateUserInformationMapper.toDTO(userUpdated);
    }

    private Address createUpdatedAddress(UpdateUserInformationCommand.AddressCommand dto, Address current) {
        if (current == null) {
            return Address.create(
                    dto.country(), dto.city(), dto.state(), dto.zip(),
                    dto.street(), dto.number(), dto.neighborhood(), dto.complement()
            );
        }

        return Address.rehydrate(
                dto.country() != null ? dto.country() : current.getCountry(),
                dto.city() != null ? dto.city() : current.getCity(),
                dto.state() != null ? dto.state() : current.getState(),
                dto.zip() != null ? dto.zip() : current.getZip(),
                dto.street() != null ? dto.street() : current.getStreet(),
                dto.number() != null ? dto.number() : current.getNumber(),
                dto.neighborhood() != null ? dto.neighborhood() : current.getNeighborhood(),
                dto.complement() != null ? dto.complement() : current.getComplement()
        );
    }
}
