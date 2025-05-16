package com.Servindustria.WebPage.Classes.Category.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Servindustria.WebPage.Classes.Category.Repository.CategoryRepository;
import com.Servindustria.WebPage.Exception.ResourceNotFoundException;
import com.Servindustria.WebPage.Classes.Category.DTO.CategoryDto;
import com.Servindustria.WebPage.Classes.Category.Mapper.CategoryMapper;
import com.Servindustria.WebPage.Classes.Category.Model.Category;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    
    //Create
      public void createCategory(CategoryDto categoryDto){
        Category category = CategoryMapper.toEntity(categoryDto);
        categoryRepository.save(category);
    }

    //Get by ID
    public CategoryDto getCategoryById(Long id){
        Category category =  categoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Categoria con ID " +id+" no encontrado"));
        return CategoryMapper.toDto(category);
    }

    //Get All
    public List<CategoryDto> getAllCategory() {
        return categoryRepository.findAll().stream()
            .map(CategoryMapper::toDto)
            .collect(Collectors.toList());
    }

    //Update
    public CategoryDto updateCategory(Long id, CategoryDto dto){
        Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Categoria con ID " +id+" no encontrado"));        
        category.setId(dto.getId());
        category.setNombre(dto.getNombre());
        Category categoryUpdated = categoryRepository.save(category);

        return CategoryMapper.toDto(categoryUpdated);
    } 

    //Delete
    public void deleteCategory(Long id){
        categoryRepository.findById(id)
        .orElseThrow(()-> new ResourceNotFoundException("Categoria con ID "+id+" no encontrado"));
        categoryRepository.deleteById(id);
    }
    
    
}
