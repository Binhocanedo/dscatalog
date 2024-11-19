package com.fabio.dscatalog.integration;

import com.fabio.dscatalog.dto.ProductDTO;
import com.fabio.dscatalog.exceptions.ApiException;
import com.fabio.dscatalog.repositories.ProductRepository;
import com.fabio.dscatalog.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ProductServiceIntegration {

    @Autowired
    private ProductService service;
    
    @Autowired
    private ProductRepository repository;


    private Long existingId;
    private Long nonExistinId;
    private Long countTotalProducts;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistinId = 1000L;
        countTotalProducts = 25L;
    }


    @Test
    public void deleteShouldDeleteResourceWhenExistingId(){
        service.delete(existingId);
        Assertions.assertEquals(countTotalProducts - 1, repository.count());
    }

    @Test
    public void deleteShouldThrowNotApiExceptionWhenDoesNotExists(){

        Assertions.assertThrows(ApiException.class, () -> {
            service.delete(nonExistinId);
        });
    }

    @Test
    public void findAllPagedShouldReturnPagedWhenPage0Size10(){

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<ProductDTO> result = service.findAllPaged(pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(0, result.getNumber());
        Assertions.assertEquals(10, result.getSize());
        Assertions.assertEquals(countTotalProducts, result.getTotalElements());

    }

    @Test
    public void findAllPagedShouldEmptyPagedWhenDoesNotExists(){

        PageRequest pageRequest = PageRequest.of(50, 10);
        Page<ProductDTO> result = service.findAllPaged(pageRequest);
        Assertions.assertTrue(result.isEmpty());

    }

    @Test
    public void findAllPagedShouldReturnSortedWhenSortByName(){

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));

        Page<ProductDTO> result = service.findAllPaged(pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("Macbook Pro", result.getContent().getFirst().getName());
        Assertions.assertEquals("PC Gamer", result.getContent().get(1).getName());
        Assertions.assertEquals("PC Gamer Alfa", result.getContent().get(2).getName());

    }

}
