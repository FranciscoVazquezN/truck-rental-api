package com.truckrental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "marca", nullable = false, length = 100)
    private String marca;

    @Column(name = "modelo", nullable = false, length = 100)
    private String modelo;

    @Column(name = "capacidad")
    private Float capacidad;

}


