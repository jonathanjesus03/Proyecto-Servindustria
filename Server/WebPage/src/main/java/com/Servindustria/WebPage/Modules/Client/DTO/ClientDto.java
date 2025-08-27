package com.Servindustria.WebPage.Modules.Client.DTO;


import java.util.List;

import com.Servindustria.WebPage.Modules.Quote.DTO.QuoteDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ClientDto {
private Long id;

    @NotBlank(message = "El código no puede estar vacío")
    private String cod;

    @NotBlank(message = "El tipo de documento no puede estar vacío")
    private String typeDoc;

    @NotBlank(message = "El número de documento no puede estar vacío")
    private String documentNumber;

    @NotBlank(message = "El teléfono principal no puede estar vacío")
    @Pattern(regexp = "\\d{9,15}", message = "El teléfono debe contener entre 9 y 15 dígitos")
    private String phone1;

    @Pattern(regexp = "\\d{9,15}", message = "El teléfono debe contener entre 9 y 15 dígitos")
    private String phone2;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe tener un formato válido")
    private String email;

    private String typeClient;
    
    private Long idAccount; 

    private List<QuoteDto> quotes;

}
