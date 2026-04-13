package com.example.oms.category;

import com.example.oms.exceptions.ResourceAlreadyExists;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryResponseDTO create(CategoryRequestDTO categoryRequestDTO){
        boolean exists = categoryRepository.existsByName(categoryRequestDTO.getName());
        if(exists){
            throw new ResourceAlreadyExists("Category already exists");
        }

        Category category = Category.builder().name(categoryRequestDTO.getName()).build();
        Category createdCategory = categoryRepository.save(category);
        return modelMapper.map(createdCategory,CategoryResponseDTO.class);

    }

    public List<CategoryResponseDTO> getAllCategories(){
        List<Category> categoryList= categoryRepository.findAll();
        return categoryList.stream().map(category -> modelMapper.map(category,CategoryResponseDTO.class)).collect(Collectors.toList());
    }
}
