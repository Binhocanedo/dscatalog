package com.fabio.dscatalog.resources;

import com.fabio.dscatalog.dto.ProductDTO;
import com.fabio.dscatalog.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

    @Autowired
    private ProductService ProductService;

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable){
        Page<ProductDTO> list = ProductService.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
        ProductDTO dto = ProductService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO dto){
        dto = ProductService.insert(dto);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(url).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO dto){
        dto = ProductService.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> delete(@PathVariable Long id){
        ProductService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
