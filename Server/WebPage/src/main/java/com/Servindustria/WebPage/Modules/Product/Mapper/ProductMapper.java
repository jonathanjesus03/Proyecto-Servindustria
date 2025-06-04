package com.Servindustria.WebPage.Modules.Product.Mapper;

import com.Servindustria.WebPage.Modules.Category.Model.Category;
import com.Servindustria.WebPage.Modules.Inventory.Model.Inventory;
import com.Servindustria.WebPage.Modules.Product.DTO.ProductDto;
import com.Servindustria.WebPage.Modules.Product.Model.Product;

public class ProductMapper {
    
    public static ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setCod(product.getCod());
        dto.setType(product.getType());
        dto.setDescription(product.getDescription());
        dto.setStock(product.getStock());
        dto.setPrice(product.getPrice());
        dto.setIdCategory(product.getCategory() != null ? product.getCategory().getId() : null);
        dto.setIdInventory(product.getInventory() != null ? product.getInventory().getId() : null);

        // Nuevos campos
        dto.setHeight(product.getHeight());
        dto.setLength(product.getLength());
        dto.setDepth(product.getDepth());
        dto.setBrand(product.getBrand());
        dto.setModel(product.getModel());
        dto.setApplication(product.getApplication());
        dto.setEffect(product.getEffect());
        dto.setImg(product.getImg());
        dto.setContent(product.getContent());
        dto.setObservations(product.getObservations());

        return dto;
    }

    public static Product toEntity(ProductDto dto, Inventory inventory, Category category) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setCod(dto.getCod());
        product.setType(dto.getType());
        product.setDescription(dto.getDescription());
        product.setStock(dto.getStock());
        product.setPrice(dto.getPrice());
        product.setInventory(inventory);
        product.setCategory(category);

        // Nuevos campos
        product.setHeight(dto.getHeight());
        product.setLength(dto.getLength());
        product.setDepth(dto.getDepth());
        product.setBrand(dto.getBrand());
        product.setModel(dto.getModel());
        product.setApplication(dto.getApplication());
        product.setEffect(dto.getEffect());
        product.setContent(dto.getContent());
        product.setImg(dto.getImg());
        product.setObservations(dto.getObservations());

        return product;
    }
}
