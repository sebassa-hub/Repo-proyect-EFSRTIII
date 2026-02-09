package com.cibertec.EFSRTIII.repository;

import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cibertec.EFSRTIII.entity.DiaSemana;
import com.cibertec.EFSRTIII.entity.HorarioMedico;

@Repository
public interface HorarioMedicoRepository extends JpaRepository<HorarioMedico, Integer> {

	@Query("SELECT h FROM HorarioMedico h WHERE h.idMedico.idMedico = :idMedico AND h.diaSemana = :diaSemana")
    HorarioMedico buscarHorarioPorMedicoYDia(
        @Param("idMedico") String idMedico, 
        @Param("diaSemana") DiaSemana diaSemana
    );
	
	@Query("SELECT h FROM HorarioMedico h " +
		       "WHERE h.idMedico.idMedico = :idMedico " +
		       "AND h.diaSemana = :diaSemana " +
		       "AND ( " +
		       "     (h.horaInicio < :horaFin AND h.horaFin > :horaInicio) " +
		       ")")
		List<HorarioMedico> buscarHorariosSolapados(
		        @Param("idMedico") String idMedico,
		        @Param("diaSemana") DiaSemana diaSemana,
		        @Param("horaInicio") Time horaInicio,
		        @Param("horaFin") Time horaFin
		);
}
