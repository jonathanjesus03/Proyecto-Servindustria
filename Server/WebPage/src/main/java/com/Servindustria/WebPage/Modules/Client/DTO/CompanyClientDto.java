package com.Servindustria.WebPage.Modules.Client.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO para clientes de tipo empresa.
 */
@Data
public class CompanyClientDto extends ClientDto {

    @NotBlank(message = "La razón social no puede estar vacía")
    private String nameReasonSoc;

    @NotBlank(message = "La dirección no puede estar vacía")
    private String address;

    @NotBlank(message = "La referencia no puede estar vacía")
    private String reference;
}
