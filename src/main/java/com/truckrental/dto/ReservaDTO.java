package com.truckrental.dto;

import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
public class ReservaDTO {
    public int id;
    public String origen;
    public String destino;
    public LocalDate fechaDesde;
    public LocalDate fechaHasta;
    public Double volumenTotal;
    public List<CamionDTO> camiones;
}