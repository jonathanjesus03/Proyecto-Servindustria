package com.Servindustria.WebPage.Modules.Quote.Service;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.Servindustria.WebPage.Exception.ResourceNotFoundException;
import com.Servindustria.WebPage.Modules.Invoice.Service.InvoiceService;
import com.Servindustria.WebPage.Modules.Quote.Model.Quote;
import com.Servindustria.WebPage.Modules.Quote.Repository.QuoteRepository;


import java.io.FileOutputStream;
import java.io.OutputStream;

@Service
@RequiredArgsConstructor
public class QuoteAsyncService {
    private final QuoteDocument quoteDocument;
    private final QuoteEmail quoteEmail;
    private final InvoiceService invoiceService;
    private final QuoteRepository quoteRepository;
    private static final Logger logger = LoggerFactory.getLogger(QuoteAsyncService.class); 


    public void processQuoteAsync(Long quoteId) {
        generateDocuments(quoteId);
        createInvoiceFromQuote(quoteId);
    }

    public void generateDocuments(Long quoteId) {
        Quote quote = quoteRepository.findById(quoteId)
            .orElseThrow(() -> new ResourceNotFoundException("Cotización no encontrada"));

    try {
                OutputStream os = new FileOutputStream("C:/Users/Samsung/Desktop/Proyecto-Servindustria/Documents/quote-" + quote.getCod() + ".pdf");
                quoteDocument.generateQuotePdf(quote.getId(), os);
                os.close();
                OutputStream os2 = new FileOutputStream("C:/Users/Samsung/Desktop/Proyecto-Servindustria/Documents/quote-" + quote.getCod() + ".docx");
                quoteDocument.generateQuoteWord(quote.getId(), os2);
                os2.close();
            } catch (Exception e) {
            System.err.println("Error al generar documentos para la cotización con ID: " + quoteId);
                e.printStackTrace();
                logger.error("Error al generar documentos para la cotización con ID: {}", quoteId, e);
            }
        }
    
    @Async
    public void sendQuoteEmail(Long quoteId) {
        Quote quote = quoteRepository.findById(quoteId)
            .orElseThrow(() -> new ResourceNotFoundException("Cotización no encontrada"));
        
        quoteEmail.SendEmailQuote(quote.getId());
    }

    public void createInvoiceFromQuote(Long quoteId) {
        Quote quote = quoteRepository.findById(quoteId)
            .orElseThrow(() -> new ResourceNotFoundException("Cotización no encontrada"));

        invoiceService.createInvoiceFromQuote(quote.getId(), "CONTADO", "EFECTIVO", quote.getTotal());
    }
        
}