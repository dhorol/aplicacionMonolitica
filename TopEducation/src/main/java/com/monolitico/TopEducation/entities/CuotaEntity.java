package com.monolitico.TopEducation.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
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
    private Date fecha;

    @OneToOne(mappedBy = "cuota", fetch = FetchType.LAZY)
    private EstudianteEntity estudiante;


    @OneToOne(mappedBy = "cuota", cascade = CascadeType.ALL)
    private MatriculaEntity matricula;

    @OneToMany(mappedBy = "cuota", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MesCuotaEntity> cuotasMes = new ArrayList<>();

    public CuotaEntity(EstudianteEntity estudiante) {
        this.fecha = new Date();
        this.estudiante = estudiante;

        // Inicializa la lista de matrículas
        MatriculaEntity matricula = new MatriculaEntity();
        matricula.setMes("matricula");
        matricula.setPagado(false);
        matricula.setMonto(70000L);
        matricula.setCuota(this);
        this.matricula = matricula;

        // No es necesario inicializar la lista de cuotas mensuales aquí, ya que se hará a medida que se agreguen MesCuotaEntity.
    }


}
