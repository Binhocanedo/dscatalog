package com.fabio.dscatalog.services;

import br.com.six2six.fixturefactory.Fixture;
import com.fabio.dscatalog.dto.ProductDTO;
import com.fabio.dscatalog.entities.Product;
import com.fabio.dscatalog.exceptions.ApiException;
import com.fabio.dscatalog.exceptions.ResourceNotFoundException;
import com.fabio.dscatalog.fixture.CategoryDTOFixture;
import com.fabio.dscatalog.fixture.CategoryFixture;
import com.fabio.dscatalog.fixture.ProductDTOFixture;
import com.fabio.dscatalog.fixture.ProductFixture;
import com.fabio.dscatalog.mapper.ProductMapper;
import com.fabio.dscatalog.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductMapper mapper;

    @Mock
    private ProductRepository repository;


    Long existingId = 1L;
    Long dependentId = 3L;
    Long nonExistingId = 1000L;
    private Product product;
    private ProductDTO dto;

    @BeforeEach
    void setUp() throws Exception {

        new CategoryFixture().load();
        new ProductFixture().load();
        new CategoryDTOFixture().load();
        new ProductDTOFixture().load();

        product = Fixture.from(Product.class).gimme("basic");
        PageImpl<Product> page = new PageImpl<>(List.of(product));

        dto = Fixture.from(ProductDTO.class).gimme("basic");

        doNothing().when(repository).deleteById(existingId);
        doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

        when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);
        when(repository.save(ArgumentMatchers.any())).thenReturn(product);

        when(repository.findById(existingId)).thenReturn(Optional.of(product));
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        when(repository.getReferenceById(existingId)).thenReturn(product);
        when(repository.getReferenceById(nonExistingId)).thenThrow(new EntityNotFoundException());
        when(repository.save(product)).thenReturn(product);

        doNothing().when(mapper).updateEntityFromDto(dto, product);



        when(repository.existsById(existingId)).thenReturn(true);
        when(repository.existsById(nonExistingId)).thenReturn(false);
        when(repository.existsById(dependentId)).thenReturn(true);

    }

    @Test
    public void updateShouldUpdateProductWhenExistingId(){
        when(mapper.toDto(product)).thenReturn(dto);
        ProductDTO result = service.update(existingId, dto);
        Assertions.assertEquals(dto, result);
    }

    @Test
    public void updateShouldNotUpdateProductWhenNonExistingId(){
        Assertions.assertThrows(ApiException.class, ()-> {
            service.update(nonExistingId, dto);
        });
    }

    @Test
    public void findByIdShouldReturnProductWhenExistingId(){
        Product product1 = Fixture.from(Product.class).gimme("basic");
        ProductDTO result = service.findById(existingId);
        ProductDTO expected = new ProductDTO(product1, product1.getCategories());
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void findByIdShouldReturnProductWhenNotExistingId(){
        Assertions.assertThrows(ApiException.class, () -> {
            service.findById(nonExistingId);
        });
    }

    @Test
    public void findAllPagedShouldReturnPage(){
        Pageable pageable = PageRequest.of(0, 10);

        Page<ProductDTO> result = service.findAllPaged(pageable);

        Assertions.assertNotNull(result);
        verify(repository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() ->{
            service.delete(existingId);
        });
        verify(repository, times(1)).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(ApiException.class, () -> {
            service.delete(nonExistingId);
        });
    }

    @Test
    public void deleteShouldDataBaseExceptionWhenDependentId() {
        Assertions.assertThrows(ApiException.class, () -> {
            service.delete(dependentId);
        });
    }
}