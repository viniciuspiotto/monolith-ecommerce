package edu.unifalmg.monolithecommerce.iam.application.useCase;

import edu.unifalmg.monolithecommerce.iam.application.DTO.UpdateUserInformationDTO;
import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.UpdateUserInformationCommand;
import edu.unifalmg.monolithecommerce.iam.application.mapper.UpdateUserInformationMapper;
import edu.unifalmg.monolithecommerce.iam.application.port.in.UpdateUserInformationPort;
import edu.unifalmg.monolithecommerce.iam.application.port.out.TokenUtilsPort;
import edu.unifalmg.monolithecommerce.iam.application.port.out.UserRepositoryPort;
import edu.unifalmg.monolithecommerce.iam.domain.model.User;
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.NationalId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UpdateUserInformationUseCase implements UpdateUserInformationPort {

    private final UserRepositoryPort userRepository;
    private final UpdateUserInformationMapper updateUserInformationMapper;
    private final TokenUtilsPort tokenUtils;

    @Override
    @Transactional
    public UpdateUserInformationDTO execute(UpdateUserInformationCommand cmd){

        String email = tokenUtils.extractEmail(cmd.token());
        User user = userRepository.findByEmail(email);

        if(cmd.address() != null){

            if(cmd.address().city() != null){
                user.getAddress().updateCity(cmd.address().city());
            }

            if(cmd.address().country() != null){
                user.getAddress().updateCountry(cmd.address().country());
            }

            if(cmd.address().neighborhood() != null){
                user.getAddress().updateNeighborhood(cmd.address().neighborhood());
            }

            if(cmd.address().number() != null){
                user.getAddress().updateNumber(cmd.address().number());
            }

            if(cmd.address().complement() != null){
                user.getAddress().updateComplement(cmd.address().complement());
            }

            if(cmd.address().zip() != null){
                user.getAddress().updateZip(cmd.address().zip());
            }

            if(cmd.address().state() != null){
                user.getAddress().updateState(cmd.address().state());
            }

        }

        if(cmd.name() != null){
            user.updateName(cmd.name());
        }

        if(cmd.lastName() != null){
            user.updateLastName(cmd.lastName());
        }

        if(cmd.nationalId() != null){
            NationalId nationalId = NationalId.create(cmd.nationalId());
            user.updateNationalId(nationalId);
        }

        User userUpdated = userRepository.update(user);
        return updateUserInformationMapper.toDTO(userUpdated);

    }
}
