package com.Servindustria.WebPage.Modules.Service.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Servindustria.WebPage.Modules.Service.DTO.ServiceDto;
import com.Servindustria.WebPage.Modules.Service.Service.ServiceService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/service")
public class ServiceController {
    private final ServiceService serviceService;

    // Create
    @PostMapping
    public ResponseEntity<String> createService(@RequestBody ServiceDto dto) {
        serviceService.createService(dto);
        return ResponseEntity.ok("Servicio creado correctamente.");
    }

    // Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<ServiceDto> getServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceService.getServiceById(id));
    }

    // Get All
    @GetMapping
    public ResponseEntity<List<ServiceDto>> getAllServices() {
        return ResponseEntity.ok(serviceService.getAllServices());
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<ServiceDto> updateService(@PathVariable Long id, @RequestBody ServiceDto dto) {
        return ResponseEntity.ok(serviceService.updateService(id, dto));
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
        return ResponseEntity.ok("Servicio eliminado correctamente.");
    }
}
