package com.Servindustria.WebPage.Modules.InvoiceDetail.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

import com.Servindustria.WebPage.Modules.AfterSales.Model.AfterSales;
import com.Servindustria.WebPage.Modules.Product.Model.Product;
import com.Servindustria.WebPage.Modules.Invoice.Model.Invoice;
import com.Servindustria.WebPage.Modules.Service.Model.Service;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "det_factura")
public class InvoiceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_det_factura")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_factura", nullable = false)
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id_servicio")
    private Service service;

    @ManyToOne
    @JoinColumn(name = "id_post_venta")
    private AfterSales afterSales;

    @Column(name = "cantidad", nullable = false)
    private Integer amount;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitaryPrice;

    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;
}