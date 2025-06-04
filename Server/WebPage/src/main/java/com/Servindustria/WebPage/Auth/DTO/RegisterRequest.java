package com.Servindustria.WebPage.Auth.DTO;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
private String email;
    private String password;
    private String role;
    // (NaturalClient)
    private String name;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthDay;
    private String gender;
    private String documentType;
    private String documentNumber;
    private String phone1;
    private String phone2;

    // (CompanyClient)
    private String nameReasonSoc;
    private String address;
    private String reference;

    // Employee
    private String employeeName;
    private String position;
}
