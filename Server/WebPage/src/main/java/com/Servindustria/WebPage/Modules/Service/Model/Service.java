package com.Servindustria.WebPage.Modules.Service.Model;

import java.math.BigDecimal;

import com.Servindustria.WebPage.Modules.Category.Model.Category;

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
@Table(name = "servicio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cod", nullable = false, unique = true, length = 50)
    private String cod;

    @Column(name = "tipo", nullable = false, length = 50)
    private String type;

    @Column(name = "ciclo", nullable = false, length = 45)
    private String cycle;

    @Column(name = "descripcion", nullable = false, length = 100)
    private String description;

    @Column(name = "matraje", nullable = false, precision = 10, scale = 2)
    private BigDecimal footage;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Category category;

}
