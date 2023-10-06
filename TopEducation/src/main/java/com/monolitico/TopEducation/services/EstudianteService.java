package com.monolitico.TopEducation.services;

import com.monolitico.TopEducation.entities.CuotaEntity;
import com.monolitico.TopEducation.repositories.EstudianteRepository;
import com.monolitico.TopEducation.entities.EstudianteEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

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

        // Crear una instancia de CuotaEntity y establecerla en el estudiante
        CuotaEntity cuota = new CuotaEntity(estudiante);
        estudiante.setCuota(cuota);

        // Guardar el estudiante en la base de datos
        estudianteRepository.save(estudiante);
    }
}
