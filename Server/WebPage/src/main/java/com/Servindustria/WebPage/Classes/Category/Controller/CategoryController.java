package com.Servindustria.WebPage.Classes.Category.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Servindustria.WebPage.Classes.Category.DTO.CategoryDto;
import com.Servindustria.WebPage.Classes.Category.Service.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    //Create
    @PostMapping
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CategoryDto categorydDto){
        categoryService.createCategory(categorydDto);
         return ResponseEntity.status(HttpStatus.CREATED).build();
    }    
    //Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id){
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryDto);
    }

    //Get All
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllInventories(){
        List<CategoryDto> listCategoriaDto = categoryService.getAllCategory();
        return ResponseEntity.ok(listCategoriaDto);
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    //Update
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategoryById(@PathVariable Long id, @Valid @RequestBody CategoryDto dto){
        CategoryDto categoryDto = categoryService.updateCategory(id, dto);
        return ResponseEntity.ok(categoryDto);
    }
    
}
