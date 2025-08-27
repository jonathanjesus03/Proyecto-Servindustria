package com.Servindustria.WebPage.Modules.QuoteDetail.Mapper;


import java.math.BigDecimal;
import java.util.Optional;

import com.Servindustria.WebPage.Modules.AfterSales.Model.AfterSales;
import com.Servindustria.WebPage.Modules.Product.Model.Product;
import com.Servindustria.WebPage.Modules.Quote.Model.Quote;
import com.Servindustria.WebPage.Modules.QuoteDetail.DTO.QuoteDetailDto;
import com.Servindustria.WebPage.Modules.QuoteDetail.Model.QuoteDetail;
import com.Servindustria.WebPage.Modules.Service.Model.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QuoteDetailMapper {

    public static QuoteDetailDto toDto(QuoteDetail quoteDetail) {
        if (quoteDetail == null) return null;
        QuoteDetailDto dto = new QuoteDetailDto();
        dto.setIdQuoteDetail(quoteDetail.getIdQuoteDetail());
        dto.setIdQuote(quoteDetail.getQuote() != null ? quoteDetail.getQuote().getId() : null);
        dto.setIdProduct(quoteDetail.getProduct() != null ? quoteDetail.getProduct().getId() : null);
        dto.setIdService(quoteDetail.getService() != null ? quoteDetail.getService().getId() : null);
        dto.setIdAfterSales(quoteDetail.getAfterSales() != null ? quoteDetail.getAfterSales().getId() : null);
        dto.setAmount(quoteDetail.getAmount());
        dto.setUnitaryPrice(quoteDetail.getUnitaryPrice());
        dto.setSubtotal(quoteDetail.getUnitaryPrice().multiply(BigDecimal.valueOf(quoteDetail.getAmount())));
        return dto;
    }

    public static QuoteDetail toEntity(QuoteDetailDto dto , Quote quote, Optional<Product> product, Optional<Service> service, Optional<AfterSales> afterSales) {
        if (dto == null) return null;
        QuoteDetail quoteDetail = new QuoteDetail();
        // Relations managed in Service Layout
        quoteDetail.setIdQuoteDetail(dto.getIdQuoteDetail());
        quoteDetail.setQuote(quote);
        
        if(product != null){
            quoteDetail.setProduct(product.orElse(null));
        }
        if(service != null){
            quoteDetail.setService(service.orElse(null));
        }   
        if(afterSales != null){
            quoteDetail.setAfterSales(afterSales.orElse(null));
        }   
        quoteDetail.setAmount(dto.getAmount());
        quoteDetail.setUnitaryPrice(dto.getUnitaryPrice());
        quoteDetail.setSubtotal(dto.getUnitaryPrice().multiply(BigDecimal.valueOf(dto.getAmount())));
        return quoteDetail;
    }


}
