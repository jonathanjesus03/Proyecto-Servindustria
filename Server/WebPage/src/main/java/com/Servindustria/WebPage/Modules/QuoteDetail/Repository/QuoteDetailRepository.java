package com.Servindustria.WebPage.Modules.QuoteDetail.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Servindustria.WebPage.Modules.QuoteDetail.Model.QuoteDetail;

@Repository
public interface QuoteDetailRepository extends JpaRepository<QuoteDetail, Long>{
    @Query("SELECT qd FROM QuoteDetail qd WHERE qd.quote.id = :quoteId")
    List<QuoteDetail> findByQuoteId(@Param("quoteId") Long quoteId);

    @Query("SELECT qd FROM QuoteDetail qd "
     + "LEFT JOIN FETCH qd.product "
     + "LEFT JOIN FETCH qd.service "
     + "WHERE qd.quote.id = :quoteId")
    List<QuoteDetail> findByQuoteIdWithProductAndService(@Param("quoteId") Long quoteId);
}
