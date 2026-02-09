package com.cibertec.EFSRTIII.service;

import java.util.List;

import com.cibertec.EFSRTIII.entity.Especialidad;


public interface EspecialidadService {

    Especialidad guardarEspecialidad(Especialidad esp);
    Especialidad actualizarEspecialidad(Especialidad esp);
    void eliminarEspecialidad(String idEsp);

    List<Especialidad> listarEspecialidad();

    Especialidad buscarByEspecialidadId(String idEspecialidad);
    Especialidad buscarByEspecialidad(String especialidad); // ← Para validar si ya existe
}
