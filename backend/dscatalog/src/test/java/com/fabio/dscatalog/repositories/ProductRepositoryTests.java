package com.fabio.dscatalog.repositories;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.fabio.dscatalog.entities.Product;
import com.fabio.dscatalog.fixture.CategoryFixture;
import com.fabio.dscatalog.fixture.ProductFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    private long exintingId;
    private long countTotalProducts;

    @BeforeAll
    public static void setup() {
        FixtureFactoryLoader.loadTemplates("com.seu.pacote.fixture");
        new ProductFixture().load();
        new CategoryFixture().load();
    }

    @BeforeEach
    void setUp() throws Exception{
        exintingId = 1L;
        countTotalProducts = 25L;
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull(){
        Product product = Fixture.from(Product.class).gimme("basic");
        product.setId(null);

        product = repository.save(product);

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countTotalProducts + 1, product.getId());

    }

    @Test
    public void searchShouldReturnAProductIsNotNull(){

        Optional<Product> result = repository.findById(exintingId);

        Assertions.assertEquals(result.isPresent(), true);

        Assertions.assertNotNull(result.get(), "O produto não deveria estar nulo");

    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){

        repository.deleteById(exintingId);

        Optional<Product> result = repository.findById(exintingId);

        Assertions.assertFalse(result.isPresent());
    }
}
