
package com.Servindustria.WebPage.Modules.Product.Scheduler;

import com.Servindustria.WebPage.Modules.Product.DTO.ProductDto;
import com.Servindustria.WebPage.Modules.Product.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StockAlertScheduler {

    private final ProductService productService;

    // Ejecutar cada 5 minutos
   @Scheduled(cron = "*/30 * * * * *")
    public void verificarStockAgotado() {
        System.out.println("⏰ Verificando productos sin stock...");

        List<ProductDto> productos = productService.getAllProducts();

        for (ProductDto producto : productos) {
            if (producto.getStock() <= 0) {
                System.out.println("⚠️ ¡ALERTA! El producto \"" + producto.getDescription() + "\" está sin stock.");
                // Aquí podrías enviar correo, log, notificación, etc.
            }
        }
    }
}
