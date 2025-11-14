package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.in.messengers.sqs;

public record ZipNotificationPayload(
        String modelId,
        String zipFileKey
) {
}
