package com.fabio.dscatalog.dto;

import com.fabio.dscatalog.entities.Category;


public record CategoryDTO(Long id, String name){
    public CategoryDTO(Category entity){
        this(entity.getId(), entity.getName());
    }
}