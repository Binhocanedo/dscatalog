package com.fabio.dscatalog.services;

import com.fabio.dscatalog.dto.CategoryDTO;
import com.fabio.dscatalog.entities.Category;
import com.fabio.dscatalog.repositories.CategoryRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRespository categoryRespository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll (){
       List<Category> categoryList = categoryRespository.findAll();
       return categoryList.stream().map(CategoryDTO::new).collect(Collectors.toList());

    }
}
