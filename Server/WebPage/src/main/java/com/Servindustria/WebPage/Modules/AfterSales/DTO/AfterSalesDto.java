package com.Servindustria.WebPage.Modules.AfterSales.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AfterSalesDto {
    private Long id;
    private String cod;
    private String ServiceType;
    private LocalDateTime dateExpi;
    private BigDecimal price;
    private String state;
    private Integer amount;
    private Long idProduct;
    private Long idService;
    private Long idClient;
}
