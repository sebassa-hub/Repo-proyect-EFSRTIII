package com.cibertec.EFSRTIII.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.EFSRTIII.entity.Paciente;
import com.cibertec.EFSRTIII.service.PacienteService;


@Controller
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;
   
  
    //MENU CAJERO
    @GetMapping("/cajero/inicio")
    public String vistaMenuCajero() {
        return "layout/layoutCajero";
    }
    
    //MENU RECEPCIONISTA
    @GetMapping("/recepcion/inicio")
    public String vistaMenuRecepcionista() {
        return "layout/layoutRecepcionista"; 
    }
      
    @PostMapping("/cambiarEstado/{id}")
    public String cambiarEstado(@PathVariable("id") String id, RedirectAttributes redirect) {
        pacienteService.cambiarEstadoPaciente(id);
        redirect.addFlashAttribute("exito", "Estado actualizado correctamente");
        return "redirect:/paciente/listar";
    }
    
    @GetMapping("/listar")
    public String listarPacientes(Model model) {
        List<Paciente> lista = pacienteService.listarPacientes();
        model.addAttribute("listaPacientes", lista);
        return "paciente/index";
    }
    
    @GetMapping("/detalle/{id}")
    public String verDetallePaciente(@PathVariable("id") String idPaciente, Model model) {
        Paciente paciente = pacienteService.buscarPacienteById(idPaciente);
        model.addAttribute("paciente", paciente);
        return "paciente/detallePaciente";
    }
    
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        Paciente paciente = new Paciente();    
        model.addAttribute("paciente", paciente);
        return "paciente/registrarPaciente";
    }

 // PacienteController.java
    @PostMapping("/guardar")
    public String guardarPaciente(@ModelAttribute Paciente paciente, RedirectAttributes redirectAttrs) {
        Paciente pacienteExistente = pacienteService.buscarPacienteByDni(paciente.getNumeroDocumento());

        if (pacienteExistente != null) {
            redirectAttrs.addFlashAttribute("error", "Ya existe un paciente con el mismo número de documento.");
            return "redirect:/paciente/registro";
        }

        pacienteService.guardarPaciente(paciente);
        redirectAttrs.addFlashAttribute("exito", "Paciente registrado correctamente");
        return "redirect:/paciente/registro";
    }
   
    
    // ✅ MOSTRAR FORMULARIO DE EDICIÓN
    @GetMapping("/editar/{id}")
    public String editarPaciente(@PathVariable("id") String idPaciente, Model model) {
        Paciente paciente = pacienteService.buscarPacienteById(idPaciente);
        if (paciente != null) {
            model.addAttribute("paciente", paciente);
            return "paciente/editarPaciente";
        } else {
            return "redirect:/paciente/listar";
        }
    }

    // ✅ ACTUALIZAR PACIENTE (vía POST)
    @PostMapping("/actualizar")
    public String actualizarPaciente(@ModelAttribute Paciente paciente, RedirectAttributes redirectAttributes) {
        pacienteService.actualizarPaciente(paciente);
        redirectAttributes.addFlashAttribute("exito", "Cambios guardados correctamente");
        return "redirect:/paciente/listar";
    }

    
    // ✅ ELIMINAR PACIENTE
    @GetMapping("/eliminar/{id}")
    public String eliminarPaciente(@PathVariable("id") String idPaciente, RedirectAttributes redirectAttributes) {
        pacienteService.eliminarPacienteById(idPaciente);
        redirectAttributes.addFlashAttribute("exito", "Paciente eliminado correctamente");
        return "redirect:/paciente/listar";
    }
}
