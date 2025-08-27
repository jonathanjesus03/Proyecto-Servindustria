package com.Servindustria.WebPage.Modules.CotizacionProv;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Servindustria.WebPage.Exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CotizacionService {
    private final CotizacionRepository cotizacionRepository;

    public void createCotizacion(CotizacionDTO cotiDto){
        Cotizacion coti = CotizacionMapper.toEntity(cotiDto);
        cotizacionRepository.save(coti);
        
    }

    public void deleteCotizacion(Long id){
        Cotizacion cotiToDelete =cotizacionRepository.findById(id).
        orElseThrow(() -> new ResourceNotFoundException("Cotización con ID: "+ id +" no encontrado"));
        cotizacionRepository.delete(cotiToDelete);
    }

    public List<CotizacionDTO> getCotizaciones(){
        return cotizacionRepository.findAll().stream().map(CotizacionMapper::toDto).collect(Collectors.toList());
    }
}
