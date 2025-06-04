package com.Servindustria.WebPage.Modules.Quote.DTO;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class QuoteProductsDto {
    private List<ProductItem> products;
    
    @Data
    public static class ProductItem {
        private Long id;
        private int amount;
        private BigDecimal price;
        private long idCategory;
        private String type;
        private BigDecimal dscto;
    }      
}
