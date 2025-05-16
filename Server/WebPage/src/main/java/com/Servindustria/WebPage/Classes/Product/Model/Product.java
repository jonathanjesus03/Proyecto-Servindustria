package com.Servindustria.WebPage.Classes.Product.Model;

import java.math.BigDecimal;

import com.Servindustria.WebPage.Classes.Category.Model.Category;
import com.Servindustria.WebPage.Classes.Inventory.Model.Inventory;

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

@Entity
@Table(name = "producto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 45)
    private String cod;

    @Column(nullable = false, length = 50)
    private String tipo;

    @Column(nullable = false, length = 255)
    private String descripcion;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Category categoria;

    @ManyToOne
    @JoinColumn(name = "id_inventario")
    private Inventory inventario;

    // Nuevos atributos

    @Column(length = 20)
    private String alto;

    @Column(length = 20)
    private String largo;

    @Column(length = 20)
    private String fondo;

    @Column(length = 100)
    private String marca;

    @Column(length = 100)
    private String modelo;

    @Column(columnDefinition = "TEXT")
    private String aplicacion;

    @Column(columnDefinition = "TEXT")
    private String efecto;

    @Column(columnDefinition = "TEXT")
    private String contenido;

    @Column(columnDefinition = "TEXT")
    private String observaciones;
}
