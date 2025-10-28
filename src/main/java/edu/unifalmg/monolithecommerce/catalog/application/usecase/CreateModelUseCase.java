package edu.unifalmg.monolithecommerce.catalog.application.usecase;

import edu.unifalmg.monolithecommerce.catalog.application.dto.CreateModelCommand;
import edu.unifalmg.monolithecommerce.catalog.application.dto.FileStorageResultDTO;
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

@Service
@RequiredArgsConstructor
public class CreateModelUseCase implements CreateModelPort {

    private final ModelRepositoryPort modelRepositoryPort;
    private final FileStoragePort fileStoragePort;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ModelDTO execute(CreateModelCommand cmd) {
        FileStorageResultDTO thumbnailDTO = fileStoragePort.save(
                cmd.thumbnail(),
                ThumbnailType.ALLOWED_MIMETYPES
        );

        FileStorageResultDTO meshDTO = fileStoragePort.save(
                cmd.meshFile(),
                MeshType.ALLOWED_MIMETYPES
        );

        FileStorageResultDTO textureDTO = fileStoragePort.save(
                cmd.textureFile(),
                TextureType.ALLOWED_MIMETYPES
        );

        Thumbnail thumbnail = Thumbnail.create(
                thumbnailDTO.publicUrl(),
                thumbnailDTO.originalFilename(),
                thumbnailDTO.mimeType()
        );

        Mesh mesh = Mesh.create(
                meshDTO.publicUrl(),
                meshDTO.originalFilename(),
                meshDTO.mimeType()
        );

        Texture texture = Texture.create(
                textureDTO.publicUrl(),
                textureDTO.originalFilename(),
                textureDTO.mimeType()
        );

        Model newModel = Model.create(
                cmd.title(),
                cmd.description(),
                thumbnail,
                cmd.price(),
                cmd.categoryId()
        );

        newModel.addMesh(mesh);
        newModel.addTexture(texture);

        Model savedModel = modelRepositoryPort.save(newModel);

        return modelMapper.toDTO(savedModel);
    }
}
