package com.fabio.dscatalog.dto;

import com.fabio.dscatalog.entities.Category;

import java.time.Instant;


public record CategoryDTO(Long id, String name, Instant createdAt, Instant updateAt){
    public CategoryDTO(Category entity){
        this(entity.getId(), entity.getName(), entity.getCreatedAt(), entity.getUpdateAt());
    }
}