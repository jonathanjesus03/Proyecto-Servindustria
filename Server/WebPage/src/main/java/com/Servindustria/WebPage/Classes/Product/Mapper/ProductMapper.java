package com.Servindustria.WebPage.Classes.Product.Mapper;

import com.Servindustria.WebPage.Classes.Category.Model.Category;
import com.Servindustria.WebPage.Classes.Inventory.Model.Inventory;
import com.Servindustria.WebPage.Classes.Product.DTO.ProductDto;
import com.Servindustria.WebPage.Classes.Product.Model.Product;

public class ProductMapper {
    
    public static ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setCod(product.getCod());
        dto.setTipo(product.getTipo());
        dto.setDescripcion(product.getDescripcion());
        dto.setStock(product.getStock());
        dto.setPrecio(product.getPrecio());
        dto.setIdCategoria(product.getCategoria() != null ? product.getCategoria().getId() : null);
        dto.setIdInventario(product.getInventario() != null ? product.getInventario().getId() : null);

        // Nuevos campos
        dto.setAlto(product.getAlto());
        dto.setLargo(product.getLargo());
        dto.setFondo(product.getFondo());
        dto.setMarca(product.getMarca());
        dto.setModelo(product.getModelo());
        dto.setAplicacion(product.getAplicacion());
        dto.setEfecto(product.getEfecto());
        dto.setContenido(product.getContenido());
        dto.setObservaciones(product.getObservaciones());

        return dto;
    }

    public static Product toEntity(ProductDto dto, Inventory inventario, Category categoria) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setCod(dto.getCod());
        product.setTipo(dto.getTipo());
        product.setDescripcion(dto.getDescripcion());
        product.setStock(dto.getStock());
        product.setPrecio(dto.getPrecio());
        product.setInventario(inventario);
        product.setCategoria(categoria);

        // Nuevos campos
        product.setAlto(dto.getAlto());
        product.setLargo(dto.getLargo());
        product.setFondo(dto.getFondo());
        product.setMarca(dto.getMarca());
        product.setModelo(dto.getModelo());
        product.setAplicacion(dto.getAplicacion());
        product.setEfecto(dto.getEfecto());
        product.setContenido(dto.getContenido());
        product.setObservaciones(dto.getObservaciones());

        return product;
    }
}
