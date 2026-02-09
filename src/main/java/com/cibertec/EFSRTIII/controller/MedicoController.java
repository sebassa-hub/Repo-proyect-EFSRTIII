package com.cibertec.EFSRTIII.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.EFSRTIII.entity.Medico;
import com.cibertec.EFSRTIII.service.EspecialidadService;
import com.cibertec.EFSRTIII.service.MedicoService;

@Controller
@RequestMapping("/medico")
public class MedicoController {
	
    @Autowired
    private MedicoService medicoService;

    @Autowired
    private EspecialidadService especialidadService;
    
    //Medico
    @GetMapping("/listar")
    public String listarMedicos(Model model) {
        List<Medico> lista = medicoService.listarMedicos();
        model.addAttribute("listaMedicos", lista);

        // Necesario para que el modal funcione
        model.addAttribute("medico", new Medico());
        model.addAttribute("listaEspecialidades",especialidadService.listarEspecialidad());

        return "medico/index";
    }
    
    @PostMapping("/cambiarEstado/{id}")
    public String cambiarEstado(@PathVariable("id") String id, RedirectAttributes redirect) {
        medicoService.cambiarEstadoMedico(id);
        redirect.addFlashAttribute("exito", "Estado actualizado correctamente");
        return "redirect:/medico/listar";
    }
    
    @GetMapping("/detalle/{id}")
    public String verDetalleMedico(@PathVariable("id") String idMedico, Model model) {
        Medico medico = medicoService.buscarMedicoById(idMedico);
        model.addAttribute("medico", medico);
        return "medico/detalleMedico";
    }

    @PostMapping("/guardar")
    public String guardarMedico(@ModelAttribute Medico medico, RedirectAttributes redirectAttrs) {
        Medico existente = medicoService.buscarMedicoByDni(medico.getNumeroDocumento());
        if (existente != null) {
            redirectAttrs.addFlashAttribute("error", "Ya existe un médico con el mismo número de documento.");
            return "redirect:/medico/listar";
        }

        medicoService.guardarMedico(medico);
        redirectAttrs.addFlashAttribute("exito", "Médico registrado correctamente.");
        return "redirect:/medico/listar";
    }

    @GetMapping("/editar/{id}")
    public String editarMedico(@PathVariable("id") String idMedico, Model model) {
        Medico medico = medicoService.buscarMedicoById(idMedico);
        if (medico != null) {
            model.addAttribute("medico", medico);
            model.addAttribute("listaEspecialidades", especialidadService.listarEspecialidad());
            return "medico/editarMedico";
        } else {
            return "redirect:/medico/listar";
        }
    }

    @PostMapping("/actualizar")
    public String actualizarMedico(@ModelAttribute Medico medico, RedirectAttributes redirectAttrs) {
        medicoService.actualizarMedico(medico);
        redirectAttrs.addFlashAttribute("exito", "Cambios guardados correctamente.");
        return "redirect:/medico/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarMedico(@PathVariable("id") String idMedico, RedirectAttributes redirectAttrs) {
        medicoService.eliminarMedicoById(idMedico);
        redirectAttrs.addFlashAttribute("exito", "Médico eliminado correctamente.");
        return "redirect:/medico/listar";
    }     
}
