package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.in.controllers;

import edu.unifalmg.monolithecommerce.catalog.application.dto.ModelDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.CreateModelCommand;
import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.EditModelCommand;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.in.dto.requests.CreateModelRequest;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.in.dto.requests.EditModelRequest;
import edu.unifalmg.monolithecommerce.catalog.application.mapper.ModelRequestMapper;
import edu.unifalmg.monolithecommerce.catalog.application.port.in.CreateModelPort;
import edu.unifalmg.monolithecommerce.catalog.application.port.in.EditModelPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("models")
@RequiredArgsConstructor
public class ModelController {
    private final CreateModelPort createModelUseCase;
    private final EditModelPort editModelUseCase;
    private final ModelRequestMapper requestMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ModelDTO> createModel(
            @Valid @ModelAttribute CreateModelRequest request
    ) {
        CreateModelCommand command = requestMapper.toCommand(request);

        ModelDTO createdModel = createModelUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdModel);
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ModelDTO> updateModel(
            @PathVariable UUID id,
            @Valid @ModelAttribute EditModelRequest request
    ) {
        EditModelCommand command = requestMapper.toCommand(request, id);

        ModelDTO updatedModel = editModelUseCase.execute(command);

        return ResponseEntity.ok(updatedModel);
    }
}
