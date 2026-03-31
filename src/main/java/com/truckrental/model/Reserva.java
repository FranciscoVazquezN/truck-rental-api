package com.truckrental.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
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
    private Date fecha;

    private Float volumen;

    @ManyToMany
    @JoinTable(name = "reserva_camion", joinColumns = @JoinColumn(name = "reserva_id"),
            inverseJoinColumns = @JoinColumn(name = "camion_id"))
    private List<Camion> camiones;

    private Boolean cancelado = false;

}
