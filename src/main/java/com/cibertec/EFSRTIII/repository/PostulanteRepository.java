package com.cibertec.EFSRTIII.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cibertec.EFSRTIII.entity.Postulante;


@Repository
public interface PostulanteRepository extends JpaRepository<Postulante, String>{
	
		@Query(value="SELECT * FROM datosPostulante dp ORDER BY CAST(SUBSTRING(idPostulante, 4) AS UNSIGNED)", nativeQuery = true)
		List<Postulante> listarPostulante();
		
		@Query(value = "SELECT idPostulante FROM datosPostulante ORDER BY CAST(SUBSTRING(idPostulante, 4) AS UNSIGNED) DESC LIMIT 1", nativeQuery = true)
	    String obtenerUltimoId();
		
		@Query(value = "SELECT * FROM datosPostulante P WHERE P.numeroDocumento = :nDni", nativeQuery = true)
		Postulante buscarPostulanteByDni(@Param("nDni") String nDni);
		
		@Query(value = "SELECT * FROM datosPostulante P WHERE P.apellidos = :Apellidos", nativeQuery = true)
		Postulante buscarPostulanteByApellido(@Param("Apellidos") String Apellidos);

		
		//Query para buscar por ID Opcional(Report PDF Postulante)xd
		
	    @Query("SELECT p FROM Postulante p WHERE :filtro IS NULL OR p.idPostulante LIKE %:filtro%")
	    List<Postulante> buscarPorIdPostulante(@Param("filtro") String filtro);
}