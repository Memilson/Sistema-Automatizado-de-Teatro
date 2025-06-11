package com.mycompany.mavenproject3.login.view;

import com.mycompany.mavenproject3.Main;
import com.mycompany.mavenproject3.login.controller.LoginController;
import com.mycompany.mavenproject3.supabase.SupabaseService;
import com.mycompany.mavenproject3.usuario.model.Usuario;
import com.mycompany.mavenproject3.usuario.repository.UsuarioRepository;
import com.mycompany.mavenproject3.usuario.repository.UsuarioRepositorySupabase;
import com.mycompany.mavenproject3.registro.view.TelaRegistro;
import com.mycompany.mavenproject3.usuario.service.UsuarioService;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {
    private final JTextField emailField;
    private final JPasswordField senhaField;
    private final JLabel statusLabel;

    public TelaLogin() {
        setTitle("Login de Usuário");
        setSize(350, 250);
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

        JButton registrarButton = new JButton("Registrar-se");
        registrarButton.addActionListener(e -> {
            new TelaRegistro();
            dispose();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(loginButton);
        buttonPanel.add(registrarButton);

        add(statusLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
    private void autenticar() {
        String email = emailField.getText();
        String senha = new String(senhaField.getPassword());

        String authId = LoginController.login(email, senha); // retorna o ID do usuário logado
        if (authId != null) {
            UsuarioRepository repository = new UsuarioRepositorySupabase(new SupabaseService());
            UsuarioService service = new UsuarioService(repository);
            Usuario usuario = service.buscarUsuarioPorId(authId);

            if (usuario != null) {
                JOptionPane.showMessageDialog(this, "Login bem-sucedido!");
                new Main(usuario); // redireciona para a tela principal
                dispose();
            } else {
                statusLabel.setText("Falha ao buscar dados do usuário.");
            }
        } else {
            statusLabel.setText("Credenciais inválidas.");
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaLogin::new);
    }
}
