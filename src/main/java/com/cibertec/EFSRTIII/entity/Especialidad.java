package com.cibertec.EFSRTIII.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="especialidad")
public class Especialidad implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idespecialidad")
	private String idEspecialidad;

	@Column(name = "especialidad")
	private String especialidad;

	@Column(name = "montoporespecialidad")
	private Double montoPorEspecialidad;

	public String getIdEspecialidad() {
		return idEspecialidad;
	}

	public void setIdEspecialidad(String idEspecialidad) {
		this.idEspecialidad = idEspecialidad;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public Double getMontoPorEspecialidad() {
		return montoPorEspecialidad;
	}

	public void setMontoPorEspecialidad(Double montoPorEspecialidad) {
		this.montoPorEspecialidad = montoPorEspecialidad;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
