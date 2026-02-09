package com.cibertec.EFSRTIII.service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.cibertec.EFSRTIII.entity.CitaMedica;


public interface CitaMedicaService {

    List<CitaMedica> listarPorMedicoYFecha(String idMedico, Date fechaCita);

    boolean existeCita(String idMedico, Date fechaCita, Time horaCita);

    void registrar(CitaMedica cita);
    
    
//METODOS PARA EL CAJERO - NO TOCAR NADA - PROHIBIDOOOOOOO TnT
    
    //METODOS PARA SECCION LISTAR CITAS MEDICAS
    
    List<CitaMedica> listarTodas();
	
	List<CitaMedica> listarCitasOrdenadas();

	List<CitaMedica> buscarPorMedico(String idMedico);
	
	List<CitaMedica> buscarPorPaciente(String idPaciente);
	
	List<CitaMedica> buscarPorEstado(CitaMedica.EstadoCita estado);

	void eliminarCita(String nroCita);
	
	
	String obtenerUltimoNroCita();
	
	CitaMedica guardarCita(CitaMedica cita);
	
	CitaMedica obtenerPorId(String nroCita);
	
	
    //METODOS PARA GENERAR COMPROBANTE DE PAGO Y DETALLE
    
    CitaMedica obtenerCitaConDetalles(String nroCita);
    
    //ACTUALIZAR ESTADO CITA
    void actualizarEstadoCita(String nroCita, CitaMedica.EstadoCita nuevoEstado);


    
    
}
