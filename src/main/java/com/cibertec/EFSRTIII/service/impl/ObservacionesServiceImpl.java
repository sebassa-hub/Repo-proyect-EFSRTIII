package com.cibertec.EFSRTIII.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.EFSRTIII.entity.Observaciones;
import com.cibertec.EFSRTIII.repository.ObservacionesRepository;
import com.cibertec.EFSRTIII.service.ObservacionesService;

@Service
public class ObservacionesServiceImpl implements ObservacionesService{

	@Autowired
	ObservacionesRepository obsRepository;
	
	@Override
	public Observaciones guardarObservaciones(Observaciones userEntity) {
		String nuevoId = generarCodigoObservacion();
			userEntity.setIdObs(nuevoId);
			userEntity.setFechaRegistro(LocalDateTime.now());
		return obsRepository.save(userEntity);
	}

	//METODO PARA AUTOGENERAR EL CODIGO DE OBSERVACIONES
			private String generarCodigoObservacion() {
			    String ultimoId = obsRepository.obtenerUltimoId(); // ej: "POS005"
			    if (ultimoId == null) {
			        return "OBS001";  // Primer código
			    }
			    int num = Integer.parseInt(ultimoId.substring(3)) + 1; // Extrae la parte numérica y suma 1
			    return String.format("OBS%03d", num); // Ejemplo resultado: "POS006"
			}
	
}
