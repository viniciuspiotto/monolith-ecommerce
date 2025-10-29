package edu.unifalmg.monolithecommerce.catalog.application.usecase;

import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.CreateModelCommand;
import edu.unifalmg.monolithecommerce.catalog.application.dto.response.FileStorageResponse;
import edu.unifalmg.monolithecommerce.catalog.application.dto.ModelDTO;
import edu.unifalmg.monolithecommerce.catalog.application.mapper.ModelMapper;
import edu.unifalmg.monolithecommerce.catalog.application.port.in.CreateModelPort;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.FileStoragePort;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.ModelRepositoryPort;
import edu.unifalmg.monolithecommerce.catalog.domain.model.Model;
import edu.unifalmg.monolithecommerce.catalog.domain.model.enums.MeshType;
import edu.unifalmg.monolithecommerce.catalog.domain.model.enums.TextureType;
import edu.unifalmg.monolithecommerce.catalog.domain.model.enums.ThumbnailType;
import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.Mesh;
import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.Texture;
import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.Thumbnail;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateModelUseCase implements CreateModelPort {

    private final ModelRepositoryPort modelRepositoryPort;
    private final FileStoragePort fileStoragePort;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ModelDTO execute(CreateModelCommand cmd) {
        FileStorageResponse thumbnailDTO = fileStoragePort.save(
                cmd.thumbnailFile(),
                ThumbnailType.ALLOWED_MIMETYPES
        );

        List<FileStorageResponse> meshesDTO = new ArrayList<>();
        for (CreateModelCommand.FileCommand meshCommand : cmd.meshFile()) {
            meshesDTO.add(fileStoragePort.save(
                    meshCommand,
                    MeshType.ALLOWED_MIMETYPES
            ));
        }

        List<FileStorageResponse> texturesDTO = new ArrayList<>();
        for (CreateModelCommand.FileCommand textureCommand : cmd.textureFile()) {
            texturesDTO.add(fileStoragePort.save(
                    textureCommand,
                    TextureType.ALLOWED_MIMETYPES
            ));
        }

        Thumbnail thumbnail = Thumbnail.create(
                thumbnailDTO.publicUrl(),
                thumbnailDTO.originalFilename(),
                thumbnailDTO.mimeType()
        );


        Model newModel = Model.create(
                cmd.title(),
                cmd.description(),
                thumbnail,
                cmd.price(),
                cmd.categoryId()
        );

        for (FileStorageResponse meshDTO : meshesDTO) {
            Mesh mesh = Mesh.create(
                    meshDTO.publicUrl(),
                    meshDTO.originalFilename(),
                    meshDTO.mimeType()
            );
            newModel.addMesh(mesh);
        }

        for (FileStorageResponse textureDTO : texturesDTO) {
            Texture texture = Texture.create(
                    textureDTO.publicUrl(),
                    textureDTO.originalFilename(),
                    textureDTO.mimeType()
            );
            newModel.addTexture(texture);
        }

        Model savedModel = modelRepositoryPort.save(newModel);

        return modelMapper.toDTO(savedModel);
    }
}
