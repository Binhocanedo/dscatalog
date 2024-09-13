package com.fabio.dscatalog.services;

import com.fabio.dscatalog.entities.Category;
import com.fabio.dscatalog.repositories.CategoryRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRespository categoryRespository;

    @Transactional(readOnly = true)
    public List<Category> findAll (){return categoryRespository.findAll();}
}
