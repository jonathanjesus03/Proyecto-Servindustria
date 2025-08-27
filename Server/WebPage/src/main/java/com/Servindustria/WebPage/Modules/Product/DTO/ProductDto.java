package com.Servindustria.WebPage.Modules.Product.DTO;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductDto {

    private Long id;

    @NotBlank
    private String cod;

    @NotBlank
    private String type;

    @NotBlank
    private String description;

    @NotNull
    private Integer stock;

    @NotNull
    private BigDecimal price;

    private Long idInventory;

    private Long idCategory;

    // Nuevos atributos opcionales

    private String height;

    private String length;

    private String depth;

    private String brand;

    private String model;

    private String img;

    private String application;

    private String effect;

    private String content;

    private String observations;
}
