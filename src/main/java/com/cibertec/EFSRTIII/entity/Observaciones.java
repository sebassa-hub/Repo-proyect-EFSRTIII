package com.cibertec.EFSRTIII.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name="observaciones")
public class Observaciones implements Serializable{
private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idObs")
	private String idObs;
	
	@Column(name="observacion")
	private String observacion;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime fechaRegistro;
	
	@ManyToOne
	@JoinColumn(name="idPostulante")
	private Postulante idPostulante;

	public String getIdObs() {
		return idObs;
	}

	public void setIdObs(String idObs) {
		this.idObs = idObs;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Postulante getIdPostulante() {
		return idPostulante;
	}

	public void setIdPostulante(Postulante idPostulante) {
		this.idPostulante = idPostulante;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
