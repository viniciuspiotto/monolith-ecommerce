package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.mapper;

import edu.unifalmg.monolithecommerce.catalog.application.dto.CreateModelCommand;
import org.mapstruct.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Mapper
public class FileCommandMapper {
    public static List<CreateModelCommand.FileCommand> toFileCommands(List<MultipartFile> files) {
        return files.stream().map(file -> {
            try {
                return new CreateModelCommand.FileCommand(file.getOriginalFilename(), file.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException("Error in Process the file:", e);
            }
        }).toList();
    }
}
