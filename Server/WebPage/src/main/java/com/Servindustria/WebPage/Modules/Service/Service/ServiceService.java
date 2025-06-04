package com.Servindustria.WebPage.Modules.Service.Service;

import java.util.List;
import java.util.stream.Collectors;


import com.Servindustria.WebPage.Exception.ResourceNotFoundException;
import com.Servindustria.WebPage.Modules.Category.Model.Category;
import com.Servindustria.WebPage.Modules.Category.Repository.CategoryRepository;
import com.Servindustria.WebPage.Modules.Service.DTO.ServiceDto;
import com.Servindustria.WebPage.Modules.Service.Mapper.ServiceMapper;
import com.Servindustria.WebPage.Modules.Service.Model.Service;
import com.Servindustria.WebPage.Modules.Service.Repository.ServiceRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@org.springframework.stereotype.Service // <-- Specify Service from Spring Framework
public class ServiceService {
    private final ServiceRepository serviceRepository;
    private final CategoryRepository categoryRepository;

    // Create
    public void createService(ServiceDto dto) {
        Category category = categoryRepository.findById(dto.getIdCategory())
            .orElseThrow(() -> new ResourceNotFoundException("Categoría con ID " + dto.getIdCategory() + " no encontrada"));

        Service service = ServiceMapper.toEntity(dto, category);
        serviceRepository.save(service);
    }

    // Get by ID
    public ServiceDto getServiceById(Long id) {
        Service service = serviceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Servicio con ID " + id + " no encontrado"));

        return ServiceMapper.toDto(service);
    }

    // Get All
    public List<ServiceDto> getAllServices() {
        return serviceRepository.findAll().stream()
            .map(ServiceMapper::toDto)
            .collect(Collectors.toList());
    }

    // Update
    public ServiceDto updateService(Long id, ServiceDto dto) {
        Service service = serviceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Servicio con ID " + id + " no encontrado"));

        Category category = categoryRepository.findById(dto.getIdCategory())
            .orElseThrow(() -> new ResourceNotFoundException("Categoría con ID " + dto.getIdCategory() + " no encontrada"));

        service.setCod(dto.getCod());
        service.setType(dto.getType());
        service.setCycle(dto.getCycle());
        service.setDescription(dto.getDescription());
        service.setFootage(dto.getFootage());
        service.setPrice(dto.getPrice());
        service.setCategory(category);

        Service updated = serviceRepository.save(service);
        return ServiceMapper.toDto(updated);
    }

    // Eliminar
    public void deleteService(Long id) {
        serviceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Servicio con ID " + id + " no encontrado"));
        serviceRepository.deleteById(id);
    }

}
