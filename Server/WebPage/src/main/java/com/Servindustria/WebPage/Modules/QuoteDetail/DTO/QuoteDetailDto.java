package com.Servindustria.WebPage.Modules.QuoteDetail.DTO;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class QuoteDetailDto {
private Long idQuoteDetail;
    private Long idQuote;
    private Long idProduct;
    private Long idService;
    private Long idAfterSales;
    private Integer amount;
    private BigDecimal unitaryPrice;
    private BigDecimal subtotal;
}
