package com.monolitico.TopEducation.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "Cuota")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuotaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private Date fechaInicial;

    @OneToOne(mappedBy = "cuota")
    private EstudianteEntity estudiante;

    @OneToOne
    @JoinColumn(name = "matricula_id")
    private MatriculaEntity matricula;

    @OneToMany(mappedBy = "cuota", cascade = CascadeType.ALL)
    private List<MesCuotaEntity> mesCuotas = new ArrayList<>();



}
