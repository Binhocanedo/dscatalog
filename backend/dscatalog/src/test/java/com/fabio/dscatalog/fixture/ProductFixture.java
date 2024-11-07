package com.fabio.dscatalog.fixture;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.fabio.dscatalog.entities.Category;
import com.fabio.dscatalog.entities.Product;

import java.time.Instant;

import static com.fabio.dscatalog.entities.Product.Fields.*;

public class ProductFixture  implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(Product.class).addTemplate("basic", new Rule(){
            {
                add(name, "Phone");
                add(description, "Good Phone");
                add(price, 800.0);
                add(imgUrl, "https://img.com/img.png");
                add(date, Instant.parse("2020-10-20T03:00:00Z"));
                //add(categories, has(1).of(Category.class, "basic"));
            }
        });
    }
}