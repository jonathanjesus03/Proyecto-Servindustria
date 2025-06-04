package com.Servindustria.WebPage.Modules.Invoice.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class InvoiceDto {
    private Long id;
    private String code;
    private LocalDateTime DateEmi;
    private BigDecimal subtotal;
    private BigDecimal dscto;
    private BigDecimal total;
    private String paymentType;
    private String paymentMethod;
    private BigDecimal paymentAmount;
    private BigDecimal pendingBalance;
    private Long quoteId;
    private Long shippingGuideId;
    private Long employeeId;
    private Long clientId;
}
