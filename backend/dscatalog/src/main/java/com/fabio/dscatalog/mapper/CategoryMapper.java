package com.fabio.dscatalog.mapper;

import com.fabio.dscatalog.dto.CategoryDTO;
import com.fabio.dscatalog.entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryDTO dto){
        Category entity = new Category();
        entity.setName(dto.name());
        return entity;
    }

    public CategoryDTO toDto(Category entity){
        return new CategoryDTO(entity);
    }

    public void updateEntityFromDto(CategoryDTO dto, Category entity){
        entity.setName(dto.name());
    }
}
