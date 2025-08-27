package com.Servindustria.WebPage.Modules.Quote.Service;

import java.math.BigDecimal;
import java.security.Provider.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.Servindustria.WebPage.Exception.ResourceNotFoundException;
import com.Servindustria.WebPage.JWT.JwtService;
import com.Servindustria.WebPage.Modules.Client.Model.Client;
import com.Servindustria.WebPage.Modules.Client.Repository.ClientRepository;
import com.Servindustria.WebPage.Modules.Product.Mapper.ProductMapper;
import com.Servindustria.WebPage.Modules.Product.Model.Product;
import com.Servindustria.WebPage.Modules.Product.Repository.ProductRepository;
import com.Servindustria.WebPage.Modules.Product.Service.ProductService;
import com.Servindustria.WebPage.Modules.Quote.DTO.QuoteDto;
import com.Servindustria.WebPage.Modules.Quote.DTO.QuoteProductsDto;
import com.Servindustria.WebPage.Modules.Quote.Mapper.QuoteMapper;
import com.Servindustria.WebPage.Modules.Quote.Model.Quote;
import com.Servindustria.WebPage.Modules.Quote.Repository.QuoteRepository;
import com.Servindustria.WebPage.Modules.QuoteDetail.DTO.QuoteDetailDto;
import com.Servindustria.WebPage.Modules.QuoteDetail.Mapper.QuoteDetailMapper;
import com.Servindustria.WebPage.Modules.QuoteDetail.Model.QuoteDetail;
import com.Servindustria.WebPage.Modules.QuoteDetail.Service.QuoteDetailService;
import com.Servindustria.WebPage.Modules.Service.Repository.ServiceRepository;
import com.Servindustria.WebPage.Modules.Service.Service.ServiceService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class QuoteService {
    private final QuoteRepository quoteRepository;
    private final QuoteDetailService quoteDetailService;
    private final ClientRepository clientRepository;
    private final JwtService jwtService;
    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);
    private final ServiceRepository serviceRepository;

    //Create
    public QuoteDto createQuote(QuoteDto dto) {
        Client client = clientRepository.findById(dto.getIdClient())
            .orElseThrow(() -> new ResourceNotFoundException("Cliente con ID " + dto.getIdClient() + " no encontrado"));
        Quote quote = QuoteMapper.toEntity(dto, client);
        Quote saved = quoteRepository.save(quote);
        clientRepository.save(client); // Update Client with new Quote and persist changes 
        return QuoteMapper.toDTO(saved);
    }
    
    //Create with token validation and client lookup
    @Transactional
    public QuoteDto createQuoteWithToken(String token, QuoteProductsDto dto) {
        String email = jwtService.getEmailFromToken(token);
        if (email == null || email.isEmpty()) {
            throw new ResourceNotFoundException("Token inválido o no proporcionado");
        }
        Client client = clientRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente con email: "+ email +" no encontrado"));

        Quote quote = new Quote();
        quote.setDateEmi(LocalDateTime.now());
        quote.setDateExpi(LocalDateTime.now().plusDays(7));
        quote.setState("EN_PROCESO");
        BigDecimal subTotal = dto.getProducts().stream()
            .map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getAmount())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        quote.setSubTotal(subTotal);
        quote.setClient(client);
        quote.setDscto(BigDecimal.ZERO);
        quote.setTotal(subTotal);

        Quote savedQuote = quoteRepository.saveAndFlush(quote);

        String code = "QT-" + String.format("%05d", savedQuote.getId());
        savedQuote.setCod(code);
        quoteRepository.saveAndFlush(savedQuote);

           // 1. Separa IDs de productos y servicios
        List<Long> productIds = dto.getProducts().stream()
            .filter(p -> !(p.getIdCategory() == 6 || p.getIdCategory() == 7 || p.getIdCategory() == 8))
            .map(p -> p.getId())
            .collect(Collectors.toList());

        List<Long> serviceIds = dto.getProducts().stream()
            .filter(p -> (p.getIdCategory() == 6 || p.getIdCategory() == 7 || p.getIdCategory() == 8))
            .map(p -> p.getId())
            .collect(Collectors.toList());

        // 2. Obtén todos los productos y servicios necesarios en una sola consulta
        Map<Long, Product> productsMap = productRepository.findAllById(productIds)
            .stream().collect(Collectors.toMap(Product::getId, p -> p));
        Map<Long, com.Servindustria.WebPage.Modules.Service.Model.Service> servicesMap = serviceRepository.findAllById(serviceIds)
            .stream().collect(Collectors.toMap(s -> s.getId(), s -> s));

        // 3. Mapea los detalles usando las entidades ya cargadas
        List<QuoteDetail> detalles = dto.getProducts().stream().map(product -> {
            QuoteDetailDto quoteDetail = new QuoteDetailDto();
            quoteDetail.setIdQuote(savedQuote.getId());
            quoteDetail.setUnitaryPrice(product.getPrice());
            quoteDetail.setAmount(product.getAmount());
            quoteDetail.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(product.getAmount())));
            if (product.getIdCategory() == 6 || product.getIdCategory() == 7 || product.getIdCategory() == 8) {
                quoteDetail.setIdService(product.getId());
                return QuoteDetailMapper.toEntity(
                    quoteDetail, savedQuote, java.util.Optional.empty(), java.util.Optional.ofNullable(servicesMap.get(product.getId())), java.util.Optional.empty()
                );
            } else {
                quoteDetail.setIdProduct(product.getId());
                return QuoteDetailMapper.toEntity(
                    quoteDetail, savedQuote, java.util.Optional.ofNullable(productsMap.get(product.getId())), java.util.Optional.empty(), java.util.Optional.empty()
                );
            }
        }).collect(Collectors.toList());

        // 4. Guarda en batch
        quoteDetailService.createQuoteDetailsBatch(detalles);
        return QuoteMapper.toDTO(savedQuote);
        
    }

    

    //Get by ID
    public QuoteDto getQuoteById(Long id) {
        Quote quote = quoteRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("Cotización con ID " + id + " no encontrada");
                return new ResourceNotFoundException("Cotización con ID " + id + " no encontrada");
            });

        return QuoteMapper.toDTO(quote);
    }

    //Get All
    public List<QuoteDto> getAllQuotes() {
        return quoteRepository.findAll().stream()
            .map(QuoteMapper::toDTO)
            .collect(Collectors.toList());
    }

    //Get by ID Client
    public List<QuoteDto> getQuotesByClientId(Long clientId) {
        return quoteRepository.findByClientId(clientId).stream()
            .map(QuoteMapper::toDTO)
            .collect(Collectors.toList());
    }

    //Update
    public QuoteDto updateQuote(Long id, QuoteDto dto) {
       Quote existingQuote = quoteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cotización con ID " + id + " no encontrada"));

        Client client = clientRepository.findById(dto.getIdClient())
            .orElseThrow(() -> new ResourceNotFoundException("Cliente con ID " + dto.getIdClient() + " no encontrado"));

        Quote updatedQuote = QuoteMapper.toEntity(dto, client);
        updatedQuote.setId(id); //Hold ID original
        Quote saved = quoteRepository.save(updatedQuote);

        // Update Relation
        if (!existingQuote.getClient().getId().equals(client.getId())) {
            existingQuote.getClient().getQuotes().remove(existingQuote);
            client.getQuotes().add(saved);
            clientRepository.save(client);
            clientRepository.save(existingQuote.getClient());
        }
        return QuoteMapper.toDTO(saved);
    }

    //Delete
    public void deleteQuote(Long id) {
        quoteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cotización con ID " + id + " no encontrada"));
        quoteRepository.deleteById(id);
    }

}
