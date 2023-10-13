package com.monolitico.TopEducation.services;

import com.monolitico.TopEducation.entities.CuotaEntity;
import com.monolitico.TopEducation.entities.MatriculaEntity;
import com.monolitico.TopEducation.repositories.EstudianteRepository;
import com.monolitico.TopEducation.repositories.CuotaRepository;
import com.monolitico.TopEducation.repositories.MatriculaRepository;

import com.monolitico.TopEducation.entities.EstudianteEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.sql.Date;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;
    @Autowired
    private CuotaRepository cuotaRepository;
    @Autowired
    private MatriculaRepository matriculaRepository;

    public void saveEstudent(
            String rut,
            String apellidos,
            String nombres,
            Date fechaDeNacimiento,
            String tipoColegio,
            String nombreColegio,
            Long annioEgreso) {

        // Crear una instancia de EstudianteEntity
        EstudianteEntity estudiante = new EstudianteEntity();
        estudiante.setRut(rut);
        estudiante.setApellidos(apellidos);
        estudiante.setNombres(nombres);
        estudiante.setFechaDeNacimiento(fechaDeNacimiento);
        estudiante.setTipoColegio(tipoColegio);
        estudiante.setNombreColegio(nombreColegio);
        estudiante.setAnnioEgreso(annioEgreso);

        // Crear una cuota inicial
        CuotaEntity cuotaInicial = new CuotaEntity();

        // Establecer la fecha del 15 de marzo de 2023 para la cuota inicial
        Calendar cal = Calendar.getInstance();
        cal.set(2023, Calendar.MARCH, 15);
        cuotaInicial.setFechaInicial(new Date(cal.getTimeInMillis()));

        // Guardar la cuota inicial en la base de datos
        cuotaRepository.save(cuotaInicial);

        // Crear una instancia de MatriculaEntity y configurarla
        MatriculaEntity matricula = new MatriculaEntity();
        matricula.setNombreMatricula("matricula");
        matricula.setPagado(false);
        matricula.setMonto(70000.0);
        matricula.setCuota(cuotaInicial);

        // Asociar la cuota inicial con la matricula
        cuotaInicial.setMatricula(matricula);

        // Guardar la matrícula en la base de datos
        matriculaRepository.save(matricula);

        // Asociar la cuota inicial con el estudiante
        estudiante.setCuota(cuotaInicial);

        // Guardar el estudiante en la base de datos
        estudianteRepository.save(estudiante);
    }

    public Iterable<EstudianteEntity> getAllEstudiantes() {
        return estudianteRepository.findAll();
    }

}