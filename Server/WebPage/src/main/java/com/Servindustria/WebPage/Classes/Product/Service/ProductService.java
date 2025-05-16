package com.Servindustria.WebPage.Classes.Product.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Servindustria.WebPage.Classes.Category.Model.Category;
import com.Servindustria.WebPage.Classes.Category.Repository.CategoryRepository;
import com.Servindustria.WebPage.Classes.Inventory.Model.Inventory;
import com.Servindustria.WebPage.Classes.Inventory.Repository.InventoryRepository;
import com.Servindustria.WebPage.Classes.Product.DTO.ProductDto;
import com.Servindustria.WebPage.Classes.Product.Mapper.ProductMapper;
import com.Servindustria.WebPage.Classes.Product.Model.Product;
import com.Servindustria.WebPage.Classes.Product.Repository.ProductRepository;
import com.Servindustria.WebPage.Exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {
    
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final CategoryRepository categoryRepository;

    //Create
    public void createProduct(ProductDto dto) {
        Inventory inventory = inventoryRepository.findById(dto.getIdInventario())
            .orElseThrow(() -> new ResourceNotFoundException("Inventario del Producto con ID " + dto.getIdInventario() + " no encontrado"));

        Category category = categoryRepository.findById(dto.getIdCategoria())
            .orElseThrow(() -> new ResourceNotFoundException("Categoría del Producto con ID " + dto.getIdCategoria() + " no encontrada"));

        Product product = ProductMapper.toEntity(dto, inventory, category);
        productRepository.save(product);
    }

    //Get by ID
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto con ID " + id + " no encontrado"));

        return ProductMapper.toDto(product);
    }

    //Get All
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
            .map(ProductMapper::toDto)
            .collect(Collectors.toList());
    }

    //Update
    public ProductDto updateProduct(Long id, ProductDto dto) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto con ID " + id + " no encontrado"));

        Inventory inventory = inventoryRepository.findById(dto.getIdInventario())
            .orElseThrow(() -> new ResourceNotFoundException("Inventario del Producto con ID " + dto.getIdInventario() + " no encontrado"));

        Category category = categoryRepository.findById(dto.getIdCategoria())
            .orElseThrow(() -> new ResourceNotFoundException("Categoría del Producto con ID " + dto.getIdCategoria() + " no encontrada"));

        product.setCod(dto.getCod());
        product.setTipo(dto.getTipo());
        product.setDescripcion(dto.getDescripcion());
        product.setStock(dto.getStock());
        product.setPrecio(dto.getPrecio());
        product.setInventario(inventory);
        product.setCategoria(category);

        Product updated = productRepository.save(product);
        return ProductMapper.toDto(updated);
    }

    //Delete
    public void deleteProduct(Long id) {
        productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto con ID " + id + " no encontrado"));
        productRepository.deleteById(id);
    }
}
