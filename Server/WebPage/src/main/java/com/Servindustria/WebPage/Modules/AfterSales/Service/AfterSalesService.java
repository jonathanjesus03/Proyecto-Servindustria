package com.Servindustria.WebPage.Modules.AfterSales.Service;


import java.util.List;

import com.Servindustria.WebPage.Exception.ResourceNotFoundException;
import com.Servindustria.WebPage.Modules.AfterSales.Model.AfterSales;
import com.Servindustria.WebPage.Modules.AfterSales.Repository.AfterSalesRepository;
import com.Servindustria.WebPage.Modules.Client.Model.Client;
import com.Servindustria.WebPage.Modules.Client.Repository.ClientRepository;
import com.Servindustria.WebPage.Modules.Product.Model.Product;
import com.Servindustria.WebPage.Modules.Product.Repository.ProductRepository;
import com.Servindustria.WebPage.Modules.Service.Model.Service;
import com.Servindustria.WebPage.Modules.Service.Repository.ServiceRepository;

import lombok.RequiredArgsConstructor;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class AfterSalesService {
    private final AfterSalesRepository afterSalesRepository;
    private final ProductRepository productRepository;
    private final ServiceRepository serviceRepository;
    private final ClientRepository clientRepository;

    public AfterSales createAfterSales(AfterSales afterSales) {
        // Validar que las relaciones existan
        Product product = productRepository.findById(afterSales.getProduct().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Product con ID " + afterSales.getProduct().getId() + " no encontrado"));
        Service service = serviceRepository.findById(afterSales.getService().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Service con ID " + afterSales.getService().getId() + " no encontrado"));
        Client cliente = clientRepository.findById(afterSales.getClient().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Cliente con ID " + afterSales.getClient().getId() + " no encontrado"));

        afterSales.setProduct(product);
        afterSales.setService(service);
        afterSales.setClient(cliente);

        return afterSalesRepository.save(afterSales);
    }

    public List<AfterSales> getAfterSalesByClientId(Long clientId) {
        return afterSalesRepository.findByClientId(clientId);
    }

    public AfterSales getAfterSalesById(Long id) {
        return afterSalesRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("AfterSales con ID " + id + " no encontrado"));
    }

    public AfterSales updateAfterSales(Long id, AfterSales updatedAfterSales) {
        AfterSales existingAfterSales = afterSalesRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("AfterSales con ID " + id + " no encontrado"));

        existingAfterSales.setCod(updatedAfterSales.getCod());
        existingAfterSales.setServiceType(updatedAfterSales.getServiceType());
        existingAfterSales.setDateExpi(updatedAfterSales.getDateExpi());
        existingAfterSales.setPrice(updatedAfterSales.getPrice());
        existingAfterSales.setState(updatedAfterSales.getState());
        existingAfterSales.setAmount(updatedAfterSales.getAmount());

        Product product = productRepository.findById(updatedAfterSales.getProduct().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Product con ID " + updatedAfterSales.getProduct().getId() + " no encontrado"));
        Service service = serviceRepository.findById(updatedAfterSales.getService().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Service con ID " + updatedAfterSales.getService().getId() + " no encontrado"));
        Client client = clientRepository.findById(updatedAfterSales.getClient().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Cliente con ID " + updatedAfterSales.getClient().getId() + " no encontrado"));

        existingAfterSales.setProduct(product);
        existingAfterSales.setService(service);
        existingAfterSales.setClient(client);

        return afterSalesRepository.save(existingAfterSales);
    }

    public void deleteAfterSales(Long id) {
        if (!afterSalesRepository.existsById(id)) {
            throw new ResourceNotFoundException("AfterSales con ID " + id + " no encontrado");
        }
        afterSalesRepository.deleteById(id);
    }
}
