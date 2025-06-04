package com.Servindustria.WebPage.Modules.Inventory.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventoryDto {
    private Long id;
    @NotBlank(message = "El código es obligatorio")
    private String cod;

    @NotNull(message = "El stock total no puede ser nulo")
    private Integer stock_total;

    @NotBlank(message = "La ubicación es obligatoria")
    private String ubicacion;

}
