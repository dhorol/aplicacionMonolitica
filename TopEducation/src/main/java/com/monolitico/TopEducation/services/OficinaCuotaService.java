package com.monolitico.TopEducation.services;

import com.monolitico.TopEducation.entities.CuotaEntity;
import com.monolitico.TopEducation.entities.EstudianteEntity;
import com.monolitico.TopEducation.entities.ExamenEntity;
import com.monolitico.TopEducation.entities.MesCuotaEntity;
import com.monolitico.TopEducation.repositories.CuotaRepository;
import com.monolitico.TopEducation.repositories.EstudianteRepository;
import com.monolitico.TopEducation.repositories.MatriculaRepository;
import com.monolitico.TopEducation.repositories.MesCuotaRepository;
import com.monolitico.TopEducation.repositories.ExamenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

    @Autowired
    private ExamenRepository examenRepository;

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
    @Transactional
    public void calcularYAplicarIntereses(CuotaEntity cuota) {
        //Date fechaInicial = cuota.getFechaInicial();

        //Estas 3 lineas son para probar, al momento de la api real debe ser el de arriba que esta comentado
        Calendar cal = Calendar.getInstance();
        cal.set(2023, Calendar.MARCH, 14);
        Date fechaInicial= new Date(cal.getTimeInMillis());

        Calendar fechaActual = Calendar.getInstance();
        fechaActual.setTime(fechaInicial);
        int mesesDeAtraso;

        for (MesCuotaEntity mesCuota : cuota.getMesCuotas()) {
            if (!mesCuota.isPagado()) {
                java.sql.Date fechaActualSql = new java.sql.Date(fechaActual.getTimeInMillis());
                mesesDeAtraso = calcularMesesDeAtraso(mesCuota.getVencimiento(), fechaActualSql);
                aplicarInteres(mesCuota, mesesDeAtraso);
                mesCuotaRepository.save(mesCuota);  // Guarda el cambio en la base de datos
            }
        }
        cuotaRepository.save(cuota);
    }



    private int calcularMesesDeAtraso(Date vencimiento, Date fechaActual) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(vencimiento);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(fechaActual);

        int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        return diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
    }

    private void aplicarInteres(MesCuotaEntity mesCuota, int mesesDeAtraso) {
        double interes = 0;
        if (mesesDeAtraso == 1) {
            interes = 0.03;
        } else if (mesesDeAtraso == 2) {
            interes = 0.06;
        } else if (mesesDeAtraso == 3) {
            interes = 0.09;
        } else if (mesesDeAtraso > 3) {
            interes = 0.15 + (0.15 * (mesesDeAtraso - 3));
        }

        double nuevoMonto = mesCuota.getMonto() * (1 + interes);
        nuevoMonto = (double) Math.round(nuevoMonto);
        mesCuota.setMonto(nuevoMonto);
    }
    public MesCuotaEntity findMesCuotaById(Long id) {
        return mesCuotaRepository.findById(id).orElse(null);
    }

    @Transactional
    public void pagarMesCuota(Long mesCuotaId, boolean pagado) {
        MesCuotaEntity mesCuota = findMesCuotaById(mesCuotaId);

        if (mesCuota == null) {
            throw new RuntimeException("Mes de cuota no encontrado.");
        }

        mesCuota.setPagado(pagado);
        mesCuotaRepository.save(mesCuota);

        verificarYActualizarCuotaComoPagada(mesCuota.getCuota());
    }

    private void verificarYActualizarCuotaComoPagada(CuotaEntity cuota) {
        // Verificar si todos los MesCuotaEntity asociados a CuotaEntity están pagados
        boolean todosPagados = cuota.getMesCuotas().stream().allMatch(MesCuotaEntity::isPagado);

        if (todosPagados) {
            cuota.setPagado(true);
            cuotaRepository.save(cuota);
        }
    }

    @Transactional
    public void aplicarDescuentoPorPromedioExamen(CuotaEntity cuota) {
        EstudianteEntity estudiante = cuota.getEstudiante();
        List<ExamenEntity> examenes = examenRepository.findByEstudianteId(estudiante.getId());

        double sumaPuntajes = 0.0;
        for (ExamenEntity examen : examenes) {
            sumaPuntajes += examen.getPuntaje();
        }

        double promedio = examenes.size() > 0 ? sumaPuntajes / examenes.size() : 0;

        double descuento = 0;
        if (promedio >= 950) {
            descuento = 0.10;
        } else if (promedio >= 900) {
            descuento = 0.05;
        } else if (promedio >= 850) {
            descuento = 0.02;
        }

        // Obtener el mes actual
        int mesActual = LocalDate.now().getMonthValue();

        // Usa el mes de prueba en lugar del mes actual
        //int mesActual = //MES_PRUEBA(Es el numero del mes; ej: marzo=3;

        for (MesCuotaEntity mesCuota : cuota.getMesCuotas()) {
            // Solo aplicar el descuento si el mes de MesCuotaEntity es el mes actual
            if (mesCuota.getMesNumerico() == mesActual) {
                double montoConDescuento = mesCuota.getMonto() * (1 - descuento);
                mesCuota.setMonto(montoConDescuento);
                mesCuotaRepository.save(mesCuota);
            }
        }
        cuotaRepository.save(cuota);
    }







}
