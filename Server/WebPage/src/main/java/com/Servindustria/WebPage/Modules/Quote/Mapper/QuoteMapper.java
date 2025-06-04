package com.Servindustria.WebPage.Modules.Quote.Mapper;

import com.Servindustria.WebPage.Modules.Client.Model.Client;
import com.Servindustria.WebPage.Modules.Quote.DTO.QuoteDto;
import com.Servindustria.WebPage.Modules.Quote.Model.Quote;

public class QuoteMapper {

    public static QuoteDto toDTO(Quote quote) {
        QuoteDto dto = new QuoteDto();
        dto.setId(quote.getId());
        dto.setCod(quote.getCod());
        dto.setDateEmi(quote.getDateEmi());
        dto.setDateExpi(quote.getDateExpi());
        dto.setState(quote.getState());
        dto.setSubTotal(quote.getSubTotal());
        dto.setDscto(quote.getDscto());
        dto.setTotal(quote.getTotal());
        dto.setIdClient(quote.getClient().getId());
        return dto;
    }

    public static Quote toEntity(QuoteDto dto, Client client) {
        Quote quote = new Quote();
        quote.setId(dto.getId());
        quote.setCod(dto.getCod());
        quote.setDateEmi(dto.getDateEmi());
        quote.setDateExpi(dto.getDateExpi());
        quote.setState(dto.getState());
        quote.setSubTotal(dto.getSubTotal());
        quote.setDscto(dto.getDscto());
        quote.setTotal(dto.getTotal());
        quote.setClient(client);

        if (!client.getQuotes().contains(quote)) {
            client.getQuotes().add(quote);
        }
        return quote;
    }
}
