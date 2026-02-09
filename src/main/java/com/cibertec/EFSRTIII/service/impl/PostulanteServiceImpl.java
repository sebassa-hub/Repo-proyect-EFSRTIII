package com.cibertec.EFSRTIII.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.EFSRTIII.entity.Postulante;
import com.cibertec.EFSRTIII.repository.PostulanteRepository;
import com.cibertec.EFSRTIII.service.PostulanteService;
@Service
public class PostulanteServiceImpl implements PostulanteService{
	
	@Autowired
	PostulanteRepository postulanteRepository;

	@Override
	public Postulante guardarPostulante(Postulante userEntity) {
		 String nuevoId = generarCodigoPostulante();
	        userEntity.setIdPostulante(nuevoId);
	        // ✅ Calcular edad
	        userEntity.setEdad(calcularEdad(userEntity.getFechaNacimiento()));
	     // ✅ Asignar el estado de postulante como "postulante en evaluacion" siempre que se registre
	        userEntity.setEstado("postulante en evaluación"); 
	        // ✅ Asignar la fecha actual como fecha de registro
	        userEntity.setFechaRegistro(LocalDateTime.now());

	        return postulanteRepository.save(userEntity);
	}

	@Override
	public Postulante actualizarPostulante(Postulante userEntity) {
		return postulanteRepository.save(userEntity);
	}

	@Override
	public Postulante buscarPostulanteByDni(String nDni) {
		return postulanteRepository.buscarPostulanteByDni(nDni);
	}

	@Override
	public Postulante buscarPostulanteByApellidos(String Apellidos) {
		return postulanteRepository.buscarPostulanteByApellido(Apellidos);
	}

	@Override
	public Postulante buscarPostulanteById(String idPost) {
		return postulanteRepository.findById(idPost).get();
	}

	@Override
	public void eliminarPostulanteById(String idPost) {
		postulanteRepository.deleteById(idPost);
		
	}
	
	@Override
	public List<Postulante> listarPostulante() {
		return postulanteRepository.listarPostulante();
	}

	
	//METODO PARA AUTOGENERAR EL CODIGO DE POSTULANTE
		private String generarCodigoPostulante() {
		    String ultimoId = postulanteRepository.obtenerUltimoId(); // ej: "POS005"
		    if (ultimoId == null) {
		        return "POS001";  // Primer código
		    }
		    int num = Integer.parseInt(ultimoId.substring(3)) + 1; // Extrae la parte numérica y suma 1
		    return String.format("POS%03d", num); // Ejemplo resultado: "POS006"
		}
		
		//METODO PARA CALCULAR LA EDAD DEL PACIENTE
		private int calcularEdad(LocalDate fechaNacimiento) {
	        if (fechaNacimiento == null) return 0;
	        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
	    }

		
		
		//Buscar por ID(Report PDF Postulante) xd
		@Override
		public List<Postulante> listarPorFiltro(String filtro) {
			  if (filtro == null || filtro.trim().isEmpty()) {
		            return postulanteRepository.findAll();
		        } else {
		            return postulanteRepository.buscarPorIdPostulante(filtro.trim());
		        }
		}
}
