package com.Servindustria.WebPage.Modules.Invoice.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Servindustria.WebPage.Modules.Invoice.Model.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    
    @Query("SELECT MAX(i.id) FROM Invoice i")
    Optional<Long> findMaxId();

}
