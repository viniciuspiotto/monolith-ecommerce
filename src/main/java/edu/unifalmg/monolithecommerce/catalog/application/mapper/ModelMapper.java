package edu.unifalmg.monolithecommerce.catalog.application.mapper;

import edu.unifalmg.monolithecommerce.catalog.application.dto.ModelDTO;
import edu.unifalmg.monolithecommerce.catalog.domain.model.Model;
import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.Mesh;
import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.Rate;
import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.Texture;
import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.Thumbnail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ModelMapper {

    @Mapping(source = "modelId.id", target = "modelId")
    @Mapping(source = "averageRate.value", target = "averageRate")
    @Mapping(source = "thumbnail", target = "thumbnail")
    @Mapping(source = "meshes", target = "meshes")
    @Mapping(source = "textures", target = "textures")
    ModelDTO toDTO(Model model);

    ModelDTO.FileWithoutURLDTO thumbnailToFileDTO(Thumbnail thumbnail);

    ModelDTO.FileWithoutURLDTO meshToFileDTO(Mesh mesh);

    ModelDTO.FileDTO textureToFileDTO(Texture texture);

    default double rateToDouble(Rate rate) {
        if (rate == null) {
            return 0.0;
        }
        return rate.value();
    }
}
