package com.cibertec.EFSRTIII.service;

import java.sql.Time;
import java.util.List;

import com.cibertec.EFSRTIII.entity.DiaSemana;
import com.cibertec.EFSRTIII.entity.HorarioMedico;
import com.cibertec.EFSRTIII.entity.Medico;


public interface HorarioMedicoService {
	public void registrarHorario(Medico idMedico, DiaSemana diaSemana, Time horaInicio, Time horaFin);
	public List<HorarioMedico> listarTodosLosHorarios();
	public HorarioMedico registrarHorario(HorarioMedico horarioMedico);
	HorarioMedico buscarHorarioPorMedicoYDia(String idMedico, DiaSemana diaSemana);
}
