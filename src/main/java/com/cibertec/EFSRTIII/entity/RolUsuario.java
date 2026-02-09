package com.cibertec.EFSRTIII.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name="rolusuario")
@NoArgsConstructor
@AllArgsConstructor
public class RolUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRolUsuario")
    private Integer idRolUsuario;

    @Column(name = "rolUsuario", nullable = false, length = 50)
    private String rolUsuario;

	public Integer getIdRolUsuario() {
		return idRolUsuario;
	}

	public void setIdRolUsuario(Integer idRolUsuario) {
		this.idRolUsuario = idRolUsuario;
	}

	public String getRolUsuario() {
		return rolUsuario;
	}

	public void setRolUsuario(String rolUsuario) {
		this.rolUsuario = rolUsuario;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    
}
