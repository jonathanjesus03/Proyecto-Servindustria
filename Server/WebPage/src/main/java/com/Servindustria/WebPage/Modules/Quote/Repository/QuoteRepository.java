package com.Servindustria.WebPage.Modules.Quote.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Servindustria.WebPage.Modules.InvoiceDetail.Model.InvoiceDetail;
import com.Servindustria.WebPage.Modules.Quote.Model.Quote;
import com.Servindustria.WebPage.Modules.QuoteDetail.Model.QuoteDetail;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long>{

    @Query("SELECT q FROM Quote q WHERE q.client.id = :clientId")
    List<Quote> findByClientId(@Param("clientId") Long clientId);

    @Query("SELECT MAX(q.id) FROM Quote q")
    Optional<Long> findMaxId();

    @Query("SELECT qdt from QuoteDetail qdt where qdt.quote.id = :quoteid ")
    List<QuoteDetail> findByQuoteI(@Param("quoteid") Long quoteId);

    
    @Query("SELECT idt FROM InvoiceDetail idt WHERE idt.invoice.id = :invoiceid")
    List<InvoiceDetail> findByInvoiceId(@Param("invoiceid") Long invoiceId);

}
