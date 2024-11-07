package com.fabio.dscatalog.resources;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.fabio.dscatalog.dto.ProductDTO;
import com.fabio.dscatalog.erros.ErroMensagem;
import com.fabio.dscatalog.exceptions.ApiException;
import com.fabio.dscatalog.exceptions.DataBaseException;
import com.fabio.dscatalog.exceptions.ResourceNotFoundException;
import com.fabio.dscatalog.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.print.attribute.standard.Media;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




@WebMvcTest(ProductResource.class)
public class ProductResourceTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    private PageImpl<ProductDTO> page;

    private final Long existingId = 1L;
    private final Long nonExistingId = 2L;
    private final Long dependentId = 3L;


    @BeforeEach
    void setUp() throws Exception{

        FixtureFactoryLoader.loadTemplates("com.fabio.dscatalog.fixture");
        ProductDTO productDTO = Fixture.from(ProductDTO.class).gimme("basic");
        page = new PageImpl<>(List.of(productDTO));
        when(productService.findAllPaged(any())).thenReturn(page);

        when(productService.findById(existingId)).thenReturn(productDTO);

        when(productService.update(eq(existingId), any())).thenReturn(productDTO);
        ApiException exception = new ApiException(ErroMensagem.PRODUTO_INEXISTENTE);
        when(productService.update(eq(nonExistingId), any())).thenThrow(exception);

        doNothing().when(productService).delete(existingId);

        when(productService.insert(productDTO)).thenReturn(productDTO);



    }

    @Test
    public void findAllShouldReturnPage() throws Exception {
        ResultActions result =  mockMvc.perform(get("/products").accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    public void findByIdShouldReturnProductDTOWhenExistingId() throws Exception {

        ResultActions result = mockMvc.perform(get("/products/{id}", existingId).accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
    }

    @Test
    public void findByIdShouldReturnApiExceptionDoesNotExist() throws Exception {

        ApiException exception = new ApiException(ErroMensagem.PRODUTO_INEXISTENTE);
        when(productService.findById(nonExistingId)).thenThrow(exception);


        ResultActions result = mockMvc.perform(get("/products/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
        result.andExpect(jsonPath("$.message").value("Produto não encontrado"));
        result.andExpect(jsonPath("$.status").value("NOT_FOUND"));
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExists() throws Exception{

        ProductDTO productDTO = Fixture.from(ProductDTO.class).gimme("existingId");

        String jsonBody = objectMapper.writeValueAsString(productDTO);


        ResultActions result = mockMvc.perform(put("/products/{id}", existingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
        result.andExpect(jsonPath("$.price").exists());
        result.andExpect(jsonPath("$.imgUrl").exists());
        result.andExpect(jsonPath("$.date").exists());

    }

    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {

        ProductDTO productDTO = Fixture.from(ProductDTO.class).gimme("nonexistingId");

        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result = mockMvc.perform(put("/products/{id}", nonExistingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteShouldDeleteProductDTOWhenIdExists() throws Exception {

        ResultActions result = mockMvc.perform(delete("/products/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNoContent());
    }

    @Test
    public void deleteShouldReturnNotFoundWhenDoesNotExist() throws Exception {

        ApiException exception = new ApiException(ErroMensagem.RECURSO_NAO_ENCONTRADO);
        doThrow(exception).when(productService).delete(nonExistingId);

        ResultActions result = mockMvc.perform(delete("/products/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteShouldReturnDataBaseExceptionWhenDepentId() throws Exception {

        ApiException exception = new ApiException(ErroMensagem.INTEGRIDADE_VIOLADA);
        doThrow(exception).when(productService).delete(dependentId);

        ResultActions result = mockMvc.perform(delete("/products/{id}", dependentId)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest());
    }

    @Test
    public void insertShouldInsertNewProductDTOWhenExistingId() throws Exception {

        ProductDTO productDTO = Fixture.from(ProductDTO.class).gimme("basic");

        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result = mockMvc.perform(post("/products")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));


        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.name").exists());
    }
}
