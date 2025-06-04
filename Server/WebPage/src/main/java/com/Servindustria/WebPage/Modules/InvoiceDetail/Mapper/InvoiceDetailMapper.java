package com.Servindustria.WebPage.Modules.InvoiceDetail.Mapper;

import com.Servindustria.WebPage.Modules.InvoiceDetail.DTO.InvoiceDetailDto;
import com.Servindustria.WebPage.Modules.InvoiceDetail.Model.InvoiceDetail;
import com.Servindustria.WebPage.Modules.Product.Model.Product;
import com.Servindustria.WebPage.Modules.Service.Model.Service;
import com.Servindustria.WebPage.Modules.AfterSales.Model.AfterSales;
import com.Servindustria.WebPage.Modules.Invoice.Model.Invoice;

import java.util.Optional;

public class InvoiceDetailMapper {

    public static InvoiceDetailDto toDto(InvoiceDetail detail) {
        if (detail == null) return null;
        InvoiceDetailDto dto = new InvoiceDetailDto();
        dto.setId(detail.getId());
        dto.setInvoiceId(detail.getInvoice() != null ? detail.getInvoice().getId() : null);
        dto.setProductId(detail.getProduct() != null ? detail.getProduct().getId() : null);
        dto.setServiceId(detail.getService() != null ? detail.getService().getId() : null);
        dto.setAfterSalesId(detail.getAfterSales() != null ? detail.getAfterSales().getId() : null);
        dto.setAmount(detail.getAmount());
        dto.setUnitaryPrice(detail.getUnitaryPrice());
        dto.setSubtotal(detail.getSubtotal());
        return dto;
    }

    public static InvoiceDetail toEntity(
            InvoiceDetailDto dto,
            Invoice invoice,
            Optional<Product> product,
            Optional<Service> service,
            Optional<AfterSales> afterSales
    ) {
        if (dto == null) return null;
        InvoiceDetail detail = new InvoiceDetail();
        detail.setId(dto.getId());
        detail.setInvoice(invoice);
        detail.setProduct(product.orElse(null));
        detail.setService(service.orElse(null));
        detail.setAfterSales(afterSales.orElse(null));
        detail.setAmount(dto.getAmount());
        detail.setUnitaryPrice(dto.getUnitaryPrice());
        detail.setSubtotal(dto.getSubtotal());
        return detail;
    }
}