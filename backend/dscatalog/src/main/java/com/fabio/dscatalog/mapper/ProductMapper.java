package com.fabio.dscatalog.mapper;

import com.fabio.dscatalog.dto.ProductDTO;
import com.fabio.dscatalog.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductDTO dto){
        Product entity = new Product();
        entity.setName(dto.getName());
        return entity;
    }

    public ProductDTO toDto(Product entity){
        return new ProductDTO(entity);
    }

    public void updateEntityFromDto(ProductDTO dto, Product entity){
        entity.setName(dto.getName());
    }
}
