package com.example.oms.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RequestMapping("api/v1/products")
@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    ResponseEntity<ProductResponseDTO> createProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO){
        return new ResponseEntity<>(productService.createProduct(productRequestDTO),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<ProductResponseDTO> getProduct(@PathVariable Long Id){
        return new ResponseEntity<>(productService.getProduct(Id),HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<Page<ProductResponseDTO>> getProducts(Pageable pageable,
                                                         @RequestParam(required = false) Long categoryId){

        if(categoryId != null){
            return new ResponseEntity<>(productService.getProductsByCategoryId(categoryId,pageable),HttpStatus.OK);
        }
        return new ResponseEntity<>(productService.getAllProducts(pageable),HttpStatus.OK);
    }

}
