package com.cibertec.EFSRTIII.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.EFSRTIII.dto.ComprobantePagoDTO;
import com.cibertec.EFSRTIII.entity.CitaMedica;
import com.cibertec.EFSRTIII.entity.ComprobanteDePago;
import com.cibertec.EFSRTIII.repository.ComprobanteDePagoRepository;
import com.cibertec.EFSRTIII.service.CdpService;
import com.cibertec.EFSRTIII.service.CitaMedicaService;

@Controller
@RequestMapping("/cajero/citas")
public class CajeroCitaController {

	@Autowired
	private CitaMedicaService citaMedicaService;
	
	
	//LISTAR TODAS LAS CITAS ORDENADAS POR EL NRO CITA
	@GetMapping("/listar")
	public String listarCitas(Model model) {
		
		List<CitaMedica> lista = citaMedicaService.listarCitasOrdenadas();
		model.addAttribute("citas", lista);
		
		return "cajero/buscarCitas";  //vista del modulo de cajero
	}
	
	
	//BUSCAR CITAS POR EL ID DEL MEDICO
	
	@GetMapping("/buscar/medico")
	public String buscarPorMedico(@RequestParam("idMedico") String idMedico, Model model) {
		
		List<CitaMedica> citas = citaMedicaService.buscarPorMedico(idMedico);
		model.addAttribute("citas", citas);
		
		return "cajero/buscarCitas";
	}
	
	//BUSCAR CITAS POR EL ID DEL PACIENTE
	@GetMapping("/buscar/paciente")
	public String buscarPorPaciente(@RequestParam("idPaciente")String idPaciente, Model model) {
		
		List<CitaMedica> citas = citaMedicaService.buscarPorPaciente(idPaciente);
		model.addAttribute("citas", citas);
		
		return "cajero/buscarCitas";	
	}
	
	//BUSCAR POR EL ESTADO
	@GetMapping("/buscar/estado")
	public String buscarPorEstado(@RequestParam("estado")String estadoStr, Model model) {
		
		try {
			
			CitaMedica.EstadoCita estado = CitaMedica.EstadoCita.valueOf(estadoStr);
			
			List<CitaMedica> citas = citaMedicaService.buscarPorEstado(estado);
			model.addAttribute("citas", citas);
		} catch (IllegalArgumentException e) {
			
			model.addAttribute("citas", List.of());
			model.addAttribute("error", "Estado Inválido: " + estadoStr);
		}
		
		return "cajero/buscarCitas";	
	}
	
	
	
	
	//SECCION PARA VISUALIZAR CDP
	@Autowired
	private CdpService cdpService;
	
	
	@GetMapping("/generarCDP")
	public String generarCDP(@RequestParam("nroCita") String nroCita, Model model) {
		
		
		ComprobantePagoDTO dto = cdpService.obtenerDatosComprobante(nroCita);
		
		if (dto == null) {
		    model.addAttribute("mensajeError", "No se encontró la cita con el código: " + nroCita);
		    return "/cajero/buscarCitas"; 
		}
		
		model.addAttribute("comprobante", dto);
		return "/cajero/generarCDP"; 
	}
	
	
	//SECCION DETALLE CITA
	
	@GetMapping("/detalle/{nroCita}")
	public String verDetalleCita(@PathVariable String nroCita, Model model) {
		
		ComprobantePagoDTO dto = cdpService.obtenerDatosComprobante(nroCita);
		
	    if (dto == null) {
	        model.addAttribute("mensajeError", "No se encontró la cita con el número: " + nroCita);
	        return "cajero/buscarCitas";
	    }

	    model.addAttribute("detalle", dto);
	    return "cajero/detalleCita";
		
	}
	
	//sSECCION PARA GENERAR EL CDP
	
	@PostMapping("/guardarCDP")
	public String guardarComprobante(@RequestParam("nroCita") String nroCita, RedirectAttributes redirectAttributes) {
	    cdpService.registrarComprobante(nroCita);
	    redirectAttributes.addFlashAttribute("mensaje", "Comprobante generado con éxito.");
	    return "redirect:/cajero/citas/listar";
	}

	
	//SECCION PARA LISTAR COMPROBANTES DE PAGO
	
	@Autowired
	private ComprobanteDePagoRepository comprobanteDePagoRepository;
	
	@GetMapping("/cdps")
	public String listarComprobantes(Model model) {
	    List<ComprobanteDePago> lista = comprobanteDePagoRepository.findAll();
	    model.addAttribute("cdps", lista);
	    return "cajero/listarCDP"; // Nombre de la vista Thymeleaf
	}
	
	
	
	@GetMapping("/cdps/buscar")
	public String buscarCDP(
	        @RequestParam("tipo") String tipo,
	        @RequestParam("valor") String valor,
	        Model model) {

	    List<ComprobanteDePago> resultados;

	    switch (tipo) {
	        case "idCDP":
	            ComprobanteDePago encontrado = comprobanteDePagoRepository.findById(valor).orElse(null);
	            resultados = (encontrado != null) ? List.of(encontrado) : List.of();
	            break;

	        case "apellidoPaciente":
	            resultados = comprobanteDePagoRepository.findByCita_Paciente_ApellidosContainingIgnoreCase(valor);
	            break;

	        case "apellidoMedico":
	            resultados = comprobanteDePagoRepository.findByCita_Medico_ApellidosContainingIgnoreCase(valor);
	            break;

	        default:
	            resultados = List.of(); // búsqueda vacía por defecto
	    }

	    model.addAttribute("cdps", resultados);
	    return "cajero/listarCDP";
	}

	

    
    //ACTUALIZAR ESTADO DE CITA
    
    @GetMapping("/editar/{nroCita}")
    public String editarEstado(@PathVariable String nroCita, Model model) {
        ComprobantePagoDTO dto = cdpService.obtenerDatosComprobante(nroCita);
        
        if (dto == null) {
            model.addAttribute("mensajeError", "No se encontró la cita.");
            return "redirect:/cajero/citas/listar";
        }

        model.addAttribute("detalle", dto);
        return "cajero/editarCita";
    }

    @PostMapping("/actualizarEstado")
    public String actualizarEstadoCita(
            @RequestParam("nroCita") String nroCita,
            @RequestParam("nuevoEstado") String nuevoEstado,
            RedirectAttributes redirectAttributes) {

        try {
            CitaMedica.EstadoCita estadoEnum = CitaMedica.EstadoCita.valueOf(nuevoEstado);
            citaMedicaService.actualizarEstadoCita(nroCita, estadoEnum);
            redirectAttributes.addFlashAttribute("mensaje", "Estado actualizado correctamente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Estado no válido.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al actualizar el estado.");
        }

        return "redirect:/cajero/citas/listar";
    }

	
}
