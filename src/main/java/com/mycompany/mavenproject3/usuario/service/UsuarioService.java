package com.mycompany.mavenproject3.usuario.service;


import com.mycompany.mavenproject3.usuario.model.Usuario;
import com.mycompany.mavenproject3.usuario.repository.UsuarioRepository;

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario buscarUsuarioPorId(String id) {
        return usuarioRepository.buscarPorId(id);
    }

    public boolean atualizarAssinatura(String userId, String novaAssinaturaId) {
        return usuarioRepository.alterarAssinatura(userId, novaAssinaturaId);
    }

    public boolean promoverUsuarioParaAdmin(String userId) {
        return usuarioRepository.promoverParaAdmin(userId);
    }

    public int contarUsuarios() {
        return usuarioRepository.totalUsuariosCadastrados();
    }
}
