package com.cibertec.EFSRTIII.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

import com.cibertec.EFSRTIII.entity.CitaMedica;


public interface CitaMedicaService {

    List<CitaMedica> listarPorMedicoYFecha(String idMedico, Date fechaCita);
    boolean existeCita(String idMedico, Date fechaCita, LocalTime horaCita);
    void registrar(CitaMedica cita);
    List<CitaMedica> listarTodas();
	List<CitaMedica> listarCitasOrdenadas();
	List<CitaMedica> buscarPorMedico(String idMedico);
	List<CitaMedica> buscarPorPaciente(String idPaciente);
	List<CitaMedica> buscarPorEstado(CitaMedica.EstadoCita estado);
	void eliminarCita(String nroCita);
	String obtenerUltimoNroCita();
	CitaMedica obtenerPorId(String nroCita);
    void actualizarEstadoCita(String nroCita, CitaMedica.EstadoCita nuevoEstado);
    
}
