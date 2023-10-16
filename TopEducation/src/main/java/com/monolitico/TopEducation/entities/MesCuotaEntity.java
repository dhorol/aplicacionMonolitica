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
    private Integer retrasos;
    @ManyToOne
    @JoinColumn(name = "cuota_id")
    private CuotaEntity cuota;

    @Transient
    private Boolean previouslyPaid;

    @Transient
    private Double montoOriginal;

    public void guardarMontoOriginal() {
        this.montoOriginal = this.monto;
    }

    public double calcularDiferenciaPorInteres() {
        return this.monto - this.montoOriginal;
    }

    @PostLoad
    public void onLoad() {
        this.previouslyPaid = this.pagado;
    }

    public void markAsModified() {
        this.previouslyPaid = this.pagado;
    }

    public boolean wasPreviouslyPaid() {
        return previouslyPaid != null && previouslyPaid;
    }

    // Método para obtener el valor numérico del mes

    public int getMesNumerico() {
        switch (mes) {
            case "Enero": return 1;
            case "Febrero": return 2;
            case "Marzo": return 3;
            case "Abril": return 4;
            case "Mayo": return 5;
            case "Junio": return 6;
            case "Julio": return 7;
            case "Agosto": return 8;
            case "Septiembre": return 9;
            case "Octubre": return 10;
            case "Noviembre": return 11;
            case "Diciembre": return 12;
            default: throw new IllegalArgumentException("Mes no válido: " + mes);
        }
    }

}
