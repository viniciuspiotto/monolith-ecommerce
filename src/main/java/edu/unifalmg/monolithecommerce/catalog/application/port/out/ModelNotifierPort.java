package edu.unifalmg.monolithecommerce.catalog.application.port.out;

import edu.unifalmg.monolithecommerce.catalog.application.dto.ZipRequestPayload;

public interface ModelNotifierPort {
    public void notifyModelReadyForZip(ZipRequestPayload payload);
}
