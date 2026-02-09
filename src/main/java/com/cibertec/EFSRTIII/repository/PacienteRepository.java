package com.cibertec.EFSRTIII.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cibertec.EFSRTIII.entity.Paciente;



@Repository
public interface PacienteRepository extends JpaRepository<Paciente, String>{
	
	@Query(value = "SELECT * FROM paciente P WHERE P.numeroDocumento = :nDni", nativeQuery = true)
	Paciente buscarPacienteByDni(@Param("nDni") String nDni);
	
	@Query(value = "SELECT * FROM paciente P WHERE P.apellidos = :Apellidos", nativeQuery = true)
	Paciente buscarPacienteByApellido(@Param("Apellidos") String Apellidos);
	
	@Query(value = "SELECT idPaciente FROM paciente ORDER BY CAST(SUBSTRING(idPaciente, 4) AS UNSIGNED) DESC LIMIT 1", nativeQuery = true)
	String obtenerUltimoId();

	
	@Query(value = "SELECT * FROM paciente ORDER BY CAST(SUBSTRING(idPaciente, 4) AS UNSIGNED)", nativeQuery = true)
	List<Paciente> listarPacientesOrdenados();
}
