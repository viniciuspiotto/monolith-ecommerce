package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.messegers.sqs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.unifalmg.monolithecommerce.catalog.application.dto.ZipRequestPayload;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.ModelNotifierPort;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SqsException;

@Component
@Log4j2
public class SQSZipNotifierAdapter implements ModelNotifierPort {

    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;
    private final String queueUrl;

    public SQSZipNotifierAdapter(
            SqsClient sqsClient,
            ObjectMapper objectMapper,
            @Value("${aws.queueUrl}") String queueUrl
    ) {
        this.sqsClient = sqsClient;
        this.objectMapper = objectMapper;
        this.queueUrl = queueUrl;
    }

    public void notifyModelReadyForZip(ZipRequestPayload payload) {
        try {
            String messageBody = objectMapper.writeValueAsString(payload);

            SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(messageBody)
                    .build();

            sqsClient.sendMessage(sendMessageRequest);

            log.info("Successfully sent SQS message for modelId: {}", payload.modelId());


        } catch (JsonProcessingException e) {
            log.error("Failed to serialize message for SQS. Payload: {}", payload, e);
            throw new RuntimeException("Failed to serialize message for SQS", e);
        } catch (SqsException e) {
            log.error("Failed to send message to SQS. URL: {}, Payload: {}", queueUrl, payload, e);
            throw new RuntimeException("Failed to send message to SQS", e);
        }
    }
}
