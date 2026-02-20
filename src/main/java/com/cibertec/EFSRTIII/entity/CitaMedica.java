package com.cibertec.EFSRTIII.entity;

import java.io.Serializable;
import java.util.Date;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cita_medica")
public class CitaMedica implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
    private String nroCita;

    @ManyToOne
    @JoinColumn(name = "idPaciente", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "idMedico", nullable = false)
    private Medico medico;

    @Column(name = "fechaCita", nullable = false)
    private Date fechaCita;

    @Column(name = "horaCita", nullable = false)
    private LocalTime horaCita;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoCita estado;

    public enum EstadoCita {
        pendiente_pago, pagado, cancelado
    }

	public String getNroCita() {
		return nroCita;
	}

	public void setNroCita(String nroCita) {
		this.nroCita = nroCita;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Date getFechaCita() {
		return fechaCita;
	}

	public void setFechaCita(Date fechaCita) {
		this.fechaCita = fechaCita;
	}

	public LocalTime getHoraCita() {
		return horaCita;
	}

	public void setHoraCita(LocalTime horaCita) {
		this.horaCita = horaCita;
	}

	public EstadoCita getEstado() {
		return estado;
	}

	public void setEstado(EstadoCita estado) {
		this.estado = estado;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
