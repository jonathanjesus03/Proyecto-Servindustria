package com.Servindustria.WebPage.Modules.Client.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO para clientes de tipo persona natural.
 */
@Data
public class NaturalClientDto extends ClientDto {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @NotBlank(message = "El apellido no puede estar vacío")
    private String lastName;

    @NotNull(message = "La fecha de nacimiento no puede estar vacía")
    private LocalDate birthDay;

    @NotBlank(message = "El género no puede estar vacío")
    private String gender;
}