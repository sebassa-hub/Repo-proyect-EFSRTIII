package com.cibertec.EFSRTIII.repository;

import java.sql.Date;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cibertec.EFSRTIII.entity.CitaMedica;


public interface CitaMedicaRepository extends JpaRepository<CitaMedica, String> {

    // Buscar todas las citas por médico y fecha
    List<CitaMedica> findByMedico_IdMedicoAndFechaCita(String idMedico, Date fechaCita);

    // Verificar si ya hay una cita en esa fecha/hora con ese médico
    boolean existsByMedico_IdMedicoAndFechaCitaAndHoraCita(String idMedico, Date fechaCita, LocalTime horaCita);
    
    @Query(value = "SELECT nro_cita FROM cita_medica ORDER BY CAST(SUBSTRING(nro_cita, 4) AS UNSIGNED) DESC LIMIT 1", nativeQuery = true)
    String obtenerUltimoNroCita();
    
    
//SECCION DE REPORTES CAJERO - DIFERENTES CONSULTAS
	
  	//PARA LISTAR TODAS LAS CITAS MEDICAS ORDENADAS POR ORDEN
  	
    @Query(value = "SELECT * FROM cita_medica ORDER BY CAST(SUBSTRING(nro_cita, 4) AS UNSIGNED)", nativeQuery = true)
    List<CitaMedica> listarCitasOrdenadas();
  
  	
  	//BUSCAR TODAS LAS CITAS DE UN MEDICO EN ESPECIFICO
    @Query(value = "SELECT * FROM cita_medica WHERE id_medico = :idMedico", nativeQuery = true)
    List<CitaMedica> buscarCitasPorMedico(@Param("idMedico") String idMedico);
  	
  	
  	//BUSCAR TODAS LAS CITAS QUE TIENE UN PACIENTE
    @Query(value = "SELECT * FROM cita_medica WHERE id_paciente = :idPaciente", nativeQuery = true)
    List<CitaMedica> buscarCitasPorPaciente(@Param("idPaciente") String idPaciente);
  	
  	//BUSCAR CITAS POR ESTADO
    @Query("SELECT c FROM CitaMedica c WHERE c.estado = :estado")
    List<CitaMedica> buscarCitasPorEstado(@Param("estado") CitaMedica.EstadoCita estado);
}