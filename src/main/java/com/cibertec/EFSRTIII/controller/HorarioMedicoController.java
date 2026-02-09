package com.cibertec.EFSRTIII.controller;

import java.sql.Time;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.EFSRTIII.entity.DiaSemana;
import com.cibertec.EFSRTIII.entity.Medico;
import com.cibertec.EFSRTIII.service.HorarioMedicoService;
import com.cibertec.EFSRTIII.service.MedicoService;

@Controller
@RequestMapping("/horariomedico")
public class HorarioMedicoController {
	@Autowired
	private HorarioMedicoService horarioMedicoService;
	
	@Autowired
	private MedicoService medicoService;
	
	@GetMapping("/registrar")
	public String registrarHorario(Model model,
	    @RequestParam(name = "success", required = false) String success) {
	    model.addAttribute("listaMedicos", medicoService.listarMedicos());
	    model.addAttribute("listaHorarios", horarioMedicoService.listarTodosLosHorarios());
	    if (success != null) {
	        model.addAttribute("success", true);
	    }

	    return "horariomedico/registrar";
	}
	
	@PostMapping("/registrar")
	public String registrarHorario(
			@RequestParam String idMedico,
			@RequestParam DiaSemana diaSemana,
			@RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime horaInicio,
	        @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime horaFin,
			Model model
			)
	{
		try {
			Medico medico = medicoService.buscarMedicoById(idMedico);
			Time horaInicioSql = Time.valueOf(horaInicio);
	        Time horaFinSql = Time.valueOf(horaFin);
	        
			horarioMedicoService.registrarHorario(medico,diaSemana,horaInicioSql,horaFinSql);
			return "redirect:/horariomedico/registrar?success";
		}catch(Exception e)
		{
			model.addAttribute("error", e.getMessage());
			model.addAttribute("listaMedicos", medicoService.listarMedicos());
		    model.addAttribute("listaHorarios", horarioMedicoService.listarTodosLosHorarios());
            return "horariomedico/registrar";
		}
	}
}
