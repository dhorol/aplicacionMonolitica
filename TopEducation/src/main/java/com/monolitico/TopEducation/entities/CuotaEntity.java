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
    private boolean pagado;
    private Double montoTotalArancel;
    private Integer numeroCuotas;
    private Integer cuotasPagadas;
    private Double montoPagado;
    private Date ultimoPago;
    private Double pagoRestante;



    @OneToOne(mappedBy = "cuota")
    private EstudianteEntity estudiante;

    @OneToOne
    @JoinColumn(name = "matricula_id")
    private MatriculaEntity matricula;

    @OneToMany(mappedBy = "cuota", cascade = CascadeType.ALL)
    private List<MesCuotaEntity> mesCuotas = new ArrayList<>();



}
