package com.cibertec.EFSRTIII.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ComprobantePagoDTO {

	private String nroCita;
	private String estado;
	
	
	//DATOS DEL PACIENTE
	private String nombrePaciente;
	private String generoPaciente;
    private int edadPaciente;
    private String celularPaciente;
    private String correoPaciente;

    // Datos del médico
    private String nombreMedico;
    private String especialidad;
    private Double monto;

    // Cita
    private LocalDate fechaCita;
    private LocalTime horaCita;
	public String getNroCita() {
		return nroCita;
	}
	public void setNroCita(String nroCita) {
		this.nroCita = nroCita;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getNombrePaciente() {
		return nombrePaciente;
	}
	public void setNombrePaciente(String nombrePaciente) {
		this.nombrePaciente = nombrePaciente;
	}
	public String getGeneroPaciente() {
		return generoPaciente;
	}
	public void setGeneroPaciente(String generoPaciente) {
		this.generoPaciente = generoPaciente;
	}
	public int getEdadPaciente() {
		return edadPaciente;
	}
	public void setEdadPaciente(int edadPaciente) {
		this.edadPaciente = edadPaciente;
	}
	public String getCelularPaciente() {
		return celularPaciente;
	}
	public void setCelularPaciente(String celularPaciente) {
		this.celularPaciente = celularPaciente;
	}
	public String getCorreoPaciente() {
		return correoPaciente;
	}
	public void setCorreoPaciente(String correoPaciente) {
		this.correoPaciente = correoPaciente;
	}
	public String getNombreMedico() {
		return nombreMedico;
	}
	public void setNombreMedico(String nombreMedico) {
		this.nombreMedico = nombreMedico;
	}
	public String getEspecialidad() {
		return especialidad;
	}
	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}
	public Double getMonto() {
		return monto;
	}
	public void setMonto(Double monto) {
		this.monto = monto;
	}
	public LocalDate getFechaCita() {
		return fechaCita;
	}
	public void setFechaCita(LocalDate fechaCita) {
		this.fechaCita = fechaCita;
	}
	public LocalTime getHoraCita() {
		return horaCita;
	}
	public void setHoraCita(LocalTime horaCita) {
		this.horaCita = horaCita;
	}
	
    
	
}
