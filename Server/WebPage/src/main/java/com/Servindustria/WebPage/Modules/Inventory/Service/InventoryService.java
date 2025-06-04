package com.Servindustria.WebPage.Modules.Inventory.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Servindustria.WebPage.Exception.ResourceNotFoundException;
import com.Servindustria.WebPage.Modules.Inventory.DTO.InventoryDto;
import com.Servindustria.WebPage.Modules.Inventory.Mapper.InventoryMapper;
import com.Servindustria.WebPage.Modules.Inventory.Model.Inventory;
import com.Servindustria.WebPage.Modules.Inventory.Repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    //Create
    public void createInventory(InventoryDto inventoryDto){
        Inventory inventory = InventoryMapper.toEntity(inventoryDto);
         inventoryRepository.save(inventory);
    }

    //Get by ID
    public InventoryDto getInventoryById(Long id){
        Inventory inventory =  inventoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Inventario con ID " +id+" no encontrado"));
        return InventoryMapper.toDto(inventory);
    }

    //Get All
    public List<InventoryDto> getAllInventory() {
        return inventoryRepository.findAll().stream()
            .map(InventoryMapper::toDto)
            .collect(Collectors.toList());
    }

    //Update
    public InventoryDto updateInventory(Long id, InventoryDto dto){
        Inventory inventory = inventoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Inventario con ID " +id+" no encontrado"));        
        inventory.setCod(dto.getCod());
        inventory.setUbicacion(dto.getUbicacion());
        inventory.setStock_total(dto.getStock_total());
        Inventory inventoryUpdated = inventoryRepository.save(inventory);

        return InventoryMapper.toDto(inventoryUpdated);
    } 

    //Delete
    public void deleteInventory(Long id){
        inventoryRepository.findById(id)
        .orElseThrow(()-> new ResourceNotFoundException("Inventario con ID "+id+" no encontrado"));
        inventoryRepository.deleteById(id);
    }
    
}
