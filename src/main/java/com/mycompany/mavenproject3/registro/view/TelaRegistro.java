package com.mycompany.mavenproject3.registro.view;

import com.mycompany.mavenproject3.Main;
import com.mycompany.mavenproject3.core.SessaoUsuario;
import com.mycompany.mavenproject3.dados.view.TelaDadosComplementares;
import com.mycompany.mavenproject3.dados.view.TelaVerificacaoEmail;
import com.mycompany.mavenproject3.login.controller.LoginController;
import com.mycompany.mavenproject3.login.view.TelaLogin;
import com.mycompany.mavenproject3.registro.controller.RegistroController;
import com.mycompany.mavenproject3.supabase.SupabaseService;
import com.mycompany.mavenproject3.usuario.model.Usuario;
import com.mycompany.mavenproject3.usuario.repository.UsuarioRepository;
import com.mycompany.mavenproject3.usuario.repository.UsuarioRepositorySupabase;
import com.mycompany.mavenproject3.usuario.service.UsuarioService;

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
        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Registro realizado com sucesso!");

            // Realiza login automático após registro
            String authId = LoginController.login(email, senha);
            if (authId != null) {
                UsuarioRepository repository = new UsuarioRepositorySupabase(new SupabaseService());
                UsuarioService service = new UsuarioService(repository);
                Usuario usuario = service.buscarUsuarioPorId(authId);

                if (usuario == null || !repository.temDadosComplementares(authId)) {
                    new TelaDadosComplementares().setVisible(true);
                } else {
                    new Main(usuario);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Confirme seu Email!!.");
                new TelaVerificacaoEmail(email, senha);
            }

            dispose();
        } else {
            statusLabel.setText("Erro ao registrar.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaRegistro::new);
    }
}
