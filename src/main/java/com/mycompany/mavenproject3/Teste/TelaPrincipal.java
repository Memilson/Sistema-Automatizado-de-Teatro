package com.mycompany.mavenproject3.Teste;

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

        // Título
        JLabel titulo = new JLabel("Sistema de Vendas de Ingressos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(titulo);

        // Exibe o nome do usuário, caso não seja o "Usuário Exemplo"
        if (!"Usuário Exemplo".equals(nomeUsuario)) {
            labelNomeUsuario = new JLabel("Usuário: " + nomeUsuario, SwingConstants.RIGHT);
            add(labelNomeUsuario);
        }

        // Botão para Comprar Ingressos
        JButton btnComprar = new JButton("Comprar Ingresso");
        btnComprar.addActionListener(e -> abrirTelaCompra(nomeUsuario)); // Abre a tela de compra
        add(btnComprar);

        // Botão para Ver Assentos Livres
        JButton btnVerAssentos = new JButton("Ver Assentos Livres");
        btnVerAssentos.addActionListener(e -> abrirTelaAssentosLivres(nomeUsuario));
        add(btnVerAssentos);

        // Botão para Relatório
        JButton btnRelatorio = new JButton("Relatório");
        btnRelatorio.addActionListener(e -> JOptionPane.showMessageDialog(this, "Tela de relatório não implementada ainda."));
        add(btnRelatorio);

        // Exibe botões adicionais somente se for "Usuário Exemplo"
        if ("Usuário Exemplo".equals(nomeUsuario)) {
            JButton btnRegistro = new JButton("Registro de Conta");
            btnRegistro.addActionListener(e -> abrirTelaRegistro());
            add(btnRegistro);

            JButton btnLogin = new JButton("Logar");
            btnLogin.addActionListener(e -> abrirTelaLogin());
            add(btnLogin);

            // Opcional: Desabilitar botões existentes
            btnComprar.setEnabled(false);
            btnVerAssentos.setEnabled(false);
        }
    }

    private void abrirTelaCompra(String nomeUsuario) {
        // Chama a tela de compra de ingressos
        TelaCompraIngresso telaCompra = new TelaCompraIngresso(nomeUsuario);
        telaCompra.setVisible(true);
        this.dispose(); // Fecha a tela principal
    }

    private void abrirTelaRegistro() {
        // Abre a tela de registro
        TelaRegistro telaRegistro = new TelaRegistro();
        telaRegistro.setVisible(true);
        this.dispose(); // Fecha a tela principal
    }

    private void abrirTelaLogin() {
        // Abre a tela de login
        TelaLogin telaLogin = new TelaLogin();
        telaLogin.setVisible(true);
        this.dispose(); // Fecha a tela principal
    }

    private void abrirTelaAssentosLivres(String nomeUsuario) {
        // Abre a tela de login
        TelaAssentosLivres telaAssentosLivres = new TelaAssentosLivres(nomeUsuario);
        telaAssentosLivres.setVisible(true);
        this.dispose(); // Fecha a tela principal
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String nomeUsuario = "Usuário Exemplo"; // Aqui pode vir o nome do usuário logado
            TelaPrincipal telaPrincipal = new TelaPrincipal(nomeUsuario);
            telaPrincipal.setVisible(true);
        });
    }
}
