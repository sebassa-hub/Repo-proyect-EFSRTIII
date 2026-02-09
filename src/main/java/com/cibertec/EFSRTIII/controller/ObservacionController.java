package com.cibertec.EFSRTIII.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.EFSRTIII.entity.Observaciones;
import com.cibertec.EFSRTIII.entity.Postulante;
import com.cibertec.EFSRTIII.service.ObservacionesService;
import com.cibertec.EFSRTIII.service.PostulanteService;


@Controller
@RequestMapping("/observaciones")
public class ObservacionController {

    @Autowired
    private ObservacionesService observacionesService;

    @Autowired
    private PostulanteService postulanteService;

    // Mostrar el formulario
    @GetMapping("/registrar/{id}")
    public String mostrarFormularioObservacion(@PathVariable("id") String idPostulante, Model model) {
        model.addAttribute("idPostulante", idPostulante);
        model.addAttribute("observacion", new Observaciones());
        return "observaciones/registrarObservacion";
    }

    // Guardar la observación
    @PostMapping("/guardarObservacion")
    public String guardarObservacion(@ModelAttribute Observaciones observacion,
                                     @RequestParam("idPostulante") String idPostulante,
                                     RedirectAttributes redirect) {

        Postulante postulante = postulanteService.buscarPostulanteById(idPostulante);
        observacion.setIdPostulante(postulante); // Asocia el postulante
        observacionesService.guardarObservaciones(observacion); // Guarda

        redirect.addFlashAttribute("exito", "Observación registrada correctamente.");
        return "redirect:/postulante/listar";
    }
}