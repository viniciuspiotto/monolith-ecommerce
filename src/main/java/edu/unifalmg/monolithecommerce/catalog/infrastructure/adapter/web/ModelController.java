package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.web;

import edu.unifalmg.monolithecommerce.catalog.application.dto.ModelDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.CreateModelCommand;
import edu.unifalmg.monolithecommerce.catalog.application.dto.request.CreateModelRequest;
import edu.unifalmg.monolithecommerce.catalog.application.mapper.ModelRequestMapper;
import edu.unifalmg.monolithecommerce.catalog.application.port.in.CreateModelPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("models")
@RequiredArgsConstructor
public class ModelController {
    private final CreateModelPort createModelUseCase;
    private final ModelRequestMapper requestMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ModelDTO> createModel(
            @Valid @ModelAttribute CreateModelRequest request
    ) {
        CreateModelCommand command = requestMapper.toCommand(request);

        ModelDTO createdModel = createModelUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdModel);
    }
}
