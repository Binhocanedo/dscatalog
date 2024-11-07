package com.fabio.dscatalog.utils;

import com.fabio.dscatalog.entities.Product;

public class SameDataUtils {

    public static boolean isSameData(Product existingEntity, Product newEntity){
        if(existingEntity == null || newEntity == null){
            return false;
        }
        return existingEntity.getName().equals(newEntity.getName())
                && existingEntity.getDescription().equals(newEntity.getDescription())
                && existingEntity.getPrice().equals(newEntity.getPrice())
                && existingEntity.getImgUrl().equals(newEntity.getImgUrl())
                && existingEntity.getDate().equals(newEntity.getDate())
                && existingEntity.getCategories().equals(newEntity.getCategories());
    }
}
