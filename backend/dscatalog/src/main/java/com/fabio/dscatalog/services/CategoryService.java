package com.fabio.dscatalog.services;

import com.fabio.dscatalog.dto.CategoryDTO;
import com.fabio.dscatalog.entities.Category;
import com.fabio.dscatalog.erros.ErroMensagem;
import com.fabio.dscatalog.exceptions.ApiException;
import com.fabio.dscatalog.exceptions.ResourceNotFoundException;
import com.fabio.dscatalog.mapper.CategoryMapper;
import com.fabio.dscatalog.repositories.CategoryRespository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    @Autowired
    private CategoryRespository categoryRespository;
    @Autowired
    private CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(PageRequest pageRequest){
       Page<Category> categoryList = categoryRespository.findAll(pageRequest);
       return categoryList.map(CategoryDTO::new);
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Category entity = categoryRespository.findById(id)
                .orElseThrow(() -> new ApiException(ErroMensagem.CATEGORIA_INEXISTENTE));
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
        Category entity = categoryMapper.toEntity(dto);
        entity = categoryRespository.save(entity);
        return categoryMapper.toDto(entity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        try{
            Category entity = categoryRespository.getReferenceById(id);
            categoryMapper.updateEntityFromDto(dto, entity);
            entity = categoryRespository.save(entity);
            return categoryMapper.toDto(entity);
        }catch(EntityNotFoundException exception){
            throw new ResourceNotFoundException("id not found " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if(!categoryRespository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado.");
        }
        try{
            categoryRespository.deleteById(id);
        }catch(DataIntegrityViolationException exception){
            throw new ApiException(ErroMensagem.INTEGRIDADE_VIOLADA);
        }
    }
}
