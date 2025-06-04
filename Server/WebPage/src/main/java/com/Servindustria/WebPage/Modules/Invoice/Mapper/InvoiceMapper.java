package com.Servindustria.WebPage.Modules.Invoice.Mapper;

import com.Servindustria.WebPage.Modules.Invoice.DTO.InvoiceDto;
import com.Servindustria.WebPage.Modules.Invoice.Model.Invoice;
import com.Servindustria.WebPage.Modules.Quote.Model.Quote;
import com.Servindustria.WebPage.Modules.ShippingGuide.Model.ShippingGuide;
import com.Servindustria.WebPage.Modules.Employee.Model.Employee;

import java.util.Optional;

import com.Servindustria.WebPage.Modules.Client.Model.Client;

public class InvoiceMapper {

    public static InvoiceDto toDto(Invoice invoice) {
        if (invoice == null) return null;
        InvoiceDto dto = new InvoiceDto();
        dto.setId(invoice.getId());
        dto.setCode(invoice.getCode());
        dto.setDateEmi(invoice.getDateEmi());
        dto.setSubtotal(invoice.getSubtotal());
        dto.setDscto(invoice.getDscto());
        dto.setTotal(invoice.getTotal());
        dto.setPaymentType(invoice.getPaymentType());
        dto.setPaymentMethod(invoice.getPaymentMethod());
        dto.setPaymentAmount(invoice.getPaymentAmount());
        dto.setPendingBalance(invoice.getPendingBalance());
        dto.setQuoteId(invoice.getQuote() != null ? invoice.getQuote().getId() : null);
        dto.setShippingGuideId(invoice.getShippingGuide() != null ? invoice.getShippingGuide().getId() : null);
        dto.setEmployeeId(invoice.getEmployee() != null ? invoice.getEmployee().getId() : null);
        dto.setClientId(invoice.getClient() != null ? invoice.getClient().getId() : null);
        return dto;
    }

    public static Invoice toEntity(
            InvoiceDto dto,
            Quote quote,
            Optional<ShippingGuide> shippingGuide,
            Optional<Employee> employee,
            Client client
    ) {
        if (dto == null) return null;
        Invoice invoice = new Invoice();
        invoice.setId(dto.getId());
        invoice.setCode(dto.getCode());
        invoice.setDateEmi(dto.getDateEmi());
        invoice.setSubtotal(dto.getSubtotal());
        invoice.setDscto(dto.getDscto());
        invoice.setTotal(dto.getTotal());
        invoice.setPaymentType(dto.getPaymentType());
        invoice.setPaymentMethod(dto.getPaymentMethod());
        invoice.setPaymentAmount(dto.getPaymentAmount());
        invoice.setPendingBalance(dto.getPendingBalance());
        invoice.setQuote(quote);
        if (shippingGuide != null) invoice.setShippingGuide(shippingGuide.orElse(null));
        if (employee != null) invoice.setEmployee(employee.orElse(null));
        invoice.setClient(client);
        return invoice;
    }
}