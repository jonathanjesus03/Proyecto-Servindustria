package com.Servindustria.WebPage.Modules.Product.Model;

import java.math.BigDecimal;

import com.Servindustria.WebPage.Modules.Category.Model.Category;
import com.Servindustria.WebPage.Modules.Inventory.Model.Inventory;

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

    @Column(name = "tipo", nullable = false, length = 50)
    private String type;

    @Column(name = "descripcion", nullable = false, length = 255)
    private String description;

    @Column(nullable = false)
    private int stock;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "id_inventario")
    private Inventory inventory;

    // New Attributes

    @Column(name = "alto", length = 20)
    private String height;

    @Column(name = "largo", length = 20)
    private String length;

    @Column(name = "fondo", length = 20)
    private String depth;

    @Column(name = "marca", length = 100)
    private String brand;

    @Column(name = "modelo", length = 100)
    private String model;

    @Column(name = "img", length = 50)
    private String img;

    @Column(name = "aplicacion", columnDefinition = "TEXT")
    private String application;

    @Column(name = "efecto", columnDefinition = "TEXT")
    private String effect;

    @Column(name = "contenido", columnDefinition = "TEXT")
    private String content;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observations;
}
