package com.Servindustria.WebPage.Modules.Inventory.Mapper;

import com.Servindustria.WebPage.Modules.Inventory.DTO.InventoryDto;
import com.Servindustria.WebPage.Modules.Inventory.Model.Inventory;


public class InventoryMapper {
    public static InventoryDto toDto(Inventory inventory) {
        InventoryDto dto = new InventoryDto();
        dto.setId(inventory.getId());
        dto.setCod(inventory.getCod());
        dto.setStock_total(inventory.getStock_total());
        dto.setUbicacion(inventory.getUbicacion());
        return dto;
    }

    public static Inventory toEntity(InventoryDto dto) {
        Inventory inventory = new Inventory();
        inventory.setId(dto.getId());
        inventory.setCod(dto.getCod());
        inventory.setStock_total(dto.getStock_total());
        inventory.setUbicacion(dto.getUbicacion());
        return inventory;
    }
    
}
