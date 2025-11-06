package edu.unifalmg.monolithecommerce.iam.application.mapper;

import edu.unifalmg.monolithecommerce.iam.application.DTO.UpdateUserInformationDTO;
import edu.unifalmg.monolithecommerce.iam.application.DTO.UserDTO;
import edu.unifalmg.monolithecommerce.iam.domain.model.User;
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UpdateUserInformationMapper {
    @Mapping(source = "userId.id", target = "userId")
    @Mapping(source = "nationalId.nationalNumber", target = "nationalId")
    UpdateUserInformationDTO toDTO(User user);
    UpdateUserInformationDTO.AddressDTO toAddressDTO(Address address);
}
