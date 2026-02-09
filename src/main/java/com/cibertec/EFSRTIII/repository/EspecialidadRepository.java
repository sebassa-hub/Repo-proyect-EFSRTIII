package com.cibertec.EFSRTIII.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cibertec.EFSRTIII.entity.Especialidad;


@Repository
public interface EspecialidadRepository extends JpaRepository<Especialidad, String> {

    // Lista todas las especialidades
    @Query(value = "SELECT * FROM especialidad e", nativeQuery = true)
    List<Especialidad> listarEspecialidad();

    // Obtiene el último ID generado (para autoincremento tipo ESP001)
    @Query(value = "SELECT idEspecialidad FROM especialidad ORDER BY CAST(SUBSTRING(idEspecialidad, 4) AS UNSIGNED) DESC LIMIT 1", nativeQuery = true)
    String obtenerUltimoId();

    // Busca especialidad por nombre
    @Query(value = "SELECT * FROM especialidad e WHERE e.especialidad = :especialidad", nativeQuery = true)
    Especialidad buscarByEspecialidad(@Param("especialidad") String especialidad);

    // Busca especialidad por ID
    @Query(value = "SELECT * FROM especialidad e WHERE e.idEspecialidad = :idEspecialidad", nativeQuery = true)
    Especialidad buscarByEspecialidadId(@Param("idEspecialidad") String idEspecialidad);
    
    
}
