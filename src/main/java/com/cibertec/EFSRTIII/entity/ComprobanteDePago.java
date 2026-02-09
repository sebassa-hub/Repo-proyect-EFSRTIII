package com.cibertec.EFSRTIII.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="CDP")
public class ComprobanteDePago {
	@Id
 	@Column(name = "nroCDP")
    private String idCDP;

    @OneToOne
    @JoinColumn(name = "nroCita")
    private CitaMedica cita;

    private LocalDate fecha;
    private LocalTime hora;
    private Double monto;
    private String estado;
	public String getIdCDP() {
		return idCDP;
	}
	public void setIdCDP(String idCDP) {
		this.idCDP = idCDP;
	}
	public CitaMedica getCita() {
		return cita;
	}
	public void setCita(CitaMedica cita) {
		this.cita = cita;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public LocalTime getHora() {
		return hora;
	}
	public void setHora(LocalTime hora) {
		this.hora = hora;
	}
	public Double getMonto() {
		return monto;
	}
	public void setMonto(Double monto) {
		this.monto = monto;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
    
    

}
