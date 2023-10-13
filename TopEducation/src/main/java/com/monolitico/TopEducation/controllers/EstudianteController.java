package com.monolitico.TopEducation.controllers;


import com.monolitico.TopEducation.entities.EstudianteEntity;
import com.monolitico.TopEducation.services.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;


@Controller
@RequestMapping
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;
    @GetMapping("/registroEstudiantes")
    public String mostrarFormularioRegistroEstudiantes() {
        return "registroEstudiantes"; // El nombre de la página HTML sin la extensión ".html"
    }
    @PostMapping("/guardarEstudiante")
    public String guardarEstudiante(
            @RequestParam String rut,
            @RequestParam String apellidos,
            @RequestParam String nombres,
            @RequestParam Date fechaDeNacimiento, // Captura la fecha como una cadena
            @RequestParam String tipoColegio,
            @RequestParam String nombreColegio,
            @RequestParam Long annioEgreso) {

        estudianteService.saveEstudent(rut,apellidos,nombres,fechaDeNacimiento,tipoColegio,nombreColegio,annioEgreso);

        return "index"; // Redirige a una página de éxito
    }
    @GetMapping("/cuotasEstudiantes")
    public String listarEstudiantes(Model model) {
        Iterable<EstudianteEntity> estudiantes = estudianteService.getAllEstudiantes();
        model.addAttribute("estudiantes", estudiantes);
        return "cuotasEstudiantes"; // nombre de la página HTML que mostrará la lista
    }


}
