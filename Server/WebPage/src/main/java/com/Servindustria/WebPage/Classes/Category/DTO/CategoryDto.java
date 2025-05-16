package com.Servindustria.WebPage.Classes.Category.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryDto {
    private Long id;

    @NotBlank(message = "El nombre de la categoría no puede estar en blanco")
    private String nombre;
}
