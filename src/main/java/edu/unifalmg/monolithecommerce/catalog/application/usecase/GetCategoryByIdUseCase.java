package edu.unifalmg.monolithecommerce.catalog.application.usecase;

import edu.unifalmg.monolithecommerce.catalog.application.dto.CategoryDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.GetCategoryByIdCommand;
import edu.unifalmg.monolithecommerce.catalog.application.mapper.CategoryMapper;
import edu.unifalmg.monolithecommerce.catalog.application.port.in.GetCategoryByIdPort;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.CategoryRepositoryPort;
import edu.unifalmg.monolithecommerce.catalog.domain.model.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class GetCategoryByIdUseCase implements GetCategoryByIdPort {

    private final CategoryRepositoryPort categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO execute(GetCategoryByIdCommand cmd){
        Optional<Category> categoryFounded = categoryRepository.findById(cmd.id());

        if (categoryFounded.isEmpty()) {
            log.warn("A category with {} not found", cmd.id());
            throw new IllegalArgumentException("A category with the id '" + cmd.id() + "' not exists.");
        }

        return categoryFounded.map(categoryMapper::toDTO).orElse(null);
    }
}
