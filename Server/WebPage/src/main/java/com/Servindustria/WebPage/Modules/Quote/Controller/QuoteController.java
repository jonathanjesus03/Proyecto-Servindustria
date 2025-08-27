package com.Servindustria.WebPage.Modules.Quote.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Servindustria.WebPage.Modules.Quote.DTO.QuoteDto;
import com.Servindustria.WebPage.Modules.Quote.DTO.QuoteProductsDto;
import com.Servindustria.WebPage.Modules.Quote.Service.QuoteAsyncService;
import com.Servindustria.WebPage.Modules.Quote.Service.QuoteService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/quotes")
public class QuoteController {
    private final QuoteService quoteService;
    private final QuoteAsyncService quoteAsyncService;
    // Create
    @PostMapping
    public ResponseEntity<Void> createQuote(@CookieValue(value = "token") String token , @RequestBody QuoteProductsDto dto) {
        QuoteDto quoteSaved = quoteService.createQuoteWithToken(token, dto);
        quoteAsyncService.processQuoteAsync(quoteSaved.getId());
        quoteAsyncService.sendQuoteEmail(quoteSaved.getId());
        return ResponseEntity.ok().build();
    }

    // Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<QuoteDto> getQuoteById(@PathVariable Long id) {
        QuoteDto dto = quoteService.getQuoteById(id);
        return ResponseEntity.ok(dto);
    }

    // Get Quotes by Client ID
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<QuoteDto>> getByIdClient(@PathVariable Long clientId) {
        List<QuoteDto> quotes = quoteService.getQuotesByClientId(clientId);
        return ResponseEntity.ok(quotes);
    }

    // Get All
    @GetMapping
    public ResponseEntity<List<QuoteDto>> getAllQuotes() {
        List<QuoteDto> list = quoteService.getAllQuotes();
        return ResponseEntity.ok(list);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<QuoteDto> updateQuote(@PathVariable Long id, @RequestBody QuoteDto dto) {
        QuoteDto updated = quoteService.updateQuote(id, dto);
        return ResponseEntity.ok(updated);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuote(@PathVariable Long id) {
        quoteService.deleteQuote(id);
        return ResponseEntity.ok().build();
    }
}
