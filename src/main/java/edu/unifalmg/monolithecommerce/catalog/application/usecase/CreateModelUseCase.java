package edu.unifalmg.monolithecommerce.catalog.application.usecase;

import edu.unifalmg.monolithecommerce.catalog.application.dto.FileStorageDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.ModelDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.ZipRequestPayload;
import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.CreateModelCommand;
import edu.unifalmg.monolithecommerce.catalog.application.mapper.ModelMapper;
import edu.unifalmg.monolithecommerce.catalog.application.port.in.CreateModelPort;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.FileStoragePort;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.ModelNotifierPort;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.ModelRepositoryPort;
import edu.unifalmg.monolithecommerce.catalog.domain.model.Model;
import edu.unifalmg.monolithecommerce.catalog.domain.model.enums.MeshType;
import edu.unifalmg.monolithecommerce.catalog.domain.model.enums.TextureType;
import edu.unifalmg.monolithecommerce.catalog.domain.model.enums.ThumbnailType;
import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.Mesh;
import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.Texture;
import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.Thumbnail;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Log4j2
public class CreateModelUseCase implements CreateModelPort {

    private final FileStoragePort fileStoragePort;
    private final ModelMapper modelMapper;
    private final ModelRepositoryPort modelRepositoryPort;
    private final ModelNotifierPort modelNotifierPort;

    public CreateModelUseCase(
            ModelRepositoryPort modelRepositoryPort,
            @Qualifier("s3FileStorageAdapter") FileStoragePort fileStoragePort,
            ModelMapper modelMapper,
            ModelNotifierPort modelNotifierPort) {
        this.modelRepositoryPort = modelRepositoryPort;
        this.fileStoragePort = fileStoragePort;
        this.modelMapper = modelMapper;
        this.modelNotifierPort = modelNotifierPort;
    }

    @Override
    @Transactional
    public ModelDTO execute(CreateModelCommand cmd) {
        Optional<Model> optionalModel = modelRepositoryPort.findByTitle(cmd.title());

        if (optionalModel.isPresent()) {
            log.warn("Model with title '{}' already exists", cmd.title());
            throw new IllegalArgumentException("Model with title " + cmd.title() + " already exists");
        }

        FileStorageDTO thumbnailDTO = fileStoragePort.save(
                cmd.thumbnailFile(),
                ThumbnailType.ALLOWED_MIMETYPES
        );

        Map<String, ZipRequestPayload.ModelTypeFile> s3FileMap = new HashMap<>();

        List<FileStorageDTO> meshesDTO = new ArrayList<>();
        for (CreateModelCommand.FileCommand meshCommand : cmd.meshFiles()) {
            FileStorageDTO storedMesh = fileStoragePort.save(meshCommand, MeshType.ALLOWED_MIMETYPES);
            meshesDTO.add(storedMesh);
            s3FileMap.put(storedMesh.uniqueName(), ZipRequestPayload.ModelTypeFile.MESH);
        }

        List<FileStorageDTO> texturesDTO = new ArrayList<>();
        for (CreateModelCommand.FileCommand textureCommand : cmd.textureFiles()) {
            FileStorageDTO storedTexture = fileStoragePort.save(textureCommand, TextureType.ALLOWED_MIMETYPES);
            texturesDTO.add(storedTexture);
            s3FileMap.put(storedTexture.uniqueName(), ZipRequestPayload.ModelTypeFile.TEXTURE);
        }

        Thumbnail thumbnail = Thumbnail.create(
                thumbnailDTO.uniqueName(),
                thumbnailDTO.filename(),
                "",
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
                    meshDTO.filename(),
                    meshDTO.type()
            );
            newModel.addMesh(mesh);
        }

        for (FileStorageDTO textureDTO : texturesDTO) {
            Texture texture = Texture.create(
                    textureDTO.uniqueName(),
                    textureDTO.filename(),
                    textureDTO.type()
            );
            newModel.addTexture(texture);
        }

        Model savedModel = modelRepositoryPort.create(newModel);

        ZipRequestPayload payload = new ZipRequestPayload(savedModel.getModelId().id(), s3FileMap);
        modelNotifierPort.notifyModelReadyForZip(payload);

        return modelMapper.toDTO(savedModel);
    }
}
