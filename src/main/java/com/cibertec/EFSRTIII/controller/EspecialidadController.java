package com.cibertec.EFSRTIII.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.EFSRTIII.entity.Especialidad;
import com.cibertec.EFSRTIII.service.EspecialidadService;


@Controller
@RequestMapping("/especialidad")
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;

    @GetMapping("/listar")
    public String listarEspecialidad(Model model) {
        List<Especialidad> listaesp = especialidadService.listarEspecialidad();
        model.addAttribute("listaEspecialidad", listaesp);
        model.addAttribute("especialidad", new Especialidad());
        return "especialidad/index";
    }

    @GetMapping("/detalleEsp/{id}")
    public String verDetalleEspecialidad(@PathVariable("id") String idEspecialidad, Model model) {
        Especialidad esp = especialidadService.buscarByEspecialidadId(idEspecialidad);
        model.addAttribute("especialidad", esp);
        return "especialidad/detalleEspecialidad";
    }

    @GetMapping("/editarEsp/{id}")
    public String editarEspecialidad(@PathVariable("id") String idEspecialidad, Model model) {
        Especialidad esp = especialidadService.buscarByEspecialidadId(idEspecialidad);
        if (esp != null) {
            model.addAttribute("especialidad", esp);
            return "especialidad/editarEspecialidad";
        } else {
            return "redirect:/especialidad/listar";
        }
    }
    
    @PostMapping("/editarEsp")
    public String actualizarEspecialidad(@ModelAttribute Especialidad especialidad, RedirectAttributes redirectAttrs) {
        especialidadService.actualizarEspecialidad(especialidad);
        redirectAttrs.addFlashAttribute("exito", "Especialidad actualizada correctamente.");
        return "redirect:/especialidad/listar";
    }
    
    
    @GetMapping("/registrarEspecialidad")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("especialidad", new Especialidad());
        return "especialidad/registrarEspecialidad";
    }

    @PostMapping("/guardarEsp")
    public String guardarEspecialidad(@ModelAttribute Especialidad especialidad, RedirectAttributes redirectAttrs) {
        // Buscar por nombre de especialidad
        Especialidad existente = especialidadService.buscarByEspecialidad(especialidad.getEspecialidad());

        if (existente != null) {
            redirectAttrs.addFlashAttribute("error", "Ya existe una especialidad con ese nombre.");
            return "redirect:/especialidad/listar";
        }

        // Guardar solo si no existe
        especialidadService.guardarEspecialidad(especialidad);
        redirectAttrs.addFlashAttribute("exito", "Especialidad registrada correctamente.");
        return "redirect:/especialidad/listar";
    }
    
    
}
