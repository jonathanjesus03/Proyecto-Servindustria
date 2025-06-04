package com.Servindustria.WebPage.Modules.QuoteDetail.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.Servindustria.WebPage.Exception.ResourceNotFoundException;
import com.Servindustria.WebPage.Modules.AfterSales.Model.AfterSales;
import com.Servindustria.WebPage.Modules.AfterSales.Repository.AfterSalesRepository;
import com.Servindustria.WebPage.Modules.Product.Model.Product;
import com.Servindustria.WebPage.Modules.Product.Repository.ProductRepository;
import com.Servindustria.WebPage.Modules.Quote.Model.Quote;
import com.Servindustria.WebPage.Modules.Quote.Repository.QuoteRepository;
import com.Servindustria.WebPage.Modules.QuoteDetail.DTO.QuoteDetailDto;
import com.Servindustria.WebPage.Modules.QuoteDetail.Mapper.QuoteDetailMapper;
import com.Servindustria.WebPage.Modules.QuoteDetail.Model.QuoteDetail;
import com.Servindustria.WebPage.Modules.QuoteDetail.Repository.QuoteDetailRepository;
import com.Servindustria.WebPage.Modules.Service.Model.Service;
import com.Servindustria.WebPage.Modules.Service.Repository.ServiceRepository;

import lombok.RequiredArgsConstructor;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class QuoteDetailService {
    private final QuoteDetailRepository quoteDetailRepository;
    private final QuoteRepository quoteRepository;
    private final ProductRepository productRepository;
    private final ServiceRepository serviceRepository;
    private final AfterSalesRepository afterSalesRepository;

    public QuoteDetail createQuoteDetail(QuoteDetailDto quoteDetailDto) {
        Quote quote = quoteRepository.findById(quoteDetailDto.getIdQuote())
            .orElseThrow(() -> new ResourceNotFoundException("Cotización con ID " + quoteDetailDto.getIdQuote() + " no encontrada"));

        Product product = null;
        Service service = null;
        AfterSales afterSales = null;

        if (quoteDetailDto.getIdProduct() != null) {
            System.out.println("ID Producto: " + quoteDetailDto.getIdProduct());
            product = productRepository.findById(quoteDetailDto.getIdProduct())
                .orElseThrow(() -> new ResourceNotFoundException("Product con ID " + quoteDetailDto.getIdProduct() + " no encontrado"));
        }

        if (quoteDetailDto.getIdService() != null) {
            service = serviceRepository.findById(quoteDetailDto.getIdService())
                .orElseThrow(() -> new ResourceNotFoundException("Service con ID " + quoteDetailDto.getIdService() + " no encontrado"));
        }


        /* We have to validate PostSell after the advance second presentation
        if (quoteDetailDto.getIdService() != null) {
            afterSales = afterSalesRepository.findById(quoteDetailDto.getIdService())
                .orElseThrow(() -> new ResourceNotFoundException("AfterSales con ID " + quoteDetailDto.getIdService() + " no encontrado"));
        }
        */

        QuoteDetail quoteDetail = QuoteDetailMapper.toEntity(
            quoteDetailDto,
            quote,
            product != null ? Optional.of(product) : Optional.empty(),
            service != null ? Optional.of(service) : Optional.empty(),
            afterSales != null ? Optional.of(afterSales) : Optional.empty()
        );
        quoteDetail.validateExclusivity();
        return quoteDetailRepository.save(quoteDetail);
    }

    public QuoteDetail getQuoteDetailById(Long id) {
        return quoteDetailRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Detalle de cotización con ID " + id + " no encontrado"));
    }

    public List<QuoteDetail> getQuoteDetailsByQuoteId(Long quoteId) {
        return quoteDetailRepository.findByQuoteId(quoteId);
    }

    public QuoteDetail updateQuoteDetail(Long id, QuoteDetailDto quoteDetailDto) {
        QuoteDetail existingQuoteDetail = quoteDetailRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Detalle de cotización con ID " + id + " no encontrado"));

        // Actualizar relaciones
        Quote quote = quoteRepository.findById(quoteDetailDto.getIdQuote())
            .orElseThrow(() -> new ResourceNotFoundException("Cotización con ID " + quoteDetailDto.getIdQuote() + " no encontrada"));

        Product product = null;
        Service service = null;
        AfterSales afterSales = null;

        if (quoteDetailDto.getIdProduct() != null) {
            product = productRepository.findById(quoteDetailDto.getIdProduct())
                .orElseThrow(() -> new ResourceNotFoundException("Product con ID " + quoteDetailDto.getIdProduct() + " no encontrado"));
        }

        if (quoteDetailDto.getIdService() != null) {
            service = serviceRepository.findById(quoteDetailDto.getIdService())
                .orElseThrow(() -> new ResourceNotFoundException("Service con ID " + quoteDetailDto.getIdService() + " no encontrado"));
        }

        if (quoteDetailDto.getIdService() != null) {
            afterSales = afterSalesRepository.findById(quoteDetailDto.getIdService())
                .orElseThrow(() -> new ResourceNotFoundException("AfterSales con ID " + quoteDetailDto.getIdService() + " no encontrado"));
        }

        // Actualizar campos básicos
        existingQuoteDetail.setQuote(quote);
        existingQuoteDetail.setProduct(product);
        existingQuoteDetail.setService(service);
        existingQuoteDetail.setAfterSales(afterSales);
        existingQuoteDetail.setAmount(quoteDetailDto.getAmount());
        existingQuoteDetail.setUnitaryPrice(quoteDetailDto.getUnitaryPrice());

        existingQuoteDetail.validateExclusivity();

        existingQuoteDetail.setSubtotal(existingQuoteDetail.getUnitaryPrice().multiply(BigDecimal.valueOf(existingQuoteDetail.getAmount())));

        return quoteDetailRepository.save(existingQuoteDetail);
    }

    public void deleteQuoteDetail(Long id) {
        if (!quoteDetailRepository.existsById(id)) {
            throw new ResourceNotFoundException("Detalle de cotización con ID " + id + " no encontrado");
        }
        quoteDetailRepository.deleteById(id);
    }
}
