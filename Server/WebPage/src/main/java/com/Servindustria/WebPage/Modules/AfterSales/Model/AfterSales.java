package com.Servindustria.WebPage.Modules.AfterSales.Model;

import com.Servindustria.WebPage.Modules.Client.Model.Client;
import com.Servindustria.WebPage.Modules.Product.Model.Product;
import com.Servindustria.WebPage.Modules.Service.Model.Service;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "post_venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AfterSales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cod", length = 45)
    private String cod;

    @Column(name = "tipo_servicio", nullable = false, length = 45)
    private String ServiceType;

    @Column(name = "fechavcto", nullable = false)
    private LocalDateTime dateExpi ;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "estado", nullable = false, length = 45)
    private String state;

    @Column(name = "cantidad", nullable = false)
    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servicio", nullable = false)
    private Service service;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Client client;
}