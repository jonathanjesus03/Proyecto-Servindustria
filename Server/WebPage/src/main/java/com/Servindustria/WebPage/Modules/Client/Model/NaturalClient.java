package com.Servindustria.WebPage.Modules.Client.Model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cliente_persona")
@DiscriminatorValue("PERSONA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NaturalClient extends Client {

    @Column(name = "nombre", nullable = false, length = 255)
    private String name;
    @Column(name = "apellido", nullable = false, length = 255)
    private String lastName;
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate birthDay;
    @Column(name = "sexo", nullable = false, length = 245)
    private String gender;
}
