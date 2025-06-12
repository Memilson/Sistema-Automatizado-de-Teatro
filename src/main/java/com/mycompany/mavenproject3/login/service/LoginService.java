package com.mycompany.mavenproject3.login.service;

import com.mycompany.mavenproject3.supabase.SupabaseService;
import com.mycompany.mavenproject3.usuario.model.Usuario;
import com.mycompany.mavenproject3.usuario.repository.UsuarioRepository;
import com.mycompany.mavenproject3.usuario.repository.UsuarioRepositorySupabase;

public class LoginService {

    private final SupabaseService supabaseService = new SupabaseService();
    private final UsuarioRepository usuarioRepository = new UsuarioRepositorySupabase(supabaseService);


    public Usuario autenticar(String email, String senha) {
        if (email == null || senha == null || email.isEmpty() || senha.isEmpty()) {
            throw new IllegalArgumentException("Email e senha devem ser preenchidos.");
        }

        Usuario usuario = usuarioRepository.findByEmailAndSenha(email, senha);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário ou senha inválidos.");
        }

        return usuario;
    }

    public boolean usuarioTemDadosComplementares(String idAuth) {
        return usuarioRepository.temDadosComplementares(idAuth);
    }
}
