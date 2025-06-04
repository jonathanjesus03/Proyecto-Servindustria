package com.Servindustria.WebPage.Modules.InvoiceDetail.Service;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import com.Servindustria.WebPage.Modules.AfterSales.Model.AfterSales;
import com.Servindustria.WebPage.Modules.AfterSales.Repository.AfterSalesRepository;
import com.Servindustria.WebPage.Modules.InvoiceDetail.DTO.InvoiceDetailDto;
import com.Servindustria.WebPage.Modules.InvoiceDetail.Mapper.InvoiceDetailMapper;
import com.Servindustria.WebPage.Modules.InvoiceDetail.Model.InvoiceDetail;
import com.Servindustria.WebPage.Modules.InvoiceDetail.Repository.InvoiceDetailRepository;
import com.Servindustria.WebPage.Modules.Product.Model.Product;
import com.Servindustria.WebPage.Modules.Product.Repository.ProductRepository;
import com.Servindustria.WebPage.Modules.Service.Model.Service;
import com.Servindustria.WebPage.Modules.Service.Repository.ServiceRepository;
import com.Servindustria.WebPage.Modules.Invoice.Model.Invoice;
import com.Servindustria.WebPage.Modules.Invoice.Repository.InvoiceRepository;

import lombok.RequiredArgsConstructor;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class InvoiceDetailService {
    private final InvoiceDetailRepository invoiceDetailRepository;
    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;
    private final ServiceRepository serviceRepository;
    private final AfterSalesRepository afterSalesRepository;

    public InvoiceDetailDto createInvoiceDetail(InvoiceDetailDto invoiceDetailDto) {
        Invoice invoice = invoiceRepository.findById(invoiceDetailDto.getInvoiceId())
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found with id: " + invoiceDetailDto.getInvoiceId()));

        Optional<Product> product = invoiceDetailDto.getProductId() != null
                ? productRepository.findById(invoiceDetailDto.getProductId())
                : Optional.empty();

        Optional<Service> service = invoiceDetailDto.getServiceId() != null
                ? serviceRepository.findById(invoiceDetailDto.getServiceId())
                : Optional.empty();

        Optional<AfterSales> afterSales = invoiceDetailDto.getAfterSalesId() != null
                ? afterSalesRepository.findById(invoiceDetailDto.getAfterSalesId())
                : Optional.empty();

        InvoiceDetail invoiceDetail = InvoiceDetailMapper.toEntity(invoiceDetailDto, invoice, product, service, afterSales);
        InvoiceDetail savedInvoiceDetail = invoiceDetailRepository.save(invoiceDetail);

        return InvoiceDetailMapper.toDto(savedInvoiceDetail);
    }

    public List<InvoiceDetailDto> getAllInvoiceDetails() {
        List<InvoiceDetail> invoiceDetails = invoiceDetailRepository.findAll();
        List<InvoiceDetailDto> dtos = new ArrayList<>();
        for (InvoiceDetail invoiceDetail : invoiceDetails) {
            dtos.add(InvoiceDetailMapper.toDto(invoiceDetail));
        }
        return dtos;
    }

    public InvoiceDetailDto getInvoiceDetailById(Long id) {
        InvoiceDetail invoiceDetail = invoiceDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("InvoiceDetail not found with id: " + id));
        return InvoiceDetailMapper.toDto(invoiceDetail);
    }

    public InvoiceDetailDto updateInvoiceDetail(Long id, InvoiceDetailDto invoiceDetailDto) {
        InvoiceDetail existing = invoiceDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("InvoiceDetail not found with id: " + id));

        Invoice invoice = invoiceRepository.findById(invoiceDetailDto.getInvoiceId())
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found with id: " + invoiceDetailDto.getInvoiceId()));

        Optional<Product> product = invoiceDetailDto.getProductId() != null
                ? productRepository.findById(invoiceDetailDto.getProductId())
                : Optional.empty();

        Optional<Service> service = invoiceDetailDto.getServiceId() != null
                ? serviceRepository.findById(invoiceDetailDto.getServiceId())
                : Optional.empty();

        Optional<AfterSales> afterSales = invoiceDetailDto.getAfterSalesId() != null
                ? afterSalesRepository.findById(invoiceDetailDto.getAfterSalesId())
                : Optional.empty();

        InvoiceDetail updated = InvoiceDetailMapper.toEntity(invoiceDetailDto, invoice, product, service, afterSales);
        updated.setId(existing.getId());
        InvoiceDetail saved = invoiceDetailRepository.save(updated);

        return InvoiceDetailMapper.toDto(saved);
    }

    public void deleteInvoiceDetail(Long id) {
        if (!invoiceDetailRepository.existsById(id)) {
            throw new IllegalArgumentException("InvoiceDetail not found with id: " + id);
        }
        invoiceDetailRepository.deleteById(id);
    }
}