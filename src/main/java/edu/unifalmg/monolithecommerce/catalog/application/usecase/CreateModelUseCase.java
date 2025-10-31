package edu.unifalmg.monolithecommerce.catalog.application.usecase;

import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.CreateModelCommand;
import edu.unifalmg.monolithecommerce.catalog.application.dto.FileStorageDTO;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        FileStorageDTO thumbnailDTO = fileStoragePort.save(
                cmd.thumbnailFile(),
                ThumbnailType.ALLOWED_MIMETYPES
        );

        List<FileStorageDTO> meshesDTO = new ArrayList<>();
        for (CreateModelCommand.FileCommand meshCommand : cmd.meshFiles()) {
            meshesDTO.add(fileStoragePort.save(
                    meshCommand,
                    MeshType.ALLOWED_MIMETYPES
            ));
        }

        List<FileStorageDTO> texturesDTO = new ArrayList<>();
        for (CreateModelCommand.FileCommand textureCommand : cmd.textureFiles()) {
            texturesDTO.add(fileStoragePort.save(
                    textureCommand,
                    TextureType.ALLOWED_MIMETYPES
            ));
        }

        Thumbnail thumbnail = Thumbnail.create(
                thumbnailDTO.uniqueName(),
                thumbnailDTO.url(),
                thumbnailDTO.filename(),
                thumbnailDTO.type()
        );


        Model newModel = Model.create(
                cmd.title(),
                cmd.description(),
                thumbnail,
                cmd.price(),
                cmd.categoryId()
        );

        for (FileStorageDTO meshDTO : meshesDTO) {
            Mesh mesh = Mesh.create(
                    meshDTO.uniqueName(),
                    meshDTO.url(),
                    meshDTO.filename(),
                    meshDTO.type()
            );
            newModel.addMesh(mesh);
        }

        for (FileStorageDTO textureDTO : texturesDTO) {
            Texture texture = Texture.create(
                    textureDTO.uniqueName(),
                    textureDTO.url(),
                    textureDTO.filename(),
                    textureDTO.type()
            );
            newModel.addTexture(texture);
        }

        Model savedModel = modelRepositoryPort.create(newModel);

        return modelMapper.toDTO(savedModel);
    }
}
