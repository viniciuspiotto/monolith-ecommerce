package edu.unifalmg.monolithecommerce.catalog.application.mapper;

import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.CreateModelCommand;
import edu.unifalmg.monolithecommerce.catalog.application.dto.request.CreateModelRequest;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface ModelRequestMapper {

    @Mapping(source = "price", target = "price")
    @Mapping(source = "thumbnailFile", target = "thumbnailFile")
    @Mapping(source = "meshFiles", target = "meshFile")
    @Mapping(source = "textureFiles", target = "textureFile")
    CreateModelCommand toCommand(CreateModelRequest request);

    default Money bigDecimalToMoney(BigDecimal value) {
        if (value == null) {
            return null;
        }
        return new Money(value);
    }

    default CreateModelCommand.FileCommand multipartToFileCommand(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            return new CreateModelCommand.FileCommand(
                    file.getOriginalFilename(),
                    file.getInputStream()
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file input stream: " + file.getOriginalFilename(), e);
        }
    }
}
