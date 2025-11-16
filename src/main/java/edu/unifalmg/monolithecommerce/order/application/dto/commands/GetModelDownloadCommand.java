package edu.unifalmg.monolithecommerce.order.application.dto.commands;

import java.util.UUID;

public record GetModelDownloadCommand
        (UUID orderId,
         UUID modelId) {
}
