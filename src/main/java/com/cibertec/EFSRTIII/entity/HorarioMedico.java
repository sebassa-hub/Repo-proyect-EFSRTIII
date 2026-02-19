package com.cibertec.EFSRTIII.entity;

import java.io.Serializable;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="horariomedico")
public class HorarioMedico implements Serializable{
private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idHorario")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idHorario;
	
	@ManyToOne
	@JoinColumn(name="idMedico")
	private Medico idMedico;

	@Enumerated(EnumType.STRING)
	@Column(name="diaSemana")
	private DiaSemana diaSemana;
	
	@DateTimeFormat(pattern = "HH:mm")
	@Column(name="horaInicio")
	private LocalTime horaInicio;
	
	@DateTimeFormat(pattern = "HH:mm")
	@Column(name="horaFin")
	private LocalTime horaFin;

	public HorarioMedico(Medico idMedico, DiaSemana diaSemana, LocalTime horaInicio, LocalTime horaFin) {
		super();
		this.idMedico = idMedico;
		this.diaSemana = diaSemana;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
	}

	public Integer getIdHorario() {
		return idHorario;
	}

	public void setIdHorario(Integer idHorario) {
		this.idHorario = idHorario;
	}

	public Medico getIdMedico() {
		return idMedico;
	}

	public void setIdMedico(Medico idMedico) {
		this.idMedico = idMedico;
	}

	public DiaSemana getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(DiaSemana diaSemana) {
		this.diaSemana = diaSemana;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public LocalTime getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(LocalTime horaFin) {
		this.horaFin = horaFin;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
