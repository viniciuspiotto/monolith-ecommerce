package edu.unifalmg.monolithecommerce.catalog.application.usecase;

import edu.unifalmg.monolithecommerce.catalog.application.dto.CategoryDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.CreateCategoryCommand;
import edu.unifalmg.monolithecommerce.catalog.application.mapper.CategoryMapper;
import edu.unifalmg.monolithecommerce.catalog.application.port.in.CreateCategoryPort;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.CategoryRepositoryPort;
import edu.unifalmg.monolithecommerce.catalog.domain.model.Category;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCategoryModelUseCase implements CreateCategoryPort {
    private final CategoryRepositoryPort categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryDTO execute(CreateCategoryCommand cmd){
        Boolean isExistCategory = categoryRepository.existsByName(cmd.name());

        if (isExistCategory) {
            throw new IllegalArgumentException("A category with the name '" + cmd.name() + "' already exists.");
        }

        Category category = Category.create(
                cmd.name(),
                cmd.description()
        );

        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(savedCategory);
    }
}
