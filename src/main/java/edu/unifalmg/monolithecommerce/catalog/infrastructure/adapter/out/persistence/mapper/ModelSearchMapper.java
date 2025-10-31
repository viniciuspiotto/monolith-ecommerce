package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.mapper;

import edu.unifalmg.monolithecommerce.catalog.domain.model.Model;
import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.ModelId;
import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.Rate;
import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.Thumbnail;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.entities.ModelSearchDocument;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface ModelSearchMapper {

    @Mapping(source = "modelId", target = "id", qualifiedByName = "modelIdToString")
    @Mapping(source = "price", target = "priceAmount", qualifiedByName = "moneyToAmount")
    @Mapping(source = "averageRate", target = "averageRate", qualifiedByName = "rateToDouble")
    @Mapping(source = "thumbnail", target = "thumbnailUrl", qualifiedByName = "thumbnailToUrl")
    ModelSearchDocument toDocument(Model model);

    @Named("modelIdToString")
    default String modelIdToString(ModelId modelId) {
        return modelId != null ? modelId.id().toString() : null;
    }

    @Named("moneyToAmount")
    default BigDecimal moneyToAmount(Money money) {
        return money != null ? money.getAmount() : null;
    }

    @Named("moneyToCurrency")
    default String moneyToCurrency(Money money) {
        return money != null ? money.getCurrency().toString() : null;
    }

    @Named("rateToDouble")
    default Double rateToDouble(Rate rate) {
        return rate != null ? rate.value() : null;
    }

    @Named("thumbnailToUrl")
    default String thumbnailToUrl(Thumbnail thumbnail) {
        return thumbnail != null ? thumbnail.getUrl(): null;
    }
}