package edu.unifalmg.monolithecommerce.catalog.application.usecase.category;

import edu.unifalmg.monolithecommerce.catalog.application.dto.category.CategoryDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.category.CreateCategoryCommand;
import edu.unifalmg.monolithecommerce.catalog.application.mapper.category.CategoryMapper;
import edu.unifalmg.monolithecommerce.catalog.application.port.in.category.CreateCategoryPort;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.category.CategoryRepositoryPort;
import edu.unifalmg.monolithecommerce.catalog.domain.category.Category;
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
        Category category = Category.create(
                cmd.name(),
                cmd.description()
        );

        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(savedCategory);
    }

}
