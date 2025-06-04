package com.Servindustria.WebPage.Modules.Invoice.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.Servindustria.WebPage.Modules.Client.Model.Client;
import com.Servindustria.WebPage.Modules.Employee.Model.Employee;
import com.Servindustria.WebPage.Modules.Quote.Model.Quote;
import com.Servindustria.WebPage.Modules.ShippingGuide.Model.ShippingGuide;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "factura")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cod", nullable = false, unique = true, length = 45)
    private String code;

    @Column(name = "fechaemi", nullable = false)
    private LocalDateTime DateEmi;

    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "dscto", nullable = false, precision = 5, scale = 2)
    private BigDecimal dscto = BigDecimal.ZERO;

    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(name = "tipopago", nullable = false, length = 60)
    private String paymentType;

    @Column(name = "modopago", nullable = false, length = 60)
    private String paymentMethod;

    @Column(name = "montopago", nullable = false, precision = 10, scale = 2)
    private BigDecimal paymentAmount;

    @Column(name = "saldopendiente", nullable = false, precision = 10, scale = 2)
    private BigDecimal pendingBalance;

    @OneToOne
    @JoinColumn(name = "id_cotizacion", unique = true)
    private Quote quote;

   
    @OneToOne
    @JoinColumn(name = "id_guia", unique = true)
    private ShippingGuide shippingGuide;
    
    @ManyToOne
    @JoinColumn(name = "id_empleado", nullable = true)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Client client;
}