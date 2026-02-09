package com.cibertec.EFSRTIII.service.impl;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.EFSRTIII.entity.CitaMedica;
import com.cibertec.EFSRTIII.entity.CitaMedica.EstadoCita;
import com.cibertec.EFSRTIII.repository.CitaMedicaRepository;
import com.cibertec.EFSRTIII.service.CitaMedicaService;


@Service
public class CitaMedicaServiceImpl implements CitaMedicaService {

    @Autowired
    private CitaMedicaRepository citaRepo;

    @Override
    public List<CitaMedica> listarPorMedicoYFecha(String idMedico, Date fechaCita) {
        return citaRepo.findByMedico_IdMedicoAndFechaCita(idMedico, fechaCita);
    }

    @Override
    public boolean existeCita(String idMedico, Date fechaCita, Time horaCita) {
        return citaRepo.existsByMedico_IdMedicoAndFechaCitaAndHoraCita(idMedico, fechaCita, horaCita);
    }

    @Override
    public void registrar(CitaMedica cita) {
    	 cita.setNroCita(generarNroCita());
        citaRepo.save(cita);
    }
    
    private String generarNroCita() {
        String ultimo = citaRepo.obtenerUltimoNroCita(); // Ejemplo: "CIT005"
        if (ultimo == null) {
            return "CIT001";
        }
        int numero = Integer.parseInt(ultimo.substring(3)) + 1;
        return String.format("CIT%03d", numero); // Resultado: "CIT006"
    }
    

    //PARTE DE CAJERO - NO TOCAR NADA
    
	@Override
	public List<CitaMedica> listarTodas() {
		return citaRepo.findAll();
	}

	@Override
	public List<CitaMedica> listarCitasOrdenadas() {
		return citaRepo.listarCitasOrdenadas();
	}

	@Override
	public List<CitaMedica> buscarPorMedico(String idMedico) {
		return citaRepo.buscarCitasPorMedico(idMedico);
	}

	@Override
	public List<CitaMedica> buscarPorPaciente(String idPaciente) {
		return citaRepo.buscarCitasPorPaciente(idPaciente);
	}

	@Override
	public List<CitaMedica> buscarPorEstado(CitaMedica.EstadoCita estado) {
	    return citaRepo.buscarCitasPorEstado(estado);
	}


	@Override
	public void eliminarCita(String nroCita) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String obtenerUltimoNroCita() {

		return citaRepo.obtenerUltimoNroCita();
		
	}

	@Override
	public CitaMedica guardarCita(CitaMedica cita) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CitaMedica obtenerPorId(String nroCita) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//metodo para el GENERAR CDP
	
	public CitaMedica obtenerCitaConDetalles(String nroCita) {
		
		return citaRepo.findById(nroCita).orElse(null);
		
	}

    
	
	//ACTUALIZAR ESTADO CITA
	
	@Override
	public void actualizarEstadoCita(String nroCita, EstadoCita nuevoEstado) {
	    CitaMedica cita = citaRepo.findById(nroCita).orElse(null);
	    
	    if (cita != null) {
	        cita.setEstado(nuevoEstado);
	        citaRepo.save(cita);
	    } else {
	        throw new RuntimeException("No se encontró la cita con el Nro: " + nroCita);
	    }
	}


    
}
