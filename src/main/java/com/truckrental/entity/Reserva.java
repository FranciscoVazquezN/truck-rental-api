package com.truckrental.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reserva", schema = "public")
public class Reserva {
    @Id
    @Column(name = "id_reserva")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "reserva_id_reserva_seq", sequenceName = "reserva_id_camion_seq", allocationSize = 1)
    private int idReserva;

    private String origen;
    private String destino;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "fecha_desde")
    private LocalDate fechaDesde;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "fecha_hasta")
    private LocalDate fechaHasta;

    @Column(name = "volumen_total", nullable = false)
    private Double volumenTotal;

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    private List<ReservaCamion> camiones;

    private Boolean cancelado = false;

}
