package com.mycompany.mavenproject3;

import com.mycompany.mavenproject3.core.SessaoUsuario;
import com.mycompany.mavenproject3.supabase.SupabaseService;
import com.mycompany.mavenproject3.usuario.controller.AreaUsuarioController;
import com.mycompany.mavenproject3.usuario.controller.UsuarioController;
import com.mycompany.mavenproject3.usuario.factory.UsuarioControllerFactory;
import com.mycompany.mavenproject3.usuario.model.Usuario;
import com.mycompany.mavenproject3.admin.TelaAdmin;
import com.mycompany.mavenproject3.usuario.repository.UsuarioRepository;
import com.mycompany.mavenproject3.usuario.repository.UsuarioRepositorySupabase;
import com.mycompany.mavenproject3.usuario.service.UsuarioService;
import com.mycompany.mavenproject3.usuario.view.TelaAreaUsuario;
import com.mycompany.mavenproject3.compra.view.TelaCompra;
import com.mycompany.mavenproject3.login.view.TelaLogin;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private Usuario usuarioLogado;

    // Construtor principal com usuário autenticado
    public Main(Usuario usuario) {
        this.usuarioLogado = usuario;
        inicializarTela();
    }

    // Construtor padrão: redireciona para tela de login
    public Main() {
        JOptionPane.showMessageDialog(null, "Você precisa estar logado.");
        new TelaLogin();
        dispose();
    }

    private void inicializarTela() {
        setTitle("Bem-vindo, " + usuarioLogado.getNome());
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(0, 1, 10, 10));

        JLabel label = new JLabel("Escolha uma opção:", SwingConstants.CENTER);
        JButton comprarBtn = new JButton("Comprar Ingressos");
        JButton usuarioBtn = new JButton("Área do Usuário");

        add(label);
        add(comprarBtn);
        add(usuarioBtn);

        // Exibe botão de admin apenas se for admin
        if (usuarioLogado.isAdmin()) {
            JButton adminBtn = new JButton("Painel Admin");
            adminBtn.addActionListener(e -> new TelaAdmin(usuarioLogado));
            add(adminBtn);
        }

        comprarBtn.addActionListener(e -> {
            new TelaCompra(usuarioLogado);
            dispose();
        });

        usuarioBtn.addActionListener(e -> {
            SessaoUsuario.iniciar(usuarioLogado.getId());
            UsuarioController usuarioController = UsuarioControllerFactory.criar();
            AreaUsuarioController areaController = new AreaUsuarioController(usuarioController);
            new TelaAreaUsuario(areaController);
            dispose();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaLogin::new); // inicia pelo login
    }
}