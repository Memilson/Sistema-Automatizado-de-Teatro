package com.mycompany.mavenproject3;

import com.mycompany.mavenproject3.compra.view.TelaCompra;
import com.mycompany.mavenproject3.login.view.TelaLogin;
import com.mycompany.mavenproject3.registro.view.TelaRegistro;
import com.mycompany.mavenproject3.usuario.view.TelaAreaUsuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Main extends JFrame {
    public Main() {
        setTitle("Bem-vindo");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        JLabel label = new JLabel("Escolha uma opção:", SwingConstants.CENTER);
        JButton loginBtn = new JButton("Login");
        JButton registroBtn = new JButton("Registrar-se");
        JButton comprarBtn = new JButton("Comprar Ingressos");
        JButton usuarioBtn = new JButton("Área do Usuário");

        add(label);
        add(loginBtn);
        add(registroBtn);
        add(comprarBtn);
        add(usuarioBtn);

        loginBtn.addActionListener((ActionEvent e) -> {
            new TelaLogin();
            dispose();
        });

        registroBtn.addActionListener((ActionEvent e) -> {
            new TelaRegistro();
            dispose();
        });

        comprarBtn.addActionListener((ActionEvent e) -> {
            new TelaCompra();
            dispose();
        });

        usuarioBtn.addActionListener((ActionEvent e) -> {
            new TelaAreaUsuario();
            dispose();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
