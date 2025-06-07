package com.mycompany.mavenproject3.registro.view;

import com.mycompany.mavenproject3.Main;
import com.mycompany.mavenproject3.registro.controller.RegistroController;

import javax.swing.*;
import java.awt.*;

public class TelaRegistro extends JFrame {
    private final JTextField emailField;
    private final JPasswordField senhaField;
    private final JPasswordField confirmarSenhaField;
    private final JLabel statusLabel;

    public TelaRegistro() {
        setTitle("Registro de Usuário");
        setSize(350, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        emailField = new JTextField();
        senhaField = new JPasswordField();
        confirmarSenhaField = new JPasswordField();

        formPanel.add(new JLabel("Email:")); formPanel.add(emailField);
        formPanel.add(new JLabel("Senha:")); formPanel.add(senhaField);
        formPanel.add(new JLabel("Confirmar Senha:")); formPanel.add(confirmarSenhaField);

        statusLabel = new JLabel("", SwingConstants.CENTER);

        JButton registrarButton = new JButton("Registrar");
        JButton voltarButton = new JButton("Menu Principal");

        registrarButton.addActionListener(e -> registrarUsuario());
        voltarButton.addActionListener(e -> {
            new Main();
            dispose();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(registrarButton);
        buttonPanel.add(voltarButton);

        add(statusLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void registrarUsuario() {
        String email = emailField.getText();
        String senha = new String(senhaField.getPassword());
        String senhaConfirmacao = new String(confirmarSenhaField.getPassword());

        if (!senha.equals(senhaConfirmacao)) {
            statusLabel.setText("As senhas não coincidem.");
            return;
        }

        boolean sucesso = RegistroController.registrar("", "", email, senha, "", "");
        statusLabel.setText(sucesso ? "Registro realizado! Verifique seu e-mail." : "Erro ao registrar.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaRegistro::new);
    }
}
