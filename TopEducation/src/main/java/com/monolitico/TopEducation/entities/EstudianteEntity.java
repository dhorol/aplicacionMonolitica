package com.monolitico.TopEducation.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


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
    private String tipoPago;
    private Integer examenesRendidos;
    private Double promedioExamenes;



    @OneToOne
    @JoinColumn(name = "cuota_id")
    private CuotaEntity cuota;

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL)
    private List<ExamenEntity> examenes = new ArrayList<>();



}
