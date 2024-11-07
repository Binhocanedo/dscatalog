package com.fabio.dscatalog.fixture;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.fabio.dscatalog.dto.CategoryDTO;
import com.fabio.dscatalog.dto.ProductDTO;

import java.time.Instant;

import static com.fabio.dscatalog.dto.ProductDTO.Fields.*;

public class ProductDTOFixture implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(ProductDTO.class).addTemplate("basic", new Rule() {{
            add(name, "Phone");
            add(description, "Good Phone");
            add(price, 800.0);
            add(imgUrl, "https://img.com/img.png");
            add(date, Instant.parse("2020-10-20T03:00:00Z"));
        }});

        Fixture.of(ProductDTO.class).addTemplate("existingId", new Rule() {{
            add(id, 1L);
            add(name, "Phone");
            add(description, "Good Phone");
            add(price, 800.0);
            add(imgUrl, "https://img.com/img.png");
            add(date, Instant.parse("2020-10-20T03:00:00Z"));
        }});

        Fixture.of(ProductDTO.class).addTemplate("nonexistingId", new Rule() {{
            add(name, "Phone");
            add(description, "Good Phone");
            add(price, 800.0);
            add(imgUrl, "https://img.com/img.png");
            add(date, Instant.parse("2020-10-20T03:00:00Z"));
        }});
    }
}