package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.web;

import edu.unifalmg.monolithecommerce.catalog.application.dto.CreateModelCommand;
import edu.unifalmg.monolithecommerce.catalog.application.dto.ModelDTO;
import edu.unifalmg.monolithecommerce.catalog.application.port.in.CreateModelPort;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/models")
@RequiredArgsConstructor
public class CatalogArtistController {
    private final CreateModelPort createModelUseCase;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> createModel(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("price") BigDecimal price,
            @RequestParam("categoryId") UUID categoryId,
            @RequestParam("thumbnailFile") MultipartFile thumbnailFile,
            @RequestParam("meshFile") MultipartFile meshFile,
            @RequestParam("textureFile") MultipartFile textureFile
    ) {
        try {
            Money moneyPrice = new Money(price);

            CreateModelCommand.FileCommand thumbnailCommand = new CreateModelCommand.FileCommand(
                    thumbnailFile.getOriginalFilename(),
                    thumbnailFile.getInputStream()
            );

            CreateModelCommand.FileCommand meshCommand = new CreateModelCommand.FileCommand(
                    meshFile.getOriginalFilename(),
                    meshFile.getInputStream()
            );

            CreateModelCommand.FileCommand textureCommand = new CreateModelCommand.FileCommand(
                    textureFile.getOriginalFilename(),
                    textureFile.getInputStream()
            );

            CreateModelCommand command = new CreateModelCommand(
                    title,
                    description,
                    thumbnailCommand,
                    moneyPrice,
                    meshCommand,
                    textureCommand,
                    categoryId
            );

            ModelDTO createdModel = createModelUseCase.execute(command);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdModel);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing file streams: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
