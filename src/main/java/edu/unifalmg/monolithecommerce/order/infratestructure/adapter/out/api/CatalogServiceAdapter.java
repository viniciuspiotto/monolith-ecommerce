package edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.api;


import edu.unifalmg.monolithecommerce.catalog.infrastructure.api.GetDownloadLinkModelPort;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.api.GetModelNameByIdPort;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.api.ModelId;
import edu.unifalmg.monolithecommerce.order.application.port.out.OrderCatalogServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component("orderCatalogServiceAdapter")
@RequiredArgsConstructor
public class CatalogServiceAdapter implements OrderCatalogServicePort {

    private final GetDownloadLinkModelPort getDownloadLinkModelPort;
    private final GetModelNameByIdPort getModelNameByIdPort;

    @Override
    public URL getDownloadLinkModel (ModelId modelId){ return getDownloadLinkModelPort.execute(modelId); }

    @Override
    public String getModelNameById (ModelId modelId) { return getModelNameByIdPort.execute(modelId); }
}
