package com.cibertec.EFSRTIII.controller;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import jakarta.servlet.http.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cibertec.EFSRTIII.entity.CitaMedica;
import com.cibertec.EFSRTIII.entity.CitaMedica.EstadoCita;
import com.cibertec.EFSRTIII.entity.DiaSemana;
import com.cibertec.EFSRTIII.entity.HorarioMedico;
import com.cibertec.EFSRTIII.entity.Medico;
import com.cibertec.EFSRTIII.service.CitaMedicaService;
import com.cibertec.EFSRTIII.service.EspecialidadService;
import com.cibertec.EFSRTIII.service.HorarioMedicoService;
import com.cibertec.EFSRTIII.service.MedicoService;
import com.cibertec.EFSRTIII.service.PacienteService;
import com.cibertec.EFSRTIII.service.impl.CitaPdfService;

@Controller
@RequestMapping("/cita")
public class CitaMedicaController {

    @Autowired
    private CitaMedicaService citaService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private HorarioMedicoService horarioMedicoService;

    @Autowired
    private EspecialidadService especialidadService;
    
    @Autowired
    private CitaPdfService pdfService;

    // Mostrar formulario de registro de cita
    @GetMapping("/registrar")
    public String mostrarFormulario(
            @RequestParam(name = "fPaciente", required = false) String fPaciente,
            @RequestParam(name = "fEstado", required = false) EstadoCita fEstado,
            Model model,
            HttpServletRequest request) {
     
        List<CitaMedica> lista;
        
        if (fPaciente != null && !fPaciente.isEmpty()) {
            lista = citaService.buscarPorPaciente(fPaciente);
        } else if (fEstado != null) {
            lista = citaService.buscarPorEstado(fEstado);
        } else {
            lista = citaService.listarCitasOrdenadas();
        }

        model.addAttribute("listaPacientes", pacienteService.listarPacientes());
        model.addAttribute("listaEspecialidades", especialidadService.listarEspecialidad());
        model.addAttribute("listaCitas", lista);
        
        model.addAttribute("estados", EstadoCita.values());
        
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return "cita/registrarCitaMedica :: #contenedorTabla";
        }
        
        return "cita/registrarCitaMedica";
    }

    @GetMapping("/horarios-disponibles")
    @ResponseBody
    public List<Map<String, Object>> horariosDisponibles(
            @RequestParam String idMedico,
            @RequestParam Date fechaCita
    ) {
        Map<DayOfWeek, DiaSemana> mapaDias = Map.of(
                DayOfWeek.MONDAY, DiaSemana.LUNES,
                DayOfWeek.TUESDAY, DiaSemana.MARTES,
                DayOfWeek.WEDNESDAY, DiaSemana.MIERCOLES,
                DayOfWeek.THURSDAY, DiaSemana.JUEVES,
                DayOfWeek.FRIDAY, DiaSemana.VIERNES,
                DayOfWeek.SATURDAY, DiaSemana.SABADO,
                DayOfWeek.SUNDAY, DiaSemana.DOMINGO
        );

        DayOfWeek dayOfWeek = fechaCita.toLocalDate().getDayOfWeek();
        DiaSemana diaEnum = mapaDias.get(dayOfWeek);

        HorarioMedico horario = horarioMedicoService.buscarHorarioPorMedicoYDia(idMedico, diaEnum);
        if (horario == null) return List.of();

        List<LocalTime> bloques = generarBloques30Min(
        	    horario.getHoraInicio(),
        	    horario.getHoraFin()
        	);


        List<LocalTime> horasOcupadas = citaService.listarPorMedicoYFecha(idMedico, fechaCita)
                .stream()
                .map(CitaMedica::getHoraCita)
                .collect(Collectors.toList());


        return bloques.stream()
        	    .map(hora -> {
        	        Map<String, Object> map = new HashMap<>();
        	        map.put("hora", hora.toString());
        	        map.put("ocupado", horasOcupadas.contains(hora));
        	        return map;
        	    })
        	    .collect(Collectors.toList());
    }


    // Para consultar médicos por especialidad
    @GetMapping("/medicos-por-especialidad")
    @ResponseBody
    public List<Medico> listarMedicosPorEspecialidad(@RequestParam String idEspecialidad) {
        return medicoService.buscarMedicosPorEspecialidad(idEspecialidad);
    }

    // Registrar la cita
    @PostMapping("/registrar")
    public String registrarCita(
            @RequestParam String idPaciente,
            @RequestParam String idMedico,
            @RequestParam Date fechaCita,
            @RequestParam LocalTime horaCita,
            Model model
    ) {
        if (citaService.existeCita(idMedico, fechaCita, horaCita)) {
            model.addAttribute("error", "Ese horario ya está ocupado.");
            return "cita/registrarCitaMedica"; // vista en caso de error
        }

        CitaMedica cita = new CitaMedica();
       
        cita.setFechaCita(fechaCita);
        cita.setHoraCita(horaCita);
        cita.setEstado(EstadoCita.pendiente_pago);
        cita.setPaciente(pacienteService.buscarPacienteById(idPaciente));
        cita.setMedico(medicoService.buscarMedicoById(idMedico));

        citaService.registrar(cita);
        return "redirect:/cita/registrar?success";
    }

    // ✅ Corregido: Auxiliar para generar bloques de 30 minutos
    private List<LocalTime> generarBloques30Min(LocalTime inicio, LocalTime fin) {

        List<LocalTime> bloques = new ArrayList<>();

        LocalTime actual = inicio;

        while (actual.isBefore(fin)) {
            bloques.add(actual);
            actual = actual.plusMinutes(30);
        }

        return bloques;
    }
    
 // 1. Cambiar estado de la cita
    @PostMapping("/cambiar-estado")
    public String cambiarEstadoCita(
            @RequestParam String nroCita, 
            @RequestParam EstadoCita nuevoEstado) {
        
        citaService.actualizarEstadoCita(nroCita, nuevoEstado);
        return "redirect:/cita/registrar?estadoActualizado";
    }

    @GetMapping("/generar-pdf/{nroCita}")
    public void descargarTicket(@PathVariable String nroCita, jakarta.servlet.http.HttpServletResponse response) throws Exception {
        CitaMedica cita = citaService.obtenerPorId(nroCita);
        
        if (cita != null) {
        	byte[] pdfBytes = pdfService.generarTicketCita(cita);
            
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=Ticket_" + nroCita + ".pdf");
            response.getOutputStream().write(pdfBytes);
            response.getOutputStream().flush();
        }
    }


}
