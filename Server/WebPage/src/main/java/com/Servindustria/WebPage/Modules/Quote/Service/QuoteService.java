package com.Servindustria.WebPage.Modules.Quote.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.Servindustria.WebPage.Exception.ResourceNotFoundException;
import com.Servindustria.WebPage.JWT.JwtService;
import com.Servindustria.WebPage.Modules.Client.Model.Client;
import com.Servindustria.WebPage.Modules.Client.Repository.ClientRepository;
import com.Servindustria.WebPage.Modules.Invoice.Service.InvoiceService;
import com.Servindustria.WebPage.Modules.Quote.DTO.QuoteDto;
import com.Servindustria.WebPage.Modules.Quote.DTO.QuoteProductsDto;
import com.Servindustria.WebPage.Modules.Quote.Mapper.QuoteMapper;
import com.Servindustria.WebPage.Modules.Quote.Model.Quote;
import com.Servindustria.WebPage.Modules.Quote.Repository.QuoteRepository;
import com.Servindustria.WebPage.Modules.QuoteDetail.DTO.QuoteDetailDto;
import com.Servindustria.WebPage.Modules.QuoteDetail.Service.QuoteDetailService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class QuoteService {
    private final QuoteRepository quoteRepository;
    private final QuoteDetailService quoteDetailService;
    private final ClientRepository clientRepository;
    private final JwtService jwtService;
    private final InvoiceService invoiceService;
    private final QuoteDocument quoteDocument;

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
    public QuoteDto createQuoteWithToken(String token, QuoteProductsDto dto) {
        String email = jwtService.getEmailFromToken(token);
        if (email == null || email.isEmpty()) {
            throw new ResourceNotFoundException("Token inválido o no proporcionado");
        }
        Client client = clientRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente con email: "+ email +" no encontrado"));

        Long lastId = quoteRepository.findMaxId().orElse(0L);
        String code = "QT-0" + (lastId + 1);

       Quote quote = new Quote();
        quote.setCod(code);
        quote.setDateEmi(LocalDateTime.now());
        quote.setDateExpi(LocalDateTime.now().plusDays(7));
        quote.setState("EN_PROCESO");
        BigDecimal subTotal = dto.getProducts().stream()
            .map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getAmount()))
            ).reduce(BigDecimal.ZERO, BigDecimal::add);
        quote.setSubTotal(subTotal);
        quote.setClient(client);
        quote.setDscto(BigDecimal.valueOf(0));
        quote.setTotal(subTotal.subtract(quote.getDscto()));
        Quote quoteToSave = quoteRepository.save(quote);
        clientRepository.save(client);

        //Create Quote Details
        dto.getProducts().forEach((product) -> {
            QuoteDetailDto quoteDetail = new QuoteDetailDto();
            quoteDetail.setIdQuote(quoteToSave.getId());
            quoteDetail.setUnitaryPrice(product.getPrice());
            quoteDetail.setAmount(product.getAmount());
            quoteDetail.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(product.getAmount())));
            switch( (int) product.getIdCategory()){
                case 6, 7, 8: 
                    quoteDetail.setIdService(product.getId());
                    break;
                default:
                    quoteDetail.setIdProduct(product.getId());
            }
            
            quoteDetailService.createQuoteDetail(quoteDetail);
        });

        try {
        OutputStream os = new FileOutputStream("C:/Users/Samsung/Desktop/Proyecto-Servindustria/Documents/quote-"+ quoteToSave.getCod() +".pdf");
        quoteDocument.generateQuotePdf(quoteToSave.getId(), os);
        os.close();
        OutputStream os2 = new FileOutputStream("C:/Users/Samsung/Desktop/Proyecto-Servindustria/Documents/quote-"+ quoteToSave.getCod() +".docx");
        quoteDocument.generateQuoteWord(quoteToSave.getId(), os2);
        os2.close();
    
    } catch (IOException e) {
        e.printStackTrace();
    }
        invoiceService.createInvoiceFromQuote(quoteToSave.getId(), "CONTADO", "EFECTIVO", quoteToSave.getTotal());
        return QuoteMapper.toDTO(quoteToSave);
    }   

    //Get by ID
    public QuoteDto getQuoteById(Long id) {
        Quote Quote = quoteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cotización con ID " + id + " no encontrada"));

        return QuoteMapper.toDTO(Quote);
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
