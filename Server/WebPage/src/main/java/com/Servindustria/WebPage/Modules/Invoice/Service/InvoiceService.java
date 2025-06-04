package com.Servindustria.WebPage.Modules.Invoice.Service;

import com.Servindustria.WebPage.Modules.Invoice.DTO.InvoiceDto;
import com.Servindustria.WebPage.Modules.Invoice.Mapper.InvoiceMapper;
import com.Servindustria.WebPage.Modules.Invoice.Model.Invoice;
import com.Servindustria.WebPage.Modules.Invoice.Repository.InvoiceRepository;
import com.Servindustria.WebPage.Modules.InvoiceDetail.DTO.InvoiceDetailDto;
import com.Servindustria.WebPage.Modules.InvoiceDetail.Mapper.InvoiceDetailMapper;
import com.Servindustria.WebPage.Modules.InvoiceDetail.Model.InvoiceDetail;
import com.Servindustria.WebPage.Modules.InvoiceDetail.Repository.InvoiceDetailRepository;
import com.Servindustria.WebPage.Modules.Quote.Model.Quote;
import com.Servindustria.WebPage.Modules.Quote.Repository.QuoteRepository;
import com.Servindustria.WebPage.Modules.QuoteDetail.Model.QuoteDetail;
import com.Servindustria.WebPage.Modules.QuoteDetail.Repository.QuoteDetailRepository;
import com.Servindustria.WebPage.Modules.ShippingGuide.Model.ShippingGuide;

import jakarta.transaction.Transactional;

import com.Servindustria.WebPage.Modules.Employee.Model.Employee;
import com.Servindustria.WebPage.Modules.Client.Model.Client;
import com.Servindustria.WebPage.Modules.Client.Repository.ClientRepository;
import com.Servindustria.WebPage.Exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceDocument invoiceDocumentService;
    private final InvoiceDetailRepository invoiceDetailRepository;
    private final InvoiceRepository invoiceRepository;
    private final QuoteRepository quoteRepository;
    private final ClientRepository clientRepository;
    private final QuoteDetailRepository quoteDetailRepository;

//    private final ShippingGuideRepository shippingGuideRepository;
//    private final EmployeeRepository employeeRepository;

    public InvoiceDto createInvoice(InvoiceDto dto) {

        Quote quote = quoteRepository.findById(dto.getQuoteId())
                .orElseThrow(() -> new ResourceNotFoundException("Quote with ID " + dto.getQuoteId() + " not found"));

        Optional<ShippingGuide> shippingGuide = null;
        if (dto.getShippingGuideId() != null) {
            //shippingGuide = shippingGuideRepository.findById(dto.getShippingGuideId())
              //  .orElseThrow(() -> new ResourceNotFoundException("ShippingGuide with ID " + dto.getShippingGuideId() + " not found"));
        }

        Optional<Employee> employee = null;
        
        if(dto.getEmployeeId() != null) {
            //employeeRepository.findById(dto.getEmployeeId())
            //.orElseThrow(() -> new ResourceNotFoundException("Employee with ID " + dto.getEmployeeId() + " not found"));
        }
        Client client = clientRepository.findById(dto.getClientId())
            .orElseThrow(() -> new ResourceNotFoundException("Client with ID " + dto.getClientId() + " not found"));

        Invoice invoice = InvoiceMapper.toEntity(dto, quote, shippingGuide, employee, client);
        Invoice saved = invoiceRepository.save(invoice);
        return InvoiceMapper.toDto(saved);
    }

    @Transactional
    public InvoiceDto createInvoiceFromQuote(Long quoteId, String paymentType, String paymentMethod, BigDecimal paymentAmount) {
        Quote quote = quoteRepository.findById(quoteId)
        .orElseThrow(() -> new ResourceNotFoundException("Cotización no encontrada"));
        List<QuoteDetail> quoteDetails = quoteDetailRepository.findByQuoteId(quoteId);

        Long lastId = invoiceRepository.findMaxId().orElse(0L);
        String code = "IV-0" + (lastId + 1);

        Invoice invoice = new Invoice();
        invoice.setCode(code);
        invoice.setQuote(quote);
        invoice.setClient(quote.getClient());
        invoice.setDateEmi(LocalDateTime.now());
        invoice.setSubtotal(quote.getSubTotal());
        invoice.setDscto(quote.getDscto());
        invoice.setTotal(quote.getTotal());

        invoice.setPaymentType(paymentType);
        invoice.setPaymentMethod(paymentMethod);
        invoice.setPaymentAmount(paymentAmount);
        invoice.setPendingBalance(invoice.getTotal().subtract(paymentAmount != null ? paymentAmount : BigDecimal.ZERO));

        Invoice savedInvoice = invoiceRepository.save(invoice);

        for (QuoteDetail qd : quoteDetails) {
            InvoiceDetail invoiceDetail = new InvoiceDetail();
            invoiceDetail.setInvoice(savedInvoice);
            invoiceDetail.setProduct(qd.getProduct());
            invoiceDetail.setService(qd.getService());
            invoiceDetail.setAfterSales(qd.getAfterSales());
            invoiceDetail.setAmount(qd.getAmount());
            invoiceDetail.setUnitaryPrice(qd.getUnitaryPrice());
            invoiceDetail.setSubtotal(qd.getSubtotal());
            invoiceDetailRepository.save(invoiceDetail);
    }

    try {
        OutputStream os = new FileOutputStream("C:/Users/Samsung/Desktop/Proyecto-Servindustria/Documents/factura-"+ savedInvoice.getCode() +".xlsx");
        invoiceDocumentService.generateInvoiceExcel(savedInvoice.getId(), os);
        os.close();
        OutputStream os2 = new FileOutputStream("C:/Users/Samsung/Desktop/Proyecto-Servindustria/Documents/factura-"+ savedInvoice.getCode() +".pdf");
        invoiceDocumentService.generateInvoicePdf(savedInvoice.getId(), os2);
        os2.close();
    
    } catch (IOException e) {
        e.printStackTrace();
    }

    return InvoiceMapper.toDto(savedInvoice);
}

    public InvoiceDto getById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Invoice with ID " + id + " not found"));
        return InvoiceMapper.toDto(invoice);
    }

    public List<InvoiceDto> getAll() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return invoices.stream()
            .map(InvoiceMapper::toDto)
            .collect(Collectors.toList());
    }

    public InvoiceDto update(Long id, InvoiceDto dto) {
        Invoice existing = invoiceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Invoice with ID " + id + " not found"));

        Quote quote = quoteRepository.findById(dto.getQuoteId())
            .orElseThrow(() -> new ResourceNotFoundException("Quote with ID " + dto.getQuoteId() + " not found"));

        Optional<ShippingGuide> shippingGuide = null;
        if (dto.getShippingGuideId() != null) {
            //shippingGuide = shippingGuideRepository.findById(dto.getShippingGuideId())
              //  .orElseThrow(() -> new ResourceNotFoundException("ShippingGuide with ID " + dto.getShippingGuideId() + " not found"));
        }

        Optional<Employee> employee = null;
        if(dto.getEmployeeId() != null) {
            //employeeRepository.findById(dto.getEmployeeId())
            //.orElseThrow(() -> new ResourceNotFoundException("Employee with ID " + dto.getEmployeeId() + " not found"));
        }

        Client client = clientRepository.findById(dto.getClientId())
            .orElseThrow(() -> new ResourceNotFoundException("Client with ID " + dto.getClientId() + " not found"));

        Invoice updated = InvoiceMapper.toEntity(dto, quote, shippingGuide, employee, client);
        updated.setId(existing.getId());
        Invoice saved = invoiceRepository.save(updated);
        return InvoiceMapper.toDto(saved);
    }

    public void delete(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Invoice with ID " + id + " not found"));
        invoiceRepository.delete(invoice);
    }

    public List<InvoiceDetailDto> getInvoiceDetails(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
            .orElseThrow(() -> new ResourceNotFoundException("Invoice with ID " + invoiceId + " not found"));
        List<InvoiceDetail> details = invoiceDetailRepository.findByInvoiceId(invoiceId);
        return details.stream()
            .map(InvoiceDetailMapper::toDto)
            .collect(Collectors.toList());
    }
}