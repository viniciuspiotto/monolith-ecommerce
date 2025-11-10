package edu.unifalmg.monolithecommerce.catalog.application.usecase;

import edu.unifalmg.monolithecommerce.catalog.application.dto.FileStorageDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.ModelDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.CreateModelCommand;
import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.EditModelCommand;
import edu.unifalmg.monolithecommerce.catalog.application.mapper.ModelMapper;
import edu.unifalmg.monolithecommerce.catalog.application.port.in.EditModelPort;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.FileStoragePort;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.ModelRepositoryPort;
import edu.unifalmg.monolithecommerce.catalog.domain.model.Model;
import edu.unifalmg.monolithecommerce.catalog.domain.model.enums.MeshType;
import edu.unifalmg.monolithecommerce.catalog.domain.model.enums.TextureType;
import edu.unifalmg.monolithecommerce.catalog.domain.model.enums.ThumbnailType;
import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.Mesh;
import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.Texture;
import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.Thumbnail;
import edu.unifalmg.monolithecommerce.shared.infraestructure.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EditModelUseCase implements EditModelPort {

    private final FileStoragePort fileStoragePort;
    private final ModelRepositoryPort modelRepositoryPort;
    private final ModelMapper modelMapper;

    public EditModelUseCase(
            ModelRepositoryPort modelRepositoryPort,
            @Qualifier("s3FileStorageAdapter") FileStoragePort fileStoragePort,
            ModelMapper modelMapper) {
        this.modelRepositoryPort = modelRepositoryPort;
        this.fileStoragePort = fileStoragePort;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public ModelDTO execute(EditModelCommand cmd) {
        Model existingModel = modelRepositoryPort.findById(cmd.id())
                .orElseThrow(() -> new ResourceNotFoundException("Model not found with id: " + cmd.id()));

        if (cmd.title() != null) {
            existingModel.renameModel(cmd.title());
        }
        if (cmd.description() != null) {
            existingModel.changeDescription(cmd.description());
        }
        if (cmd.price() != null) {
            existingModel.changePrice(cmd.price());
        }
        if (cmd.categoryId() != null) {
            existingModel.changeCategory(cmd.categoryId());
        }

        if (cmd.thumbnailFile() != null) {
            updateThumbnail(existingModel, cmd.thumbnailFile());
        }

        if (cmd.meshFilenamesToRemove() != null ||
                (cmd.newMeshFiles() != null && !cmd.newMeshFiles().isEmpty())) {

            updateMeshes(existingModel, cmd.meshFilenamesToRemove(), cmd.newMeshFiles());
        }

        if (cmd.textureFilenamesToRemove() != null ||
                (cmd.newTextureFiles() != null && !cmd.newTextureFiles().isEmpty())) {

            updateTextures(existingModel, cmd.textureFilenamesToRemove(), cmd.newTextureFiles());
        }

        existingModel.notifyModelUpdated();

        Model savedModel = modelRepositoryPort.update(existingModel);

        return modelMapper.toDTO(savedModel);
    }

    private void updateThumbnail(Model existingModel, CreateModelCommand.FileCommand newThumbnailFile) {
        if (existingModel.getThumbnail() != null) {
            fileStoragePort.delete(existingModel.getThumbnail().getFilename());
        }

        FileStorageDTO thumbnailDTO = fileStoragePort.save(
                newThumbnailFile,
                ThumbnailType.ALLOWED_MIMETYPES
        );

        Thumbnail newThumbnail = Thumbnail.create(
                thumbnailDTO.uniqueName(),
                thumbnailDTO.filename(),
                thumbnailDTO.type(),
                ""
        );

        existingModel.changeThumbnail(newThumbnail);
    }

    private void updateMeshes(Model existingModel, List<String> meshFilenamesToRemove, List<CreateModelCommand.FileCommand> newMeshFiles) {
        if (meshFilenamesToRemove != null) {
            List<Mesh> meshesToDelete = existingModel.getMeshes().stream()
                    .filter(mesh -> meshFilenamesToRemove.contains(mesh.getUniqueName()))
                    .toList();

            if (meshesToDelete.isEmpty()) {
                throw new ResourceNotFoundException("Not found meshes to delete");
            }

            for (Mesh mesh : meshesToDelete) {
                existingModel.removeMesh(mesh);
                fileStoragePort.delete(mesh.getUniqueName());
            }
        }

        if (newMeshFiles != null) {
            for (CreateModelCommand.FileCommand meshCommand : newMeshFiles) {
                FileStorageDTO meshDTO = fileStoragePort.save(
                        meshCommand,
                        MeshType.ALLOWED_MIMETYPES
                );
                Mesh newMesh = Mesh.create(
                        meshDTO.uniqueName(),
                        meshDTO.filename(),
                        meshDTO.type()
                );
                existingModel.addMesh(newMesh);
            }
        }
    }

    private void updateTextures(Model existingModel, List<String> textureFilenamesToRemove, List<CreateModelCommand.FileCommand> newTextureFiles) {
        if (textureFilenamesToRemove != null) {
            List<Texture> texturesToDelete = existingModel.getTextures().stream()
                    .filter(texture -> textureFilenamesToRemove.contains(texture.getUniqueName()))
                    .toList();

            if (texturesToDelete.isEmpty()) {
                throw new ResourceNotFoundException("Not found textures to delete");
            }

            for (Texture texture : texturesToDelete) {
                existingModel.removeTexture(texture);
                fileStoragePort.delete(texture.getUniqueName());
            }
        }

        if (newTextureFiles != null ) {
            for (CreateModelCommand.FileCommand textureCommand : newTextureFiles) {
                FileStorageDTO textureDTO = fileStoragePort.save(
                        textureCommand,
                        TextureType.ALLOWED_MIMETYPES
                );
                Texture newTexture = Texture.create(
                        textureDTO.uniqueName(),
                        textureDTO.filename(),
                        textureDTO.type()
                );
                existingModel.addTexture(newTexture);
            }
        }
    }
}
