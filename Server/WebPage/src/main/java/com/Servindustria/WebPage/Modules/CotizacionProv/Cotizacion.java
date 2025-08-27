package com.Servindustria.WebPage.Modules.CotizacionProv;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cotizacionProv")
public class Cotizacion{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "cliente_name", nullable = false, length = 100)
    private String name;
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    @Column(name = "producto", nullable = false, length = 100)
    private String product;
    @Column(name = "cantidad", nullable = false)
    private int quantity;
    @Column(name = "observaciones", nullable = false, length = 255)
    private String details;
}