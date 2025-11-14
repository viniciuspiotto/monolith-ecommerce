package edu.unifalmg.monolithecommerce.order.application.port.out;

import edu.unifalmg.monolithecommerce.catalog.infrastructure.api.ModelId;

import java.net.URL;

public interface OrderCatalogServicePort {
    URL getDownloadLinkModel (ModelId modelId);
    String getModelNameById (ModelId modelId);
}
