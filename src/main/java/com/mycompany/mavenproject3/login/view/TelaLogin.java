package com.mycompany.mavenproject3.login.view;

import com.mycompany.mavenproject3.Main;
import com.mycompany.mavenproject3.login.controller.LoginController;
import com.mycompany.mavenproject3.usuario.view.TelaAreaUsuario;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {
    private final JTextField emailField;
    private final JPasswordField senhaField;
    private final JLabel statusLabel;

    public TelaLogin() {
        setTitle("Login de Usuário");
        setSize(350, 220);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        emailField = new JTextField();
        senhaField = new JPasswordField();

        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Senha:"));
        formPanel.add(senhaField);

        statusLabel = new JLabel("", SwingConstants.CENTER);

        JButton loginButton = new JButton("Entrar");
        loginButton.addActionListener(e -> autenticar());

        JButton voltarButton = new JButton("Menu Principal");
        voltarButton.addActionListener(e -> {
            new Main();
            dispose();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(loginButton);
        buttonPanel.add(voltarButton);

        add(statusLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void autenticar() {
        String email = emailField.getText();
        String senha = new String(senhaField.getPassword());

        boolean sucesso = LoginController.login(email, senha);
        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Login bem-sucedido!");
            new Main();
            dispose();
        } else {
            statusLabel.setText("Credenciais inválidas.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaLogin::new);
    }
}
