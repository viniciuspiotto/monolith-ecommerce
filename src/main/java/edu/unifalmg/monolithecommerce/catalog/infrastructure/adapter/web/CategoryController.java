package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.web;

import edu.unifalmg.monolithecommerce.catalog.application.dto.CreateModelCommand;
import edu.unifalmg.monolithecommerce.catalog.application.dto.category.CategoryDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.category.CreateCategoryCommand;
import edu.unifalmg.monolithecommerce.catalog.application.dto.category.GetCategoryByIdCommand;
import edu.unifalmg.monolithecommerce.catalog.application.port.in.category.CreateCategoryPort;
import edu.unifalmg.monolithecommerce.catalog.application.port.in.category.GetCategoryByIdPort;
import edu.unifalmg.monolithecommerce.catalog.application.usecase.category.GetCategoryByIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/catalog/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CreateCategoryPort createCategoryUseCase;
    private final GetCategoryByIdPort getCategoryByIdUseCase;

    @PostMapping
    public ResponseEntity<?> createCategory(
            @RequestParam("name") String name,
            @RequestParam("description") String description
    ) {
        try{
            CreateCategoryCommand command = new CreateCategoryCommand(
                    name,
                    description
            );
            CategoryDTO createdCategory = createCategoryUseCase.execute(command);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(
            @PathVariable("id") UUID id
    ) {
        try{
            GetCategoryByIdCommand command = new GetCategoryByIdCommand(id);
            CategoryDTO fundedCategory = getCategoryByIdUseCase.execute(command);
            return ResponseEntity.status(HttpStatus.OK).body(fundedCategory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
