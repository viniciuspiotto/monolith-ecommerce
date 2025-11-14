package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.in.messengers.sqs;

import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.UpdateZipKeyCommand;
import edu.unifalmg.monolithecommerce.catalog.application.port.in.UpdateModelZipKeyPort;
import edu.unifalmg.monolithecommerce.shared.infraestructure.exception.ResourceNotFoundException;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Log4j2
@RequiredArgsConstructor
public class OnZipCompletedListener {

    private final UpdateModelZipKeyPort updateModelZipKeyPort;

    @SqsListener("${aws.queues.model-zip-completed}")
    public void onZipCompleted(@Payload ZipNotificationPayload payload) {
        log.info("Received zip completed notification for model ID: {}", payload.modelId());

        try {
            UpdateZipKeyCommand cmd = new UpdateZipKeyCommand(
                    UUID.fromString(payload.modelId()),
                    payload.zipFileKey()
            );

            updateModelZipKeyPort.execute(cmd);

            log.info("Model {} successfully updated.", payload.modelId());
        } catch (ResourceNotFoundException e) {
            log.warn("Model {} not found. Discarding zip notification. Message: {}", payload.modelId(), e.getMessage());
        } catch (Exception e) {
            log.error("Failed to process zip notification for model {}: {}. Retrying...", payload.modelId(), e.getMessage());
            throw e;
        }

    }
}
