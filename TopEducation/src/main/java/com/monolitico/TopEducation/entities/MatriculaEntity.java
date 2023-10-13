package com.monolitico.TopEducation.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Matricula")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatriculaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String nombreMatricula;
    private boolean pagado;
    private Double monto;
    @OneToOne(mappedBy = "matricula")
    private CuotaEntity cuota;


}
