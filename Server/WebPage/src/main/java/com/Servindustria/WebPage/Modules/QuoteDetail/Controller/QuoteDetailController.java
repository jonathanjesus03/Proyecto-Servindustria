package com.Servindustria.WebPage.Modules.QuoteDetail.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Servindustria.WebPage.Modules.QuoteDetail.DTO.QuoteDetailDto;
import com.Servindustria.WebPage.Modules.QuoteDetail.Mapper.QuoteDetailMapper;
import com.Servindustria.WebPage.Modules.QuoteDetail.Model.QuoteDetail;
import com.Servindustria.WebPage.Modules.QuoteDetail.Service.QuoteDetailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quoteDetail")
public class QuoteDetailController {
    private final QuoteDetailService quoteDetailService;

    @PostMapping
    public ResponseEntity<QuoteDetailDto> createQuoteDetail(@RequestBody QuoteDetailDto dto) {
        return ResponseEntity.ok(QuoteDetailMapper.toDto(quoteDetailService.createQuoteDetail((dto))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuoteDetailDto> getQuoteDetailById(@PathVariable Long id) {
        return ResponseEntity.ok(QuoteDetailMapper.toDto(quoteDetailService.getQuoteDetailById(id)));
    }

    @GetMapping("/Quote/{quoteId}")
    public ResponseEntity<List<QuoteDetailDto>> getQuoteDetailsByCotizacionId(@PathVariable Long quoteId) {
        List<QuoteDetail> quoteDetails = quoteDetailService.getQuoteDetailsByQuoteId(quoteId);
        return ResponseEntity.ok(quoteDetails.stream().map(QuoteDetailMapper::toDto).collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuoteDetailDto> updateQuoteDetail(@PathVariable Long id, @RequestBody QuoteDetailDto dto) {        
        QuoteDetail updated = quoteDetailService.updateQuoteDetail(id, dto);
        return ResponseEntity.ok(QuoteDetailMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuoteDetail(@PathVariable Long id) {
        quoteDetailService.deleteQuoteDetail(id);
        return ResponseEntity.noContent().build();
    }
}
