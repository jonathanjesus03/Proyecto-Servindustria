package com.Servindustria.WebPage.Modules.Client.Model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cliente_empresa")
@DiscriminatorValue("EMPRESA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyClient extends Client {

    @Column(name = "nombre_razonsoc", nullable = false, length = 60)
    private String nameReasonSoc;

    @Column(name = "direccion", nullable = false, length = 150)
    private String address;

    @Column(name = "referencia", nullable = false, length = 255)
    private String reference;
}