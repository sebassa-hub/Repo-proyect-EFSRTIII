package com.cibertec.EFSRTIII.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.EFSRTIII.entity.Postulante;
import com.cibertec.EFSRTIII.service.PostulanteService;



@Controller
@RequestMapping("/postulante")
public class PostulanteController {

	@Autowired
	PostulanteService postulanteService;
	
	//MENU RRHH
	@GetMapping("/postulante/inicio")
	public String vistaMenuRRHH() {
        return "layout/layoutRRHH"; 
    }
	
	@GetMapping("/listar")
    public String listarPostulante(Model model) {
        List<Postulante> lista = postulanteService.listarPostulante();
        model.addAttribute("listarPostulante", lista);
        return "postulante/index";
    }
	
	
	 @GetMapping("/detalle/{id}")
    public String verDetallePostulante(@PathVariable("id") String idPostulante, Model model) {
        Postulante postulante = postulanteService.buscarPostulanteById(idPostulante);
        model.addAttribute("postulante", postulante);
        return "postulante/detallePostulante";
    }
	
	@GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        Postulante postulante = new Postulante();    
        model.addAttribute("postulante", postulante);
        return "postulante/registrarPostulante";
    }
	
	@PostMapping("/guardar")
    public String guardarPostulante(@ModelAttribute Postulante postulante, RedirectAttributes redirectAttrs) {
        Postulante postulanteExistente = postulanteService.buscarPostulanteByDni(postulante.getNumeroDocumento());

        if (postulanteExistente != null) {
            redirectAttrs.addFlashAttribute("error", "Ya existe un postulante con el mismo número de documento.");
            return "redirect:/postulante/registro";
        }

        postulanteService.guardarPostulante(postulante);
        redirectAttrs.addFlashAttribute("exito", "Postulante registrado correctamente");
        return "redirect:/postulante/registro";
    }
	
	@GetMapping("/editar/{id}")
    public String editarPostulante(@PathVariable("id") String idPostulante, Model model) {
        Postulante postulante = postulanteService.buscarPostulanteById(idPostulante);
        if (postulante != null) {
            model.addAttribute("postulante", postulante);
            return "postulante/editarPostulante";
        } else {
            return "redirect:/postulante/listar";
        }
    }
	
	@PostMapping("/actualizar")
    public String actualizarPostulante(@ModelAttribute Postulante postulante, RedirectAttributes redirectAttributes) {
        postulanteService.actualizarPostulante(postulante);
        redirectAttributes.addFlashAttribute("exito", "Cambios guardados correctamente");
        return "redirect:/postulante/listar";
    }

	//PDFS Postulante
	@GetMapping("/reportes")
	public String mostrarReportePostulante(@ModelAttribute("filtro") String filtro, Model model) {
	    List<Postulante> lista = postulanteService.listarPorFiltro(filtro);
	    model.addAttribute("listarPostulante", lista);
	    model.addAttribute("filtro", filtro); 
	    return "postulante/reportePostulante";
	}

	}
	
