package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.in.controllers;

import edu.unifalmg.monolithecommerce.catalog.application.dto.CategoryDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.CreateCategoryCommand;
import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.GetCategoryByIdCommand;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.in.dto.requests.CreateCategoryRequest;
import edu.unifalmg.monolithecommerce.catalog.application.port.in.CreateCategoryPort;
import edu.unifalmg.monolithecommerce.catalog.application.port.in.GetCategoryByIdPort;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.mapper.CategoryRequestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CreateCategoryPort createCategoryUseCase;
    private final GetCategoryByIdPort getCategoryByIdUseCase;
    private final CategoryRequestMapper requestMapper;

    @PostMapping
    @PreAuthorize("hasRole('ARTIST')")
    public ResponseEntity<?> createCategory(
           @Valid @RequestBody CreateCategoryRequest request
    ) {
        CreateCategoryCommand cmd = requestMapper.toCommand(request);

        CategoryDTO createdCategory = createCategoryUseCase.execute(cmd);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(
            @PathVariable("id") UUID id
    ) {
        GetCategoryByIdCommand command = new GetCategoryByIdCommand(id);

        CategoryDTO fundedCategory = getCategoryByIdUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.OK).body(fundedCategory);
    }
}
