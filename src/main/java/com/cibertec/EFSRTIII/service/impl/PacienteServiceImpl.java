package com.cibertec.EFSRTIII.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.EFSRTIII.entity.Paciente;
import com.cibertec.EFSRTIII.repository.PacienteRepository;
import com.cibertec.EFSRTIII.service.PacienteService;


@Service
public class PacienteServiceImpl implements PacienteService {
	
	
	@Autowired
	PacienteRepository pacienteRepository;

	@Override
	public Paciente guardarPaciente(Paciente userEntity) {
		 String nuevoId = generarCodigoPaciente();
	        userEntity.setIdPaciente(nuevoId);
	        // ✅ Calcular edad
	        userEntity.setEdad(calcularEdad(userEntity.getFechaNacimiento()));
	        // ✅ Asignar el estado de paciente como Activo siempre que se registre
	        userEntity.setEstado("activo"); 
	        // ✅ Asignar la fecha actual como fecha de registro
	        userEntity.setFechaRegistro(LocalDateTime.now());

	        return pacienteRepository.save(userEntity);
	}
	    
	@Override
	public Paciente actualizarPaciente(Paciente userEntity) {		
		return pacienteRepository.save(userEntity);
	}

	@Override
	public Paciente buscarPacienteByDni(String nDni) {
		return pacienteRepository.buscarPacienteByDni(nDni);
	}

	@Override
	public Paciente buscarPacienteByApellidos(String Apellidos) {
		return pacienteRepository.buscarPacienteByApellido(Apellidos);
	}

	@Override
	public Paciente buscarPacienteById(String idPaciente) {
		return pacienteRepository.findById(idPaciente).get();
	}

	@Override
	public void eliminarPacienteById(String idPaciente) {	
		pacienteRepository.deleteById(idPaciente);
	}

	@Override
	public List<Paciente> listarPacientes() {
	
		return pacienteRepository.listarPacientesOrdenados();
	}
	
	//METODO PARA AUTOGENERAR EL CODIGO DE PACIENTE
	private String generarCodigoPaciente() {
	    String ultimoId = pacienteRepository.obtenerUltimoId(); // ej: "CAP005"
	    if (ultimoId == null) {
	        return "PAC001";  // Primer código
	    }
	    int num = Integer.parseInt(ultimoId.substring(3)) + 1; // Extrae la parte numérica y suma 1
	    return String.format("PAC%03d", num); // Ejemplo resu ltado: "CAP006"
	}

	//METODO PARA CALCULAR LA EDAD DEL PACIENTE
	private int calcularEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) return 0;
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

	
	@Override
	public void cambiarEstadoPaciente(String idPaciente) {
	    Paciente paciente = pacienteRepository.findById(idPaciente).orElse(null);
	    if (paciente != null) {
	        String nuevoEstado = paciente.getEstado().equals("activo") ? "inactivo" : "activo";
	        paciente.setEstado(nuevoEstado);
	        pacienteRepository.save(paciente);
	    }
	}
}
