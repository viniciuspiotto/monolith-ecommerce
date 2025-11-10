package edu.unifalmg.monolithecommerce.catalog.application.port.in;

import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.GetDownloadLinkModelCommand;

import java.net.URL;

public interface GetDownloadLinkModelPort {
    URL execute(GetDownloadLinkModelCommand cmd);
}
