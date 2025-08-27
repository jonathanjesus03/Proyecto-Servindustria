package com.Servindustria.WebPage.Modules.Quote.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QuoteDto {
    private Long id;
    private String cod;
    private LocalDateTime dateEmi;
    private LocalDateTime dateExpi;
    private String state;
    private BigDecimal subTotal;
    private BigDecimal dscto;
    private BigDecimal total;
    private Long idClient;
}
