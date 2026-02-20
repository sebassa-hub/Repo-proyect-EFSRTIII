package com.cibertec.EFSRTIII.service.impl;

import com.cibertec.EFSRTIII.entity.CitaMedica;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class CitaPdfService {

    public byte[] generarTicketCita(CitaMedica cita) throws DocumentException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        // Definimos un tamaño de página pequeño (tipo ticket térmico) o A4 estándar
        Document document = new Document(PageSize.A4, 36, 36, 36, 36);
        PdfWriter.getInstance(document, out);

        document.open();

        // Fuentes
        Font fontBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 10);
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);

        // --- ENCABEZADO (Estilo EsSalud) ---
        Paragraph header = new Paragraph("Sistema Médico", fontTitle);
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);
        
        Paragraph subHeader = new Paragraph("TICKET DE ATENCIÓN", fontBold);
        subHeader.setAlignment(Element.ALIGN_CENTER);
        document.add(subHeader);
        document.add(new Paragraph(" "));

        // --- TABLA DE DATOS PRINCIPALES ---
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{40, 60});

        agregarCelda(table, "Fecha de la Cita:", fontBold);
        agregarCelda(table, cita.getFechaCita().toString(), fontNormal);

        agregarCelda(table, "Hora de la Cita:", fontBold);
        agregarCelda(table, cita.getHoraCita().toString(), fontNormal);

        agregarCelda(table, "Médico:", fontBold);
        agregarCelda(table, cita.getMedico().getNombres().toUpperCase() + " " + cita.getMedico().getApellidos().toUpperCase(), fontNormal);

        agregarCelda(table, "Especialidad:", fontBold);
        agregarCelda(table, cita.getMedico().getEspecialidad().getEspecialidad(), fontNormal);

        agregarCelda(table, "Paciente:", fontBold);
        agregarCelda(table, cita.getPaciente().getNombres().toUpperCase() + " " + cita.getPaciente().getApellidos().toUpperCase(), fontNormal);

        agregarCelda(table, "Nro. Acto Médico:", fontBold);
        agregarCelda(table, cita.getNroCita(), fontNormal);

        agregarCelda(table, "Estado:", fontBold);
        agregarCelda(table, cita.getEstado().name().toUpperCase(), fontNormal);

        document.add(table);

        // --- PIE DE PÁGINA (Slogan) ---
        document.add(new Paragraph(" "));
        Paragraph slogan = new Paragraph("\"Hacia una atención de calidad\"", fontNormal);
        slogan.setAlignment(Element.ALIGN_CENTER);
        document.add(slogan);

        document.close();
        return out.toByteArray();
    }

    private void agregarCelda(PdfPTable table, String texto, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setPadding(5);
        cell.setBorder(PdfPCell.BOTTOM); // Solo línea inferior para estilo ticket
        table.addCell(cell);
    }
}