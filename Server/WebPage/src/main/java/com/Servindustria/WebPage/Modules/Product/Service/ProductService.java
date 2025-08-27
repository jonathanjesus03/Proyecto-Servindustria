package com.Servindustria.WebPage.Modules.Product.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Servindustria.WebPage.Exception.ResourceNotFoundException;
import com.Servindustria.WebPage.Modules.Category.Model.Category;
import com.Servindustria.WebPage.Modules.Category.Repository.CategoryRepository;
import com.Servindustria.WebPage.Modules.Inventory.Model.Inventory;
import com.Servindustria.WebPage.Modules.Inventory.Repository.InventoryRepository;
import com.Servindustria.WebPage.Modules.Product.DTO.ProductDto;
import com.Servindustria.WebPage.Modules.Product.Mapper.ProductMapper;
import com.Servindustria.WebPage.Modules.Product.Model.Product;
import com.Servindustria.WebPage.Modules.Product.Repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {
    
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final CategoryRepository categoryRepository;

    //Create
    public void createProduct(ProductDto dto) {
        Inventory inventory = inventoryRepository.findById(dto.getIdInventory())
            .orElseThrow(() -> new ResourceNotFoundException("Inventario del Producto con ID " + dto.getIdInventory() + " no encontrado"));

        Category category = categoryRepository.findById(dto.getIdCategory())
            .orElseThrow(() -> new ResourceNotFoundException("Categoría del Producto con ID " + dto.getIdCategory() + " no encontrada"));

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

        Inventory inventory = inventoryRepository.findById(dto.getIdInventory())
            .orElseThrow(() -> new ResourceNotFoundException("Inventario del Producto con ID " + dto.getIdInventory() + " no encontrado"));

        Category category = categoryRepository.findById(dto.getIdCategory())
            .orElseThrow(() -> new ResourceNotFoundException("Categoría del Producto con ID " + dto.getIdCategory() + " no encontrada"));

        product.setCod(dto.getCod());
        product.setType(dto.getType());
        product.setDescription(dto.getDescription());
        product.setStock(dto.getStock());
        product.setPrice(dto.getPrice());
        product.setInventory(inventory);
        product.setCategory(category);
        product.setImg(dto.getImg());
        // Nuevos campos en inglés
        product.setHeight(dto.getHeight());
        product.setLength(dto.getLength());
        product.setDepth(dto.getDepth());
        product.setBrand(dto.getBrand());
        product.setModel(dto.getModel());
        product.setApplication(dto.getApplication());
        product.setEffect(dto.getEffect());
        product.setContent(dto.getContent());
        product.setObservations(dto.getObservations());

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
