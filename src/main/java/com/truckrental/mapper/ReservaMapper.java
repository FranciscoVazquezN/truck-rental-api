package com.truckrental.mapper;

import com.truckrental.dto.CamionDTO;
import com.truckrental.dto.ReservaDTO;
import com.truckrental.entity.Reserva;

public class ReservaMapper {

    public static ReservaDTO toDTO(Reserva r) {

        ReservaDTO dto = new ReservaDTO();
        dto.setId(r.getIdReserva());
        dto.setOrigen(r.getOrigen());
        dto.setDestino(r.getDestino());
        dto.setFechaDesde(r.getFechaDesde());
        dto.setFechaHasta(r.getFechaHasta());
        dto.setVolumenTotal(r.getVolumenTotal());

        dto.setCamiones(
                r.getCamiones().stream().map(rc -> {
                    CamionDTO c = new CamionDTO();
                    c.setId(rc.getCamion().getIdCamion());
                    c.setCapacidad(rc.getCamion().getCapacidad());
                    c.setVolumenAsignado(rc.getVolumenAsignado());
                    return c;
                }).toList()
        );

        return dto;
    }
}