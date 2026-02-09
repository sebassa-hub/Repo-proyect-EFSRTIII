package com.cibertec.EFSRTIII.service;

import java.util.List;

import com.cibertec.EFSRTIII.entity.Medico;


public interface MedicoService {

    public Medico guardarMedico(Medico medico);
    public Medico actualizarMedico(Medico medico);
    public Medico buscarMedicoByDni(String nDni);
    public Medico buscarMedicoByApellido(String apellido);
    public Medico buscarMedicoById(String idMedico);
    public void eliminarMedicoById(String idMedico);
    public List<Medico> listarMedicos();
    public void cambiarEstadoMedico(String idMedico);
    List<Medico> buscarMedicosPorEspecialidad(String idEspecialidad);
}
