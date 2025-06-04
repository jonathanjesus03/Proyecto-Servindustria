package com.Servindustria.WebPage.Modules.AfterSales.Mapper;

import com.Servindustria.WebPage.Modules.AfterSales.DTO.AfterSalesDto;
import com.Servindustria.WebPage.Modules.AfterSales.Model.AfterSales;

public class AfterSalesMapper {
    public static AfterSalesDto toDto(AfterSales afterSales) {
        if (afterSales == null) return null;
        AfterSalesDto dto = new AfterSalesDto();
        dto.setId(afterSales.getId());
        dto.setCod(afterSales.getCod());
        dto.setServiceType(afterSales.getServiceType());
        dto.setDateExpi(afterSales.getDateExpi());
        dto.setPrice(afterSales.getPrice());
        dto.setState(afterSales.getState());
        dto.setAmount(afterSales.getAmount());
        dto.setIdProduct(afterSales.getProduct() != null ? afterSales.getProduct().getId() : null);
        dto.setIdService(afterSales.getService() != null ? afterSales.getService().getId() : null);
        dto.setIdClient(afterSales.getClient() != null ? afterSales.getClient().getId() : null);
        return dto;
    }

    public static AfterSales toEntity(AfterSalesDto dto) {
        if (dto == null) return null;
        AfterSales afterSales = new AfterSales();
        afterSales.setId(dto.getId());
        afterSales.setCod(dto.getCod());
        afterSales.setServiceType(dto.getServiceType());
        afterSales.setDateExpi(dto.getDateExpi());
        afterSales.setPrice(dto.getPrice());
        afterSales.setState(dto.getState());
        afterSales.setAmount(dto.getAmount());
        // Relations on (product, service, client) should be charged in Service 
        return afterSales;
    }
}
