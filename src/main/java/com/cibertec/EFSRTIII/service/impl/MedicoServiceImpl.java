package com.cibertec.EFSRTIII.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.EFSRTIII.entity.Medico;
import com.cibertec.EFSRTIII.repository.MedicoRepository;
import com.cibertec.EFSRTIII.service.MedicoService;


@Service
public class MedicoServiceImpl implements MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    public Medico guardarMedico(Medico medico) {
        String nuevoId = generarCodigoMedico();
        medico.setIdMedico(nuevoId);
        medico.setEstado("activo");
        medico.setEdad(calcularEdad(medico.getFechaNacimiento()));
        medico.setFechaRegistro(LocalDateTime.now());
        return medicoRepository.save(medico);
    }

    @Override
    public Medico actualizarMedico(Medico medico) {
    	 medico.setEdad(calcularEdad(medico.getFechaNacimiento()));
        return medicoRepository.save(medico);
    }

    @Override
    public Medico buscarMedicoByDni(String nDni) {
        return medicoRepository.buscarMedicoByDni(nDni);
    }

    @Override
    public Medico buscarMedicoByApellido(String apellido) {
        return medicoRepository.buscarMedicoByApellido(apellido);
    }

    @Override
    public Medico buscarMedicoById(String idMedico) {
        return medicoRepository.findById(idMedico).orElse(null);
    }

    @Override
    public void eliminarMedicoById(String idMedico) {
        medicoRepository.deleteById(idMedico);
    }

    @Override
    public List<Medico> listarMedicos() {
        return medicoRepository.listarMedicosOrdenados();
    }
    
    @Override
    public List<Medico> buscarMedicosPorEspecialidad(String idEspecialidad) {
        return medicoRepository.findByEspecialidad(idEspecialidad);
    }

    // 🔧 Método para generar código automático
    private String generarCodigoMedico() {
        String ultimoId = medicoRepository.obtenerUltimoId(); // ej: "MED005"
        if (ultimoId == null) {
            return "MED001";
        }
        int num = Integer.parseInt(ultimoId.substring(3)) + 1;
        return String.format("MED%03d", num); // Resultado: "MED006"
    }

    // 🔧 Método para calcular edad a partir de fechaNacimiento
    private int calcularEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) return 0;
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    
    @Override
	public void cambiarEstadoMedico(String idMedico) {
	    Medico medico = medicoRepository.findById(idMedico).orElse(null);
	    if (medico != null) {
	        String nuevoEstado = medico.getEstado().equals("activo") ? "inactivo" : "activo";
	        medico.setEstado(nuevoEstado);
	        medicoRepository.save(medico);
	    }
	}
}
