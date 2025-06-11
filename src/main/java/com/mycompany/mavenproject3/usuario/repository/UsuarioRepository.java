package com.mycompany.mavenproject3.usuario.repository;

import com.mycompany.mavenproject3.usuario.model.Usuario;
import java.util.List;

public interface UsuarioRepository {
    Usuario buscarPorId(String idAuth);
    List<Usuario> buscarTodos();
    boolean promoverParaAdmin(String id);
    boolean alterarAssinatura(String id, String novaAssinaturaId);
    boolean alterarPlanoAssinatura(String usuarioId, String novaAssinaturaId);
    int totalUsuariosCadastrados();
}
