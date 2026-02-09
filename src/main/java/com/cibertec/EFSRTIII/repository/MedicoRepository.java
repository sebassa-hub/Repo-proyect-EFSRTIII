package com.cibertec.EFSRTIII.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cibertec.EFSRTIII.entity.Medico;


@Repository
public interface MedicoRepository extends JpaRepository<Medico, String> {

    @Query(value = "SELECT * FROM medico M WHERE M.numeroDocumento = :nDni", nativeQuery = true)
    Medico buscarMedicoByDni(@Param("nDni") String nDni);

    @Query(value = "SELECT * FROM medico M WHERE M.apellidos = :Apellidos", nativeQuery = true)
    Medico buscarMedicoByApellido(@Param("Apellidos") String Apellidos);

    @Query(value = "SELECT idMedico FROM medico ORDER BY CAST(SUBSTRING(idMedico, 4) AS UNSIGNED) DESC LIMIT 1", nativeQuery = true)
    String obtenerUltimoId();

    @Query(value = "SELECT * FROM medico ORDER BY CAST(SUBSTRING(idMedico, 4) AS UNSIGNED)", nativeQuery = true)
    List<Medico> listarMedicosOrdenados();
    
    @Query("SELECT m FROM Medico m WHERE m.especialidad.idEspecialidad = :idEspecialidad AND m.estado = 'activo'")
    List<Medico> findByEspecialidad(@Param("idEspecialidad") String idEspecialidad);
}
