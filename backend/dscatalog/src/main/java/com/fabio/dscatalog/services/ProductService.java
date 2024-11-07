package com.fabio.dscatalog.services;

import com.fabio.dscatalog.dto.ProductDTO;
import com.fabio.dscatalog.entities.Product;
import com.fabio.dscatalog.erros.ErroMensagem;
import com.fabio.dscatalog.exceptions.ApiException;
import com.fabio.dscatalog.exceptions.ResourceNotFoundException;
import com.fabio.dscatalog.mapper.ProductMapper;
import com.fabio.dscatalog.repositories.ProductRepository;
import com.fabio.dscatalog.utils.SameDataUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(Pageable pageable){
        Page<Product> productList = productRepository.findAll(pageable);
        return productList.map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErroMensagem.PRODUTO_INEXISTENTE));
        return new ProductDTO(product, product.getCategories());
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto){
        Product entity = productMapper.toEntity(dto);
        entity = productRepository.save(entity);
        return productMapper.toDto(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto){
        try{
            Product existingEntity = productRepository.getReferenceById(id);
            Product updatedEntity = new Product();
            productMapper.updateEntityFromDto(dto, updatedEntity);
            if(SameDataUtils.isSameData(existingEntity, updatedEntity)){
                throw new ApiException(ErroMensagem.PRODUTO_ATUALIZADO);
            }
            productMapper.updateEntityFromDto(dto, existingEntity);
            existingEntity = productRepository.save(existingEntity);
            return productMapper.toDto(existingEntity);
        }catch (EntityNotFoundException exception){
            throw new ApiException(ErroMensagem.PRODUTO_INEXISTENTE);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if(!productRepository.existsById(id)){
            throw new ApiException(ErroMensagem.RECURSO_NAO_ENCONTRADO);
        }
        try{
            productRepository.deleteById(id);
        }catch(DataIntegrityViolationException exception){
            throw new ApiException(ErroMensagem.INTEGRIDADE_VIOLADA);
        }
    }
}
