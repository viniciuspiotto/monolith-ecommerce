package edu.unifalmg.monolithecommerce.catalog.infrastructure.api;

import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.GetDownloadLinkModelCommand;

import java.net.URL;

public interface GetDownloadLinkModelPort {
    URL execute(ModelId modelId);
}
