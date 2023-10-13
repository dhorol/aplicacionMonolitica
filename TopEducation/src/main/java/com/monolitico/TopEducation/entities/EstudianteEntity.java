package com.monolitico.TopEducation.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Entity
@Table(name = "Estudiante")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class EstudianteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)

    private Long id;
    private String rut;
    private String apellidos;
    private String nombres;
    private Date fechaDeNacimiento;
    private String tipoColegio;
    private String nombreColegio;
    private Long annioEgreso;
    @OneToOne
    @JoinColumn(name = "cuota_id")
    private CuotaEntity cuota;






}
