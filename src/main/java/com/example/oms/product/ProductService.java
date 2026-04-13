package com.example.oms.product;


import com.example.oms.category.Category;
import com.example.oms.category.CategoryRepository;
import com.example.oms.exceptions.ResourceNotFoundException;
import com.example.oms.user.User;
import com.example.oms.user.UserResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;


    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO){
        List<Category> categoryList = categoryRepository.findAllById(productRequestDTO.getCategoryIds());
        if(categoryList.size() != productRequestDTO.getCategoryIds().size()){
            throw new ResourceNotFoundException("Invalid category ID");
        }
        Product product = Product.builder().price(productRequestDTO.getPrice()).name(productRequestDTO.getName()).categoryList(categoryList).description(productRequestDTO.getDescription()).build();
        Product savedProduct = null;

        savedProduct = productRepository.save(product);

        return modelMapper.map(savedProduct, ProductResponseDTO.class);

    }

    public ProductResponseDTO getProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User with id not found"));

        return modelMapper.map(product, ProductResponseDTO.class);
    }

    public Page<ProductResponseDTO> getProductsByCategoryId(Long categoryId, Pageable pageable){
        Page<Product> products = productRepository.findByCategories_Id(categoryId,pageable);
        return products.map(product -> modelMapper.map(product,ProductResponseDTO.class));
    }

    public Page<ProductResponseDTO> getAllProducts(Pageable pageable){
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(product -> modelMapper.map(product, ProductResponseDTO.class));

    }
}
