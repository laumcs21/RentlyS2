package com.Rently.Security;

import com.Rently.Persistence.Entity.Usuario;
import com.Rently.Persistence.Entity.Rol;

public class Sesion {
    private static Usuario usuarioActual;
    private static boolean activo = false;

    // Iniciar sesión
    public static void iniciarSesion(Usuario usuario) {
        usuarioActual = usuario;
        activo = true;
    }

    // Cerrar sesión
    public static void cerrarSesion() {
        usuarioActual = null;
        activo = false;
    }

    public static boolean isActivo() {
        return activo;
    }

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static boolean esAdmin() {
        return activo && usuarioActual != null && usuarioActual.getRol() == Rol.ADMINISTRADOR;
    }

    public static boolean esAnfitrion() {
        return activo && usuarioActual != null && usuarioActual.getRol() == Rol.ANFITRION;
    }

    public static boolean esUsuario() {
        return activo && usuarioActual != null && usuarioActual.getRol() == Rol.USUARIO;
    }
}


