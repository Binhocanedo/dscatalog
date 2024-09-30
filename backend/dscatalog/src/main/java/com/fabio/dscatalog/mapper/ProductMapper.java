package com.fabio.dscatalog.mapper;

import com.fabio.dscatalog.dto.CategoryDTO;
import com.fabio.dscatalog.dto.ProductDTO;
import com.fabio.dscatalog.entities.Category;
import com.fabio.dscatalog.entities.Product;
import com.fabio.dscatalog.repositories.CategoryRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    @Autowired
    private CategoryRespository categoryRespository;

    public Product toEntity(ProductDTO dto){
        Product entity = new Product();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setDate(dto.getDate());
        entity.setImgUrl(dto.getImgUrl());
        entity.setPrice(dto.getPrice());

        entity.getCategories().clear();

        for(CategoryDTO categoryDto : dto.getCategories()){
            Category category = categoryRespository.getOne(dto.getId());
            entity.getCategories().add(category);
        }
        return entity;
    }

    public ProductDTO toDto(Product entity){
        return new ProductDTO(entity);
    }

    public void updateEntityFromDto(ProductDTO dto, Product entity){
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setDate(dto.getDate());
        entity.setImgUrl(dto.getImgUrl());
        entity.setPrice(dto.getPrice());
        entity.getCategories().clear();
        for(CategoryDTO categoryDto : dto.getCategories()){
            Category category = categoryRespository.getOne(dto.getId());
            entity.getCategories().add(category);
        }
    }
}
