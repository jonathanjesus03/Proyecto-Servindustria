package com.Servindustria.WebPage.Modules.CotizacionProv;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/cotizar")
public class CotizacionController{

    private final CotizacionService cotizacionService;

    @PostMapping
    public ResponseEntity<Void> createCotizacion(@RequestBody CotizacionDTO cotizacionDto){
        cotizacionService.createCotizacion(cotizacionDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    } 

    @GetMapping
    public ResponseEntity<List<CotizacionDTO>> getCotizaciones(){
        List<CotizacionDTO> listCotiDto = cotizacionService.getCotizaciones();
        return ResponseEntity.ok(listCotiDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCotizacion(@PathVariable Long id){
        cotizacionService.deleteCotizacion(id);
        return ResponseEntity.noContent().build();
    }
}
