package com.cibertec.EFSRTIII.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.EFSRTIII.entity.Especialidad;
import com.cibertec.EFSRTIII.repository.EspecialidadRepository;
import com.cibertec.EFSRTIII.service.EspecialidadService;


@Service
public class EspecialidadServiceImpl implements EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Override
    public List<Especialidad> listarEspecialidad() {
        return especialidadRepository.findAll();
    }

    @Override
    public Especialidad guardarEspecialidad(Especialidad esp) {
        String nuevoId = generarCodigoMedico();
        esp.setIdEspecialidad(nuevoId);
        return especialidadRepository.save(esp);
    }

    @Override
    public Especialidad actualizarEspecialidad(Especialidad esp) {
        return especialidadRepository.save(esp);
    }

    @Override
    public void eliminarEspecialidad(String idEsp) {
        especialidadRepository.deleteById(idEsp);
    }

    @Override
    public Especialidad buscarByEspecialidadId(String especialidadId) {
        return especialidadRepository.findById(especialidadId).orElse(null);
    }

    @Override
    public Especialidad buscarByEspecialidad(String especialidad) {
        return especialidadRepository.buscarByEspecialidad(especialidad);
    }

    private String generarCodigoMedico() {
        String ultimoId = especialidadRepository.obtenerUltimoId(); // ej: "ESP005"
        if (ultimoId == null) {
            return "ESP001";
        }
        int num = Integer.parseInt(ultimoId.substring(3)) + 1;
        return String.format("ESP%03d", num); // Resultado: "ESP006"
    }
    
    public String formatoMoneda(BigDecimal monto) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return "S/ " + df.format(monto);
    }
    
    
}
