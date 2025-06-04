package com.Servindustria.WebPage.Modules.Service.Mapper;

import com.Servindustria.WebPage.Modules.Category.Model.Category;
import com.Servindustria.WebPage.Modules.Service.DTO.ServiceDto;
import com.Servindustria.WebPage.Modules.Service.Model.Service;

public class ServiceMapper {
     public static ServiceDto toDto(Service service) {
        ServiceDto dto = new ServiceDto();
        dto.setId(service.getId());
        dto.setCod(service.getCod());
        dto.setType(service.getType());
        dto.setCycle(service.getCycle());
        dto.setDescription(service.getDescription());
        dto.setFootage(service.getFootage());
        dto.setPrice(service.getPrice());
        dto.setIdCategory(service.getCategory().getId());
        return dto;
    }

    public static Service toEntity(ServiceDto dto, Category category) {
        Service service = new Service();
        service.setId(dto.getId());
        service.setCod(dto.getCod());
        service.setType(dto.getType());
        service.setCycle(dto.getCycle());
        service.setDescription(dto.getDescription());
        service.setFootage(dto.getFootage());
        service.setPrice(dto.getPrice());
        service.setCategory(category);
        return service;
    }
}
