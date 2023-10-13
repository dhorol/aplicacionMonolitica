package com.monolitico.TopEducation.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "MesCuota")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MesCuotaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String mes;
    private boolean pagado;
    private Double monto;
    private Date vencimiento;
    @ManyToOne
    @JoinColumn(name = "cuota_id")
    private CuotaEntity cuota;

}
