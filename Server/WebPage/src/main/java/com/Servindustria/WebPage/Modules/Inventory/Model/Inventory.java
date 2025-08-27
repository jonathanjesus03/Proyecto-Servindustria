package com.Servindustria.WebPage.Modules.Inventory.Model;

import java.util.ArrayList;
import java.util.List;

import com.Servindustria.WebPage.Modules.Product.Model.Product;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inventario")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 45)
    private String cod;

    @Column(nullable = false, length = 50)
    private String ubicacion;

    @Column(name = "stock_total", nullable = false)
    private int stock_total;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL)
    private List<Product> productos = new ArrayList<>();
}