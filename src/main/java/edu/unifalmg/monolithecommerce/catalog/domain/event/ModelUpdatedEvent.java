package edu.unifalmg.monolithecommerce.catalog.domain.event;

import edu.unifalmg.monolithecommerce.catalog.domain.model.Model;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ModelUpdatedEvent {
    UUID modelId;

    public ModelUpdatedEvent(Model model) {
        this.modelId = model.getModelId().id();
    }
}
