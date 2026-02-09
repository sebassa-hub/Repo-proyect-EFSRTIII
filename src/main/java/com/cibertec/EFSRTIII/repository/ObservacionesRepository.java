package com.cibertec.EFSRTIII.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cibertec.EFSRTIII.entity.Observaciones;


@Repository
public interface ObservacionesRepository extends JpaRepository<Observaciones, String>{
	//Listar observaciones
	@Query(value="SELECT * FROM observaciones o", nativeQuery = true)
	List<Observaciones> listarObservaciones();
	
	@Query(value="SELECT idObs FROM observaciones ORDER BY CAST(SUBSTRING(idObs,4)AS UNSIGNED) DESC LIMIT 1", nativeQuery = true)
	String obtenerUltimoId();
}
