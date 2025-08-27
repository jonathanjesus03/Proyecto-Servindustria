package com.Servindustria.WebPage.Modules.AfterSales.Controller;

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

import com.Servindustria.WebPage.Modules.AfterSales.DTO.AfterSalesDto;
import com.Servindustria.WebPage.Modules.AfterSales.Mapper.AfterSalesMapper;
import com.Servindustria.WebPage.Modules.AfterSales.Model.AfterSales;
import com.Servindustria.WebPage.Modules.AfterSales.Service.AfterSalesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/afterSales")
public class AfterSalesController {
    private final AfterSalesService afterSalesService;

    @PostMapping
    public ResponseEntity<AfterSalesDto> createAfterSales(@RequestBody AfterSalesDto dto) {
        AfterSales afterSales = AfterSalesMapper.toEntity(dto);
        AfterSales created = afterSalesService.createAfterSales(afterSales);
        return ResponseEntity.ok(AfterSalesMapper.toDto(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AfterSalesDto> getAfterSalesById(@PathVariable Long id) {
        AfterSales afterSales = afterSalesService.getAfterSalesById(id);
        return ResponseEntity.ok(AfterSalesMapper.toDto(afterSales));
    }

    @GetMapping("/Client/{clientId}")
  public ResponseEntity<List<AfterSalesDto>> getAfterSalesByClientId(@PathVariable Long clientId) {
        List<AfterSales> afterSalesList = afterSalesService.getAfterSalesByClientId(clientId);
        return ResponseEntity.ok(afterSalesList.stream().map(AfterSalesMapper::toDto).collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AfterSalesDto> updateAfterSales(@PathVariable Long id, @RequestBody AfterSalesDto dto) {
        AfterSales afterSales = AfterSalesMapper.toEntity(dto);
        AfterSales updated = afterSalesService.updateAfterSales(id, afterSales);
        return ResponseEntity.ok(AfterSalesMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAfterSales(@PathVariable Long id) {
        afterSalesService.deleteAfterSales(id);
        return ResponseEntity.noContent().build();
    }
}
