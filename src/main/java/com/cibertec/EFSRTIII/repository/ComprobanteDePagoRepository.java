package com.cibertec.EFSRTIII.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.EFSRTIII.entity.ComprobanteDePago;


public interface ComprobanteDePagoRepository extends JpaRepository<ComprobanteDePago, String>{

	ComprobanteDePago findByCita_NroCita(String nroCita);
	
	
	// Nuevos métodos para búsqueda
    List<ComprobanteDePago> findByCita_Paciente_ApellidosContainingIgnoreCase(String apellido);

    List<ComprobanteDePago> findByCita_Medico_ApellidosContainingIgnoreCase(String apellido);
	
    ComprobanteDePago findTopByOrderByIdCDPDesc();
}
