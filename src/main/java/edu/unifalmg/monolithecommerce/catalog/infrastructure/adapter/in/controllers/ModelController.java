package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.in.controllers;

import edu.unifalmg.monolithecommerce.catalog.application.dto.ModelDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.ModelSearchDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.CreateModelCommand;
import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.EditModelCommand;
import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.ModelSearchCommand;
import edu.unifalmg.monolithecommerce.catalog.application.mapper.ModelRequestMapper;
import edu.unifalmg.monolithecommerce.catalog.application.port.in.CreateModelPort;
import edu.unifalmg.monolithecommerce.catalog.application.port.in.EditModelPort;
import edu.unifalmg.monolithecommerce.catalog.application.port.in.SearchModelsPort;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.in.dto.requests.CreateModelRequest;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.in.dto.requests.EditModelRequest;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.in.dto.requests.SearchModelRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    private final SearchModelsPort searchModelsPort;
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

    @GetMapping("/search")
    public ResponseEntity<Page<ModelSearchDTO>> searchModels(
            @Valid SearchModelRequest request,
            @PageableDefault(size = 12) Pageable pageable) {

        ModelSearchCommand command = requestMapper.toCommand(request);

        Page<ModelSearchDTO> results = searchModelsPort.execute(command, pageable);

        return ResponseEntity.ok(results);
    }
}
