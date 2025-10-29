package edu.unifalmg.monolithecommerce.catalog.application.usecase.category;

import edu.unifalmg.monolithecommerce.catalog.application.dto.category.CategoryDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.category.CreateCategoryCommand;
import edu.unifalmg.monolithecommerce.catalog.application.dto.category.GetCategoryByIdCommand;
import edu.unifalmg.monolithecommerce.catalog.application.mapper.category.CategoryMapper;
import edu.unifalmg.monolithecommerce.catalog.application.port.in.category.GetCategoryByIdPort;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.category.CategoryRepositoryPort;
import edu.unifalmg.monolithecommerce.catalog.domain.category.Category;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCategoryByIdUseCase implements GetCategoryByIdPort {

    private final CategoryRepositoryPort categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryDTO execute(GetCategoryByIdCommand cmd){
        Category categoryFound = categoryRepository.findById(cmd.id());
        return categoryMapper.toDTO(categoryFound);
    }
}
