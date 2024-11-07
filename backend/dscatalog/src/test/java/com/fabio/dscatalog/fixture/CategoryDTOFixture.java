package com.fabio.dscatalog.fixture;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.fabio.dscatalog.dto.CategoryDTO;
import com.fabio.dscatalog.entities.Category;

import static com.fabio.dscatalog.entities.Category.Fields.id;
import static com.fabio.dscatalog.entities.Category.Fields.name;

public class CategoryDTOFixture implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(CategoryDTO.class).addTemplate("basic", new Rule(){
            {
                add(id, 1L);
                add(name, "Electronics");
            }
        });
    }
}
