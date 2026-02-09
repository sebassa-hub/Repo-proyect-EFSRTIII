package com.cibertec.EFSRTIII.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cibertec.EFSRTIII.entity.Usuario;
import com.cibertec.EFSRTIII.service.UsuarioService;

@Controller
public class MenuController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Vista principal para recepcionista.
     * Se pasa el objeto Usuario para mostrar nombre y apellido.
     */
    @GetMapping("/dashboard/recepcionista")
    public String dashboardRecepcionista(Model model, Authentication authentication) {
        String username = authentication.getName(); // obtiene el correo
        Usuario usuario = usuarioService.buscarByUsuario(username);

        // 👇 DEBUG EN CONSOLA
        if (usuario != null) {
            System.out.println("DEBUG Usuario:");
            System.out.println("Correo: " + usuario.getCorreo());
            System.out.println("Nombre: " + usuario.getNombres());
            System.out.println("Apellido: " + usuario.getApellidos());
        } else {
            System.out.println("Usuario no encontrado para: " + username);
        }

        model.addAttribute("usuario", usuario);
        return "dashboard/recepcionista";
    }

    /**
     * Vista principal para cajero.
     * También pasamos el objeto Usuario para mostrar nombre/apellido si se desea.
     */
    @GetMapping("/dashboard/cajero")
    public String dashboardCajero(Model model, Authentication authentication) {
        String username = authentication.getName();
        Usuario usuario = usuarioService.buscarByUsuario(username);
        model.addAttribute("usuario", usuario);
        return "dashboard/cajero";
    }

    /**
     * Vista principal para secretaria.
     * También pasamos el objeto Usuario para mantener consistencia.
     */
    @GetMapping("/dashboard/secretaria")
    public String dashboardSecretaria(Model model, Authentication authentication) {
        String username = authentication.getName();
        Usuario usuario = usuarioService.buscarByUsuario(username);
        model.addAttribute("usuario", usuario);
        return "dashboard/secretaria";
    }
    
    @GetMapping("/dashboard/rrhh")
    public String dashboardRRHH(Model model, Authentication authentication) {
        String username = authentication.getName(); // Obtener el correo del usuario autenticado
        Usuario usuario = usuarioService.buscarByUsuario(username); // Buscar datos del usuario
        model.addAttribute("usuario", usuario); // Pasar el usuario al modelo
        return "dashboard/rrhh"; // Retornar la vista ubicada en templates/dashboard/rrhh.html
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @GetMapping("/")
    public String redireccionPrincipal() {
        return "redirect:/login";
    }
}
