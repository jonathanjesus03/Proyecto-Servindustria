package com.Servindustria.WebPage.Modules.Employee.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

import com.Servindustria.WebPage.Modules.ClientAccount.Model.ClientAccount;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empleado")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true, length = 45)
    private String code;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "document", nullable = false, length = 45)
    private String document;

    @Column(name = "telephone", nullable = false, length = 50)
    private String telephone;

    @Column(name = "address", nullable = false, length = 150)
    private String address;

    @Column(name = "cargo", nullable = false, length = 45)
    private String position;

    @Column(name = "fechacontrato", nullable = false)
    private LocalDateTime contractDate;

    @OneToOne
    @JoinColumn(name = "id_account", unique = true)
    private ClientAccount account;
}