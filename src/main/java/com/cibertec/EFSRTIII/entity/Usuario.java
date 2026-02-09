package com.cibertec.EFSRTIII.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name="usuario")
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "idUsuario")
	    private Integer id;

	    @Column(length = 50, nullable = false)
	    private String nombres;

	    @Column(length = 50, nullable = false)
	    private String apellidos;

	    @Column(length = 100, nullable = false, unique = true)
	    private String correo;

	    @Column(length = 100, nullable = false)
	    private String contrasena;

	    @Column(length = 15)
	    private String telefono;

	    @Column(length = 150)
	    private String direccion;

	    @Column(nullable = false, columnDefinition = "ENUM('activo', 'inactivo')")
	    private String estado;

	    @CreationTimestamp
	    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	    @Column(name = "fechaRegistro", nullable = false, updatable = false)
	    private LocalDateTime fechaRegistro;

	    @ManyToOne
	    @JoinColumn(name = "idRolUsuario", nullable = false)
	    private RolUsuario rol;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getNombres() {
			return nombres;
		}

		public void setNombres(String nombres) {
			this.nombres = nombres;
		}

		public String getApellidos() {
			return apellidos;
		}

		public void setApellidos(String apellidos) {
			this.apellidos = apellidos;
		}

		public String getCorreo() {
			return correo;
		}

		public void setCorreo(String correo) {
			this.correo = correo;
		}

		public String getContrasena() {
			return contrasena;
		}

		public void setContrasena(String contrasena) {
			this.contrasena = contrasena;
		}

		public String getTelefono() {
			return telefono;
		}

		public void setTelefono(String telefono) {
			this.telefono = telefono;
		}

		public String getDireccion() {
			return direccion;
		}

		public void setDireccion(String direccion) {
			this.direccion = direccion;
		}

		public String getEstado() {
			return estado;
		}

		public void setEstado(String estado) {
			this.estado = estado;
		}

		public LocalDateTime getFechaRegistro() {
			return fechaRegistro;
		}

		public void setFechaRegistro(LocalDateTime fechaRegistro) {
			this.fechaRegistro = fechaRegistro;
		}

		public RolUsuario getRol() {
			return rol;
		}

		public void setRol(RolUsuario rol) {
			this.rol = rol;
		}
	    
	    
}
