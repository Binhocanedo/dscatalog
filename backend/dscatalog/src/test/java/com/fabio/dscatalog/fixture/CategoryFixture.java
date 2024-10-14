package com.fabio.dscatalog.fixture;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.fabio.dscatalog.entities.Category;


import static com.fabio.dscatalog.entities.Category.Fields.*;

public class CategoryFixture implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(Category.class).addTemplate("basic", new Rule(){
            {
                add(id, 2L);
                add(name, "Electronics");
            }
        });
    }
}
