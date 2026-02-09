package com.cibertec.EFSRTIII.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        // Obtener el rol del usuario autenticado
        String rol = authentication.getAuthorities().iterator().next().getAuthority();
        
        System.out.println("🔍 Rol detectado: " + rol); // Para debug

        // Redirigir según el rol (Spring Security agrega ROLE_ automáticamente)
        if (rol.equals("ROLE_RECEPCIONISTA") || rol.equals("RECEPCIONISTA")) {
            response.sendRedirect("/dashboard/recepcionista");
        } else if (rol.equals("ROLE_CAJERO") || rol.equals("CAJERO")) {
            response.sendRedirect("/dashboard/cajero");
        } else if (rol.equals("ROLE_SECRETARIA") || rol.equals("SECRETARIA")) {
            response.sendRedirect("/dashboard/secretaria");
        } else if (rol.equals("ROLE_RRHH") || rol.equals("RRHH")) {
            response.sendRedirect("/dashboard/rrhh");
        } else {
            System.out.println("⚠️ Rol no reconocido: " + rol);
            response.sendRedirect("/login?error");
        }
    }
}