package com.monolitico.TopEducation.controllers;

import com.monolitico.TopEducation.services.ExamenService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
public class ExamenController {

    @Autowired
    private ExamenService examenService;

    @PostMapping("/upload-exam-results")
    public String uploadExamenes(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            examenService.saveExamenesFromExcel(file);
            redirectAttributes.addFlashAttribute("message", "File uploaded and data saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Failed to upload file: " + e.getMessage());
        }
        return "index";  // Redirige al Menú Principal
    }

    @GetMapping("/examenes")
    public String showUploadForm() {
        return "examenes";  // asumiendo que tu archivo HTML se llama examenes.html y está en la carpeta de recursos
    }

    @PostMapping("/delete-all-exams")
    public String eliminarTodosLosExamenes(RedirectAttributes redirectAttributes) {
        examenService.eliminarTodosLosExamenes();
        redirectAttributes.addFlashAttribute("successMessage", "Todos los exámenes han sido eliminados.");
        return "index";
    }
}
