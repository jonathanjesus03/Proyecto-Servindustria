package com.Servindustria.WebPage.Modules.Service.DTO;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ServiceDto {
    private Long id;
    private String cod;
    private String type;
    private String cycle;
    private String description;
    private BigDecimal footage;
    private BigDecimal price;
    private Long idCategory; 
}
