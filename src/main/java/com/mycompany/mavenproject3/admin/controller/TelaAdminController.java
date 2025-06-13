package com.mycompany.mavenproject3.admin.controller;

import com.mycompany.mavenproject3.usuario.model.Usuario;

public class TelaAdminController {

    public static boolean validarAdmin(Usuario usuario) {
        return usuario != null && usuario.isAdmin();
    }
}
