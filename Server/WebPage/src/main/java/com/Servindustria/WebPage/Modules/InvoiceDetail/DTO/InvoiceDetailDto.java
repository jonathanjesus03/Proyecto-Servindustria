package com.Servindustria.WebPage.Modules.InvoiceDetail.DTO;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class InvoiceDetailDto {
    private Long id;
    private Long invoiceId;
    private Long productId;
    private Long serviceId;
    private Long afterSalesId;
    private Integer amount;
    private BigDecimal unitaryPrice;
    private BigDecimal subtotal;
}