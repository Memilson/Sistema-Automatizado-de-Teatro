package com.mycompany.mavenproject3;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
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

        // Botão para Comprar Ingressos
        JButton btnComprar = new JButton("Comprar Ingresso");
        btnComprar.addActionListener(e -> abrirTelaCompra(nomeUsuario));  // Abre a tela de compra
        add(btnComprar);

        // Botão para Ver Assentos Livres
        JButton btnVerAssentos = new JButton("Ver Assentos Livres");
        btnVerAssentos.addActionListener(e -> JOptionPane.showMessageDialog(this, "Tela de assentos livres não implementada ainda."));
        add(btnVerAssentos);

        // Botão para Relatório
        JButton btnRelatorio = new JButton("Relatório");
        btnRelatorio.addActionListener(e -> JOptionPane.showMessageDialog(this, "Tela de relatório não implementada ainda."));
        add(btnRelatorio);

        if ("Usuário Exemplo".equals(nomeUsuario)) {
            JButton btnRegistro = new JButton("Registro de Conta");
            btnRegistro.addActionListener(e -> abrirTelaRegistro());
            add(btnRegistro);
            JButton btnLogin = new JButton("Logar");
            btnLogin.addActionListener(e -> abrirTelaLogin());
            add(btnLogin);
        } else {
            // Caso esteja logado, exibe o nome do usuário
            labelNomeUsuario = new JLabel("Usuário: " + nomeUsuario, SwingConstants.RIGHT);
            add(labelNomeUsuario);
        }
    }

    private void abrirTelaCompra(String nomeUsuario) {
        // Chama a tela de compra de ingressos
        TelaCompraIngresso telaCompra = new TelaCompraIngresso(nomeUsuario);
        telaCompra.setVisible(true);
        this.dispose();  // Fecha a tela principal
    }

    private void abrirTelaRegistro() {
        // Abre a tela de registro
        TelaRegistro telaRegistro = new TelaRegistro();
        telaRegistro.setVisible(true);
        this.dispose();  // Fecha a tela principal
    }
    private void abrirTelaLogin() {
        // Abre a tela de login
        TelaLogin telaLogin = new TelaLogin();
        telaLogin.setVisible(true);
        this.dispose();  // Fecha a tela principal
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String nomeUsuario = "Usuário Exemplo";  // Aqui pode vir o nome do usuário logado
            TelaPrincipal telaPrincipal = new TelaPrincipal(nomeUsuario);
            telaPrincipal.setVisible(true);
        });
    }
}
