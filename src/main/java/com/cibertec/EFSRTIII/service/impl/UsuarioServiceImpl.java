package com.cibertec.EFSRTIII.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cibertec.EFSRTIII.entity.Usuario;
import com.cibertec.EFSRTIII.repository.UsuarioRepository;
import com.cibertec.EFSRTIII.service.UsuarioService;


@Service
public class UsuarioServiceImpl implements UserDetailsService, UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Este método es invocado automáticamente por Spring Security
     * durante el proceso de login. Su objetivo es cargar al usuario
     * desde la base de datos según su correo, y devolver un objeto
     * UserDetails con sus credenciales y roles.
     */
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + correo));

        // ⚠️ IMPORTANTE: Spring Security agrega automáticamente "ROLE_" 
        // Si tu BD tiene "RECEPCIONISTA", se convierte en "ROLE_RECEPCIONISTA"
        return User.builder()
        	    .username(usuario.getCorreo())
        	    .password(usuario.getContrasena())
        	    .authorities(usuario.getRol().getRolUsuario()) // "RECEPCIONISTA"
        	    .build();
    }

    /**
     * Método personalizado para obtener el objeto Usuario completo
     * desde el correo electrónico. Se usa para mostrar el nombre
     * y apellido en las vistas después del login.
     */
    
    @Override
    public Usuario buscarByUsuario(String correo) {
        return usuarioRepository.findByCorreo(correo).orElse(null);
    }
}
