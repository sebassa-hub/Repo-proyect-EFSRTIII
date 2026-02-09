package com.cibertec.EFSRTIII.service;

import java.util.List;

import com.cibertec.EFSRTIII.entity.Postulante;



public interface PostulanteService {
	public Postulante guardarPostulante(Postulante userEntity);
	public Postulante actualizarPostulante(Postulante userEntity);
	public Postulante buscarPostulanteByDni(String nDni);
	public Postulante buscarPostulanteByApellidos(String Apellidos);
	public Postulante buscarPostulanteById(String idPost);
	public void eliminarPostulanteById(String idPost);
	public List<Postulante> listarPostulante();
	
	//Metodo para buscar por Id opcional(Report PDF Postulante)xd
	List<Postulante> listarPorFiltro(String filtro);
}
