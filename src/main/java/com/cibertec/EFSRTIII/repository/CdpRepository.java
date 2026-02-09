package com.cibertec.EFSRTIII.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.EFSRTIII.entity.CitaMedica;


public interface CdpRepository extends JpaRepository<CitaMedica, String>{

	CitaMedica findByNroCita (String nroCita);
	
	
}
