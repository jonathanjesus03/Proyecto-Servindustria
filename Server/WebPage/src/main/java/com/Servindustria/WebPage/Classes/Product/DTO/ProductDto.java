package com.Servindustria.WebPage.Classes.Product.DTO;

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
    private String tipo;

    @NotBlank
    private String descripcion;

    @NotNull
    private Integer stock;

    @NotNull
    private BigDecimal precio;

    private Long idInventario;

    private Long idCategoria;

    // Nuevos atributos opcionales

    private String alto;

    private String largo;

    private String fondo;

    private String marca;

    private String modelo;

    private String aplicacion;

    private String efecto;

    private String contenido;

    private String observaciones;
}
