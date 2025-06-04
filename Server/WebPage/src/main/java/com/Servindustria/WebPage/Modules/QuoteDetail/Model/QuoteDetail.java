package com.Servindustria.WebPage.Modules.QuoteDetail.Model;

import java.math.BigDecimal;

import com.Servindustria.WebPage.Modules.AfterSales.Model.AfterSales;
import com.Servindustria.WebPage.Modules.Product.Model.Product;
import com.Servindustria.WebPage.Modules.Quote.Model.Quote;
import com.Servindustria.WebPage.Modules.Service.Model.Service;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "det_cotizacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuoteDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_det_cotizacion")
    private Long idQuoteDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cotizacion", nullable = false)
    private Quote quote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = true)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servicio", nullable = true)
    private Service service;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_post_venta", nullable = true)
    private AfterSales afterSales;

    @Column(name = "cantidad", nullable = false)
    private Integer amount;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal UnitaryPrice;

    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    public void validateExclusivity() {
        long nonNullCount = (product != null ? 1 : 0) +
                           (service != null ? 1 : 0) +
                           (afterSales != null ? 1 : 0);
        if (nonNullCount != 1) {
            throw new IllegalStateException("Solo una de las referencias (producto, servicio o postVenta) puede estar presente.");
        }
    }
}