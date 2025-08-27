package com.Servindustria.WebPage.Modules.InvoiceDetail.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Servindustria.WebPage.Modules.InvoiceDetail.DTO.InvoiceDetailDto;
import com.Servindustria.WebPage.Modules.InvoiceDetail.Service.InvoiceDetailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/invoiceDetail")
public class InvoiceDetailController {
    private final InvoiceDetailService invoiceDetailService;
    
    @PostMapping
    public ResponseEntity<InvoiceDetailDto> createInvoiceDetail(@RequestBody InvoiceDetailDto invoiceDetailDto) {
        InvoiceDetailDto createdInvoiceDetail = invoiceDetailService.createInvoiceDetail(invoiceDetailDto);
        return ResponseEntity.ok(createdInvoiceDetail);
    }

    @GetMapping
    public ResponseEntity<List<InvoiceDetailDto>> getAllInvoiceDetails() {
        List<InvoiceDetailDto> invoiceDetails = invoiceDetailService.getAllInvoiceDetails();
        return ResponseEntity.ok(invoiceDetails);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDetailDto> getInvoiceDetailById(@PathVariable Long id) {
        InvoiceDetailDto invoiceDetail = invoiceDetailService.getInvoiceDetailById(id);
        return ResponseEntity.ok(invoiceDetail);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDetailDto> updateInvoiceDetail(@PathVariable Long id, @RequestBody InvoiceDetailDto invoiceDetailDto) {
        InvoiceDetailDto updatedInvoiceDetail = invoiceDetailService.updateInvoiceDetail(id, invoiceDetailDto);
        return ResponseEntity.ok(updatedInvoiceDetail);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoiceDetail(@PathVariable Long id) {
        invoiceDetailService.deleteInvoiceDetail(id);
        return ResponseEntity.noContent().build();
    }
    
}
