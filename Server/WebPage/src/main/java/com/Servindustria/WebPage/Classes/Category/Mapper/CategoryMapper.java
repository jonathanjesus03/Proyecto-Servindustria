package com.Servindustria.WebPage.Classes.Category.Mapper;

import com.Servindustria.WebPage.Classes.Category.DTO.CategoryDto;
import com.Servindustria.WebPage.Classes.Category.Model.Category;

public class CategoryMapper {
    public static CategoryDto toDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setNombre(category.getNombre());
        return dto;
    }

    public static Category toEntity(CategoryDto dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setNombre(dto.getNombre());
        return category;
    }
}
