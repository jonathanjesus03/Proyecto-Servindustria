package com.Servindustria.WebPage.Modules.Inventory.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Servindustria.WebPage.Modules.Inventory.DTO.InventoryDto;
import com.Servindustria.WebPage.Modules.Inventory.Service.InventoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    //Create
    @PostMapping
    public ResponseEntity<Void> createInventory(@Valid @RequestBody InventoryDto inventorydDto){
        inventoryService.createInventory(inventorydDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }    

    //Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<InventoryDto> getInventoryById(@PathVariable Long id){
        InventoryDto inventoryDto = inventoryService.getInventoryById(id);
        return ResponseEntity.ok(inventoryDto);
    }

    //Get All
    @GetMapping
    public ResponseEntity<List<InventoryDto>> getAllInventories(){
        List<InventoryDto> listInventoriesDto = inventoryService.getAllInventory();
        return ResponseEntity.ok(listInventoriesDto);
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventoryById(@PathVariable Long id){
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }

    //Update
    @PutMapping("/{id}")
    public ResponseEntity<InventoryDto> updateInventoryById(@PathVariable Long id, @Valid @RequestBody InventoryDto dto){
        InventoryDto inventoryDto = inventoryService.updateInventory(id, dto);
        return ResponseEntity.ok(inventoryDto);
    }
    

}
