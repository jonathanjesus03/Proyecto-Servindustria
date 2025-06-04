package com.Servindustria.WebPage.Modules.CotizacionProv;

public class CotizacionMapper {
    
    public static CotizacionDTO toDto(Cotizacion coti){
        CotizacionDTO cotiDto = new CotizacionDTO();
        cotiDto.setEmail(coti.getEmail());
        cotiDto.setProduct(coti.getProduct());
        cotiDto.setName(coti.getName());
        cotiDto.setDetails(coti.getDetails());
        cotiDto.setQuantity(coti.getQuantity());
        return cotiDto;
    }

    public static Cotizacion toEntity(CotizacionDTO cotiDto){
        Cotizacion coti = new Cotizacion();
        coti.setName(cotiDto.getName());
        coti.setDetails(cotiDto.getDetails());
        coti.setProduct(cotiDto.getProduct());
        coti.setEmail(cotiDto.getEmail());
        coti.setQuantity(cotiDto.getQuantity());
        return coti;
    }
}
