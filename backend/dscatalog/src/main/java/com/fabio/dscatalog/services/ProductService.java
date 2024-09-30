package com.fabio.dscatalog.services;

import com.fabio.dscatalog.dto.ProductDTO;
import com.fabio.dscatalog.entities.Product;
import com.fabio.dscatalog.erros.ErroMensagem;
import com.fabio.dscatalog.exceptions.ApiException;
import com.fabio.dscatalog.exceptions.ResourceNotFoundException;
import com.fabio.dscatalog.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
        Page<Product> productList = productRepository.findAll(pageRequest);
        return productList.map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErroMensagem.PRODUTO_INEXISTENTE));
        return new ProductDTO(product, product.getCategories());
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if(!productRepository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado.");
        }
        try{
            productRepository.deleteById(id);
        }catch(DataIntegrityViolationException exception){
            throw new ApiException(ErroMensagem.INTEGRIDADE_VIOLADA);
        }
    }
}
