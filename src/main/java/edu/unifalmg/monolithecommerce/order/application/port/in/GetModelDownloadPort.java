package edu.unifalmg.monolithecommerce.order.application.port.in;

import edu.unifalmg.monolithecommerce.order.application.dto.commands.GetModelDownloadCommand;

import java.net.URL;

public interface GetModelDownloadPort {
    URL execute(GetModelDownloadCommand cmd);
}
