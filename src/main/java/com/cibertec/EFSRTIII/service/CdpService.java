package com.cibertec.EFSRTIII.service;

import com.cibertec.EFSRTIII.dto.ComprobantePagoDTO;

public interface CdpService {

	
    ComprobantePagoDTO obtenerDatosComprobante(String nroCita);
    void registrarComprobante(String nroCita);
	
	
}
