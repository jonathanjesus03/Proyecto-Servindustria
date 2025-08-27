package com.Servindustria.WebPage.Modules.Client.Model;

import java.util.ArrayList;
import java.util.List;

import com.Servindustria.WebPage.Modules.ClientAccount.Model.ClientAccount;
import com.Servindustria.WebPage.Modules.Quote.Model.Quote;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Table(name = "cliente")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_cliente", discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cod", nullable = false, unique = true, length = 45)
    private String code;

    @Column(name = "telefono1", nullable = false, length = 45)
    private String phone1;

    @Column(name = "telefono2", length = 45)
    private String phone2;

    @Column(name = "tipodoc", nullable = false, length = 45)
    private String documentType;

    @Column(name = "numdoc", nullable = false, length = 60)
    private String documentNumber;

    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_account", unique = true)
    private ClientAccount account;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Quote> quotes = new ArrayList<>();
}

