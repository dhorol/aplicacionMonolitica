package com.monolitico.TopEducation.controllers;

import com.monolitico.TopEducation.entities.EstudianteEntity;
import com.monolitico.TopEducation.repositories.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @PostMapping("/guardarEstudiante")
    public String guardarEstudiante(
            @RequestParam String rut,
            @RequestParam String apellidos,
            @RequestParam String nombres,
            @RequestParam Date fechaDeNacimiento, // Captura la fecha como una cadena
            @RequestParam String tipoColegio,
            @RequestParam String nombreColegio,
            @RequestParam Long annioEgreso) {



        // Crea una instancia de la entidad EstudianteEntity
        EstudianteEntity estudiante = new EstudianteEntity();
        estudiante.setRut(rut);
        estudiante.setApellidos(apellidos);
        estudiante.setNombres(nombres);
        estudiante.setFechaDeNacimiento(fechaDeNacimiento); // Asigna la fecha convertida
        estudiante.setTipoColegio(tipoColegio);
        estudiante.setNombreColegio(nombreColegio);
        estudiante.setAnnioEgreso(annioEgreso);

        estudianteRepository.save(estudiante);

        return "redirect:/index.html"; // Redirige a una página de éxito
    }
}
