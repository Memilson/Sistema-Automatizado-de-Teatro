package com.mycompany.mavenproject3.usuario.factory;

import com.mycompany.mavenproject3.usuario.controller.UsuarioController;
import com.mycompany.mavenproject3.usuario.repository.UsuarioRepository;
import com.mycompany.mavenproject3.usuario.repository.UsuarioRepositorySupabase;
import com.mycompany.mavenproject3.usuario.service.UsuarioService;

public class UsuarioControllerFactory {

    public static UsuarioController criar() {
        UsuarioRepository repository = new UsuarioRepositorySupabase();
        UsuarioService service = new UsuarioService(repository);
        return new UsuarioController(service);
    }
}
