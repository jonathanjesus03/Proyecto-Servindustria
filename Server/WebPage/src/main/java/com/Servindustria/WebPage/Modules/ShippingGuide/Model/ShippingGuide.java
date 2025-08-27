package com.Servindustria.WebPage.Modules.ShippingGuide.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

import com.Servindustria.WebPage.Modules.Client.Model.CompanyClient;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "guia_remision")
public class ShippingGuide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cod", nullable = false, unique = true, length = 45)
    private String code;

    @Column(name = "fechaemi", nullable = false)
    private LocalDateTime issueDate;

    @ManyToOne
    @JoinColumn(name = "cod_empresa", referencedColumnName = "cod", nullable = false)
    private CompanyClient company;

    //Entities to be added later
    /* 
    @ManyToOne
    @JoinColumn(name = "id_transportista", nullable = false)
    private Carrier carrier;

    @ManyToOne
    @JoinColumn(name = "id_almacenero", nullable = false)
    private Storekeeper storekeeper;

    @OneToOne
    @JoinColumn(name = "id_orden", unique = true, nullable = false)
    private ServiceOrder serviceOrder;
    */
}