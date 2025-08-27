package com.Servindustria.WebPage.Modules.InvoiceDetail.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Servindustria.WebPage.Modules.InvoiceDetail.Model.InvoiceDetail;

@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long> {

    @Query("SELECT idt FROM InvoiceDetail idt WHERE idt.invoice.id = :invoiceId")
    List<InvoiceDetail> findByInvoiceId(@Param("invoiceId") Long invoiceId);

     
}