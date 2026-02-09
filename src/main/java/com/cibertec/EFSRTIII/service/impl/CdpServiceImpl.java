package com.cibertec.EFSRTIII.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.EFSRTIII.dto.ComprobantePagoDTO;
import com.cibertec.EFSRTIII.entity.CitaMedica;
import com.cibertec.EFSRTIII.entity.ComprobanteDePago;
import com.cibertec.EFSRTIII.repository.CdpRepository;
import com.cibertec.EFSRTIII.repository.ComprobanteDePagoRepository;
import com.cibertec.EFSRTIII.service.CdpService;

@Service
public class CdpServiceImpl implements CdpService{


	@Autowired
	private CdpRepository cdpRepository;
	
	@Override
	public ComprobantePagoDTO obtenerDatosComprobante(String nroCita) {
		
		CitaMedica cita = cdpRepository.findById(nroCita).orElse(null);
		
		
		if (cita == null) return null;
		
		ComprobantePagoDTO dto = new ComprobantePagoDTO();
		
		dto.setNroCita(cita.getNroCita());
		dto.setFechaCita(cita.getFechaCita().toLocalDate());
		dto.setHoraCita(cita.getHoraCita().toLocalTime());
		
		
		//PACIENTE
		dto.setNombrePaciente(cita.getPaciente().getNombres() + " " + cita.getPaciente().getApellidos());
		dto.setGeneroPaciente(cita.getPaciente().getGenero());
		dto.setEdadPaciente(cita.getPaciente().getEdad());
		dto.setCelularPaciente(cita.getPaciente().getCelular());
		dto.setCorreoPaciente(cita.getPaciente().getCorreo());
		
		
		//MEDICO
		dto.setNombreMedico(cita.getMedico().getNombres() + " " + cita.getMedico().getApellidos());
		dto.setEspecialidad(cita.getMedico().getEspecialidad().getEspecialidad());
		dto.setMonto(cita.getMedico().getEspecialidad().getMontoPorEspecialidad());
		
		return dto;
	}
	
	
	@Autowired
	private ComprobanteDePagoRepository comprobanteDePagoRepository;
	
	
	private String generarCodigoCDP() {
		// Obtener el último comprobante registrado (ordenado descendentemente por ID)
	    ComprobanteDePago ultimo = comprobanteDePagoRepository
	        .findTopByOrderByIdCDPDesc();

	    int nuevoNumero = 1;

	    if (ultimo != null && ultimo.getIdCDP().startsWith("CDP")) {
	        try {
	            // Extraer la parte numérica del código (por ejemplo, de "CDP004" → 4)
	            String numeroStr = ultimo.getIdCDP().substring(3); 
	            nuevoNumero = Integer.parseInt(numeroStr) + 1;
	        } catch (NumberFormatException e) {
	            nuevoNumero = 1; // fallback en caso de error de parseo
	        }
	    }

	    // Formatear con ceros a la izquierda (3 cifras)
	    return String.format("CDP%03d", nuevoNumero);
	}
	
	
	@Override
	public void registrarComprobante(String nroCita) {
	    CitaMedica cita = cdpRepository.findByNroCita(nroCita);

	    if (cita == null) return;

	    ComprobanteDePago cdp = new ComprobanteDePago();
	       
	 // Generar código único para el comprobante

	    String codigoGenerado = generarCodigoCDP();
	    cdp.setIdCDP(codigoGenerado);
	    
	    
	    
	    cdp.setCita(cita);
	    cdp.setFecha(cita.getFechaCita().toLocalDate());
	    cdp.setHora(cita.getHoraCita().toLocalTime());
	    cdp.setMonto(cita.getMedico().getEspecialidad().getMontoPorEspecialidad());
	    cdp.setEstado("pagado");

	    comprobanteDePagoRepository.save(cdp);
	}
	

	

	

}