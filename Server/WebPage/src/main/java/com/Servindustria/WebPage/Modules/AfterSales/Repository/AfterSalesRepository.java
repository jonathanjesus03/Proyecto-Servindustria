package com.Servindustria.WebPage.Modules.AfterSales.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Servindustria.WebPage.Modules.AfterSales.Model.AfterSales;
@Repository
public interface AfterSalesRepository extends JpaRepository<AfterSales, Long> {
    @Query("SELECT a FROM AfterSales a WHERE a.client.id = :clientId")
    List<AfterSales> findByClientId(@Param("clientId") Long clientId);
}
