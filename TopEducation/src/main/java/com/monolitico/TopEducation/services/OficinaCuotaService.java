package com.monolitico.TopEducation.services;

import com.monolitico.TopEducation.entities.CuotaEntity;
import com.monolitico.TopEducation.entities.EstudianteEntity;
import com.monolitico.TopEducation.entities.MesCuotaEntity;
import com.monolitico.TopEducation.repositories.CuotaRepository;
import com.monolitico.TopEducation.repositories.EstudianteRepository;
import com.monolitico.TopEducation.repositories.MatriculaRepository;
import com.monolitico.TopEducation.repositories.MesCuotaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service

public class OficinaCuotaService {

    @Autowired
    private EstudianteRepository estudianteRepository;
    @Autowired
    private CuotaRepository cuotaRepository;
    @Autowired
    private MatriculaRepository matriculaRepository;
    @Autowired
    private MesCuotaRepository mesCuotaRepository;
    public CuotaEntity findCuotaById(Long id) {
        return cuotaRepository.findById(id).orElse(null);
    }
    public void marcarMatriculaComoPagada(Long id) {
        CuotaEntity cuota = findCuotaById(id);
        if (cuota != null && cuota.getMatricula() != null) {
            cuota.getMatricula().setPagado(true);
            cuotaRepository.save(cuota);
        }
    }

    public void pagarAlContado(Long id) {
        CuotaEntity cuota = findCuotaById(id);
        if (cuota != null) {
            MesCuotaEntity mesCuota = new MesCuotaEntity();
            mesCuota.setCuota(cuota);
            mesCuota.setMonto(1500000 * 0.5);  // 50% de 1,500,000
            mesCuota.setPagado(true);
            // Extraer el mes de la fecha 'fechaInicial' en 'cuota'
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
            String mes = sdf.format(cuota.getFechaInicial());

            // Asignar ese mes a 'mesCuota'
            mesCuota.setMes(mes);

            // Si trabajas con un repositorio de MesCuota, debes guardarlo ahí.
            mesCuotaRepository.save(mesCuota);
        }
    }
    @Transactional
    public void crearCuotasParaEstudiante(Long id) {
        CuotaEntity cuota = findCuotaById(id);
        EstudianteEntity estudiante = cuota.getEstudiante();
        List<MesCuotaEntity> cuotas = new ArrayList<>();

        double montoBase = 1500000;

        // Obteniendo el año actual
        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);

        // Calculando años desde el egreso
        int yearsSinceGraduation = currentYear - estudiante.getAnnioEgreso().intValue();

        // Aplicando descuento basado en años desde el egreso
        if (yearsSinceGraduation < 1) {
            montoBase *= 0.85; // 15% de descuento
        } else if (yearsSinceGraduation >= 1 && yearsSinceGraduation <= 2) {
            montoBase *= 0.92; // 8% de descuento
        } else if (yearsSinceGraduation >= 3 && yearsSinceGraduation <= 4) {
            montoBase *= 0.96; // 4% de descuento
        }
        // Si son 5 años o más, no se aplica descuento y se mantiene el monto base

        int numeroCuotas = 0;
        double montoCuota = 0;
        String[] meses = {};
        int[] mesesNumerico = {};

        switch (estudiante.getTipoColegio()) {
            case "Municipal":
                montoCuota = (montoBase * 0.8) / 10;
                numeroCuotas = 10;
                meses = new String[]{"Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
                mesesNumerico = new int[]{Calendar.MARCH, Calendar.APRIL, Calendar.MAY, Calendar.JUNE, Calendar.JULY, Calendar.AUGUST, Calendar.SEPTEMBER, Calendar.OCTOBER, Calendar.NOVEMBER, Calendar.DECEMBER};
                break;


            case "Subvencionado":
                montoCuota = (montoBase * 0.9) / 7;
                numeroCuotas = 7;
                meses = new String[]{"Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre"};
                mesesNumerico = new int[]{Calendar.MARCH, Calendar.APRIL, Calendar.MAY, Calendar.JUNE, Calendar.JULY, Calendar.AUGUST, Calendar.SEPTEMBER};
                break;

            case "Privado":
                montoCuota = montoBase / 4;
                numeroCuotas = 4;
                meses = new String[]{"Marzo", "Abril", "Mayo", "Junio"};
                mesesNumerico = new int[]{Calendar.MARCH, Calendar.APRIL, Calendar.MAY, Calendar.JUNE};
                break;
        }

        for (int i = 0; i < numeroCuotas; i++) {
            MesCuotaEntity mesCuota = new MesCuotaEntity();
            mesCuota.setCuota(cuota);
            mesCuota.setPagado(false);
            mesCuota.setMonto(montoCuota);
            mesCuota.setMes(meses[i]);

            // Establece la fecha de vencimiento
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MONTH, mesesNumerico[i]);
            cal.set(Calendar.DAY_OF_MONTH, 11);
            mesCuota.setVencimiento(new Date(cal.getTimeInMillis()));

            cuotas.add(mesCuota);
        }

        cuota.setMesCuotas(cuotas);
        cuotaRepository.save(cuota);

    }


}
