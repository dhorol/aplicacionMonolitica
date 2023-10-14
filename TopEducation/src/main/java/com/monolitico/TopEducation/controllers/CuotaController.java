package com.monolitico.TopEducation.controllers;

import com.monolitico.TopEducation.entities.CuotaEntity;
import com.monolitico.TopEducation.services.OficinaCuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/detalleCuota")
public class CuotaController {

    @Autowired
    private OficinaCuotaService oficinaCuotaService;

    @GetMapping("/{id}")
    public String detalleCuota(@PathVariable Long id, Model model) {
        CuotaEntity cuota = oficinaCuotaService.findCuotaById(id);
        model.addAttribute("cuota", cuota);
        return "detalleCuota";
    }

    @PostMapping("/{id}")
    public String pagarMatricula(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        oficinaCuotaService.marcarMatriculaComoPagada(id);
        redirectAttributes.addFlashAttribute("message", "Matrícula marcada como pagada");
        return "redirect:/detalleCuota/" + id;
    }

    @PostMapping("/pagarArancel/{id}")
    public String gestionarPago(@PathVariable Long id, @RequestParam String opcionPago, RedirectAttributes redirectAttributes) {
        if ("contado".equals(opcionPago)) {
            oficinaCuotaService.pagarAlContado(id);
            redirectAttributes.addFlashAttribute("successMessage", "Pago al contado realizado con éxito.");
            return "redirect:/cuotasEstudiantes";  // Cambio aquí.
        } else {
            oficinaCuotaService.crearCuotasParaEstudiante(id);
            redirectAttributes.addFlashAttribute("successMessage", "Cuotas creadas con éxito.");
            return "redirect:/detalleCuota/" + id;
        }
    }



}
