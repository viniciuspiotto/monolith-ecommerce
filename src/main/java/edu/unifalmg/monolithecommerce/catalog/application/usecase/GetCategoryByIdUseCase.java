package edu.unifalmg.monolithecommerce.catalog.application.usecase;

import edu.unifalmg.monolithecommerce.catalog.application.dto.CategoryDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.GetCategoryByIdCommand;
import edu.unifalmg.monolithecommerce.catalog.application.mapper.CategoryMapper;
import edu.unifalmg.monolithecommerce.catalog.application.port.in.GetCategoryByIdPort;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.CategoryRepositoryPort;
import edu.unifalmg.monolithecommerce.catalog.domain.model.Category;
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
