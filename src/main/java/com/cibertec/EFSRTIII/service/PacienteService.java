package com.cibertec.EFSRTIII.service;
import java.util.List;

import com.cibertec.EFSRTIII.entity.Paciente;



public interface PacienteService {
	
	public Paciente guardarPaciente(Paciente userEntity);
	public Paciente actualizarPaciente(Paciente userEntity);
	public Paciente buscarPacienteByDni(String nDni);
	public Paciente buscarPacienteByApellidos(String Apellidos);
	public Paciente buscarPacienteById(String idPaciente);
	public void eliminarPacienteById(String idPaciente);
	public List<Paciente> listarPacientes();
	public void cambiarEstadoPaciente(String idPaciente);

}
