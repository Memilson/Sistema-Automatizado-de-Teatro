package com.mycompany.mavenproject3;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class TelaPrincipal extends JFrame {
    private JLabel labelNomeUsuario;

    public TelaPrincipal(String nomeUsuario) {
        setTitle("Sistema de Vendas de Ingressos - Teatro ABC");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1, 10, 10));
        // Configura o título e os componentes principais da tela, incluindo botões de navegação
        JLabel titulo = new JLabel("Sistema de Vendas de Ingressos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(titulo);
        if (!"Usuário Exemplo".equals(nomeUsuario)) {
            labelNomeUsuario = new JLabel("Usuário: " + nomeUsuario, SwingConstants.RIGHT);
            add(labelNomeUsuario);
        }
        JButton btnComprar = new JButton("Comprar Ingresso");
        btnComprar.addActionListener(e -> abrirTelaCompra(nomeUsuario));
        add(btnComprar);
        JButton btnVerAssentos = new JButton("Ver Assentos Livres");
        btnVerAssentos.addActionListener(e -> abrirTelaAssentosLivres(nomeUsuario));
        add(btnVerAssentos);
        JButton btnRelatorio = new JButton("Relatório");
        btnRelatorio.addActionListener(e -> abrirTelaRelatorio(nomeUsuario));
        add(btnRelatorio);
        // Adiciona botões de registro e login se o usuário for "Usuário Exemplo"
        if ("Usuário Exemplo".equals(nomeUsuario)) {
            JButton btnRegistro = new JButton("Registro de Conta");
            btnRegistro.addActionListener(e -> abrirTelaRegistro());
            add(btnRegistro);
            JButton btnLogin = new JButton("Logar");
            btnLogin.addActionListener(e -> abrirTelaLogin());
            add(btnLogin);
            btnComprar.setEnabled(false);
            btnVerAssentos.setEnabled(false);
        }
    }
    // Abre a tela de compra de ingressos e fecha a tela principal
    private void abrirTelaCompra(String nomeUsuario) {
        TelaCompraIngresso telaCompra = new TelaCompraIngresso(nomeUsuario);
        telaCompra.setVisible(true);
        this.dispose();
    }
    // Abre a tela de registro de usuário
    private void abrirTelaRegistro() {
        TelaRegistro telaRegistro = new TelaRegistro();
        telaRegistro.setVisible(true);
        this.dispose();
    }
    // Abre a tela de login
    private void abrirTelaLogin() {
        TelaLogin telaLogin = new TelaLogin();
        telaLogin.setVisible(true);
        this.dispose();
    }
    // Abre a tela que mostra assentos livres
    private void abrirTelaAssentosLivres(String nomeUsuario) {
        TelaAssentosLivres telaAssentosLivres = new TelaAssentosLivres(nomeUsuario);
        telaAssentosLivres.setVisible(true);
        this.dispose();
    }
    private void abrirTelaRelatorio(String nomeUsuario) {
        TelaRelatorio telaRelatorio = new TelaRelatorio(nomeUsuario);
        telaRelatorio.setVisible(true);
        this.dispose();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String nomeUsuario = "Usuário Exemplo";
            TelaPrincipal telaPrincipal = new TelaPrincipal(nomeUsuario);
            telaPrincipal.setVisible(true);
        });
    }
}