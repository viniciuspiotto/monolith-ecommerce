package edu.unifalmg.monolithecommerce.catalog.application.usecase;

import edu.unifalmg.monolithecommerce.catalog.application.dto.CategoryDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.CreateCategoryCommand;
import edu.unifalmg.monolithecommerce.catalog.application.mapper.CategoryMapper;
import edu.unifalmg.monolithecommerce.catalog.application.port.in.CreateCategoryPort;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.CategoryRepositoryPort;
import edu.unifalmg.monolithecommerce.catalog.domain.model.Category;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class CreateCategoryModelUseCase implements CreateCategoryPort {
    private final CategoryRepositoryPort categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryDTO execute(CreateCategoryCommand cmd){
        Optional<Category> categoryFounded = categoryRepository.findByName(cmd.name());

        if (categoryFounded.isPresent()) {
            log.warn("A category with {} already exists", cmd.name());
            throw new IllegalArgumentException("A category with the name '" + cmd.name() + "' already exists.");
        }

        Category category = Category.create(
                cmd.name(),
                cmd.description()
        );

        log.info("Creating category with name '{}'.", cmd.name());

        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(savedCategory);
    }
}
