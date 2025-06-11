package com.mycompany.mavenproject3.usuario.factory;

import com.mycompany.mavenproject3.usuario.controller.UsuarioController;
import com.mycompany.mavenproject3.usuario.repository.UsuarioRepository;
import com.mycompany.mavenproject3.usuario.repository.UsuarioRepositorySupabase;
import com.mycompany.mavenproject3.usuario.service.UsuarioService;
import com.mycompany.mavenproject3.supabase.SupabaseService;

public class UsuarioControllerFactory {

    public static UsuarioController criar() {
        SupabaseService supabase = new SupabaseService(); // ou use Singleton
        UsuarioRepository repository = new UsuarioRepositorySupabase(supabase);
        UsuarioService service = new UsuarioService(repository);
        return new UsuarioController(service, supabase);
    }
}
