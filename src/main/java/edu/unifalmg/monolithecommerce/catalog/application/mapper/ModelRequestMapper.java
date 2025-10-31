package edu.unifalmg.monolithecommerce.catalog.application.mapper;

import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.CreateModelCommand;
import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.EditModelCommand;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.in.dto.requests.CreateModelRequest;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.in.dto.requests.EditModelRequest;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ModelRequestMapper {

    @Mapping(source = "price", target = "price")
    @Mapping(source = "thumbnailFile", target = "thumbnailFile")
    @Mapping(source = "meshFiles", target = "meshFiles")
    @Mapping(source = "textureFiles", target = "textureFiles")
    CreateModelCommand toCommand(CreateModelRequest request);

    @Mapping(source = "request.title", target = "title")
    @Mapping(source = "request.description", target = "description")
    @Mapping(source = "request.price", target = "price")
    @Mapping(source = "request.categoryId", target = "categoryId")
    @Mapping(source = "request.thumbnailFile", target = "thumbnailFile")
    @Mapping(source = "request.meshFilenamesToRemove", target = "meshFilenamesToRemove")
    @Mapping(source = "request.newMeshFiles", target = "newMeshFiles")
    @Mapping(source = "request.textureFilenamesToRemove", target = "textureFilenamesToRemove")
    @Mapping(source = "request.newTextureFiles", target = "newTextureFiles")
    EditModelCommand toCommand(EditModelRequest request, UUID id);

    default Money bigDecimalToMoney(BigDecimal value) {
        if (value == null) {
            return null;
        }
        return new Money(value);
    }

    default CreateModelCommand.FileCommand multipartToFileCommand(List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            return null;
        }
        return files.stream()
                .filter(file -> file != null && !file.isEmpty())
                .map(this::multipartToFileCommand)
                .findFirst().orElse(null);
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

    default List<CreateModelCommand.FileCommand> mapMultipartFileListToFileCommandList(List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            return Collections.emptyList();
        }
        return files.stream()
                .filter(file -> file != null && !file.isEmpty())
                .map(this::multipartToFileCommand)
                .collect(Collectors.toList());
    }
}
