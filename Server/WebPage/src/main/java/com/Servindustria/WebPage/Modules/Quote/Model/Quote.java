package com.Servindustria.WebPage.Modules.Quote.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.Servindustria.WebPage.Modules.Client.Model.Client;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cotizacion")
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cod", nullable = true, unique = true, length = 45)
    private String cod;
    @Column(name = "fechaemi", nullable = false)
    private LocalDateTime dateEmi;
    @Column(name = "fechavenc", nullable = false)
    private LocalDateTime dateExpi;
    @Column(name = "estado", nullable = false, length = 45)
    private String state;    
    @Column(name = "subTotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subTotal;    
    @Column(name = "dscto", nullable = false, precision = 5, scale = 2)
    private BigDecimal dscto = BigDecimal.ZERO;
    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Client client;

}
