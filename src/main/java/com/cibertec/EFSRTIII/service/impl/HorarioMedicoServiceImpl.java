package com.cibertec.EFSRTIII.service.impl;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.EFSRTIII.entity.DiaSemana;
import com.cibertec.EFSRTIII.entity.HorarioMedico;
import com.cibertec.EFSRTIII.entity.Medico;
import com.cibertec.EFSRTIII.repository.HorarioMedicoRepository;
import com.cibertec.EFSRTIII.repository.MedicoRepository;
import com.cibertec.EFSRTIII.service.HorarioMedicoService;

@Service
public class HorarioMedicoServiceImpl implements HorarioMedicoService{

	@Autowired
	private HorarioMedicoRepository horarioMedicoRepository;
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Override
	public void registrarHorario(Medico idMedico, DiaSemana diaSemana, LocalTime horaInicio, LocalTime horaFin) {
	    List<HorarioMedico> solapados = horarioMedicoRepository.buscarHorariosSolapados(
	            idMedico.getIdMedico(), diaSemana, horaInicio, horaFin);

	    if (!solapados.isEmpty()) {
	        throw new RuntimeException("Ya existe un horario que se cruza con este rango para ese médico.");
	    }

	    HorarioMedico horario = new HorarioMedico();
	    horario.setDiaSemana(diaSemana);
	    horario.setHoraInicio(horaInicio);
	    horario.setHoraFin(horaFin);
	    horario.setIdMedico(medicoRepository.findById(idMedico.getIdMedico())
	            .orElseThrow(() -> new RuntimeException("Médico no encontrado")));

	    horarioMedicoRepository.save(horario);
	}

	@Override
	public List<HorarioMedico> listarTodosLosHorarios() {
		return horarioMedicoRepository.findAll();
	}

	@Override
	public HorarioMedico registrarHorario(HorarioMedico horario) {

	    List<HorarioMedico> solapados =
	            horarioMedicoRepository.buscarHorariosSolapados(
	                    horario.getIdMedico().getIdMedico(),
	                    horario.getDiaSemana(),
	                    horario.getHoraInicio(),
	                    horario.getHoraFin());

	    if (!solapados.isEmpty()) {
	        throw new RuntimeException("Ya existe un horario que se cruza con este rango para ese médico.");
	    }

	    return horarioMedicoRepository.save(horario);
	}


	@Override
	public HorarioMedico buscarHorarioPorMedicoYDia(String idMedico, DiaSemana diaSemana) {
		 return horarioMedicoRepository.buscarHorarioPorMedicoYDia(idMedico, diaSemana);
	}
}
