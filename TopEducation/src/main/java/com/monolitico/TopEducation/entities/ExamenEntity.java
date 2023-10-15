package com.monolitico.TopEducation.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "Examen")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", referencedColumnName = "id") // Cambia "rut_estudiante" a "estudiante_id" y "rut" a "id"
    private EstudianteEntity estudiante;

    private String rutEstudiante;
    @Column(name = "fecha_examen", nullable = false)
    private Date fechaExamen;

    @Column(nullable = false)
    private double puntaje;
}
