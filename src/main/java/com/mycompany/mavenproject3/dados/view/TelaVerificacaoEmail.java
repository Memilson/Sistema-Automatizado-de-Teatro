package com.mycompany.mavenproject3.dados.view;

import com.mycompany.mavenproject3.Main;
import com.mycompany.mavenproject3.core.SessaoUsuario;
import com.mycompany.mavenproject3.dados.view.TelaDadosComplementares;
import com.mycompany.mavenproject3.login.controller.LoginController;
import com.mycompany.mavenproject3.supabase.SupabaseService;
import com.mycompany.mavenproject3.usuario.model.Usuario;
import com.mycompany.mavenproject3.usuario.repository.UsuarioRepositorySupabase;
import com.mycompany.mavenproject3.usuario.service.UsuarioService;

import javax.swing.*;
import java.awt.*;

public class TelaVerificacaoEmail extends JFrame {
    private final JLabel statusLabel = new JLabel("", SwingConstants.CENTER);
    private final String email;
    private final String senha;

    public TelaVerificacaoEmail(String email, String senha) {
        this.email = email;
        this.senha = senha;

        setTitle("Verificação de E-mail");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel texto = new JLabel("<html><center>Enviamos um link de confirmação para seu e-mail.<br>Após clicar nele, clique abaixo para continuar.</center></html>", SwingConstants.CENTER);

        JButton verificarButton = new JButton("Já confirmei");
        verificarButton.addActionListener(e -> tentarLogin());

        JPanel center = new JPanel(new BorderLayout());
        center.add(texto, BorderLayout.CENTER);
        center.add(verificarButton, BorderLayout.SOUTH);

        add(statusLabel, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);

        setVisible(true);
    }

    private void tentarLogin() {
        String authId = LoginController.login(email, senha);
        if (authId != null) {
            SessaoUsuario.iniciar(authId);
            var repo = new UsuarioRepositorySupabase(new SupabaseService());
            var usuario = new UsuarioService(repo).buscarUsuarioPorId(authId);

            if (usuario == null || !repo.temDadosComplementares(authId)) {
                new TelaDadosComplementares().setVisible(true);
            } else {
                new Main(usuario);
            }
            dispose();
        } else {
            statusLabel.setText("Conta ainda não confirmada. Tente novamente em alguns segundos.");
        }
    }

    public static void main(String[] args) {
        // Teste rápido:
        new TelaVerificacaoEmail("teste@exemplo.com", "senha123");
    }
}
