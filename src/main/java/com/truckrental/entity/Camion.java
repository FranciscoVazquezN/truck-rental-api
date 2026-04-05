package com.truckrental.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "camion", schema = "public")
public class Camion {
    @Id
    @Column(name = "id_camion")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "camion_id_camion_seq", sequenceName = "camion_id_camion_seq", allocationSize = 1)
    private int idCamion;

    @Column(name = "capacidad", nullable = false)
    private Double capacidad;

    private Boolean activo = true;

    @OneToMany(mappedBy = "camion")
    private List<ReservaCamion> reservas;

}


