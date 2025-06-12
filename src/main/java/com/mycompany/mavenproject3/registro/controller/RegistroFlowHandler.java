package com.mycompany.mavenproject3.registro.controller;

import com.mycompany.mavenproject3.Main;
import com.mycompany.mavenproject3.dados.view.TelaDadosComplementares;
import com.mycompany.mavenproject3.dados.view.TelaVerificacaoEmail;
import com.mycompany.mavenproject3.login.controller.LoginController;
import com.mycompany.mavenproject3.supabase.SupabaseService;
import com.mycompany.mavenproject3.usuario.model.Usuario;
import com.mycompany.mavenproject3.usuario.repository.UsuarioRepository;
import com.mycompany.mavenproject3.usuario.repository.UsuarioRepositorySupabase;
import com.mycompany.mavenproject3.usuario.service.UsuarioService;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class RegistroFlowHandler {

    public static void estilizarBotao(Button btn) {
        btn.setFont(javafx.scene.text.Font.font("Georgia", 16));
        btn.setStyle(
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #d4af37, #a6762d);" +
                        " -fx-text-fill: black;" +
                        " -fx-font-weight: bold;" +
                        " -fx-background-radius: 10px;"
        );
        btn.setPrefWidth(160);
        btn.setPrefHeight(45);
    }

    public static void registrarUsuario(String email, String senha, String senhaConfirmacao, Label statusLabel, Stage stage) {
        if (!senha.equals(senhaConfirmacao)) {
            statusLabel.setText("As senhas não coincidem.");
            return;
        }

        boolean sucesso = RegistroController.registrar("", "", email, senha, "", "");
        if (sucesso) {
            Platform.runLater(() -> {
                String authId = LoginController.login(email, senha);
                if (authId != null) {
                    UsuarioRepository repository = new UsuarioRepositorySupabase(new SupabaseService());
                    UsuarioService service = new UsuarioService(repository);
                    Usuario usuario = service.buscarUsuarioPorId(authId);

                    if (usuario == null || !repository.temDadosComplementares(authId)) {
                        new TelaDadosComplementares().start(new Stage());
                    } else {
                        Main mainApp = new Main(usuario);
                        try {
                            mainApp.start(new Stage());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    TelaVerificacaoEmail.abrir(email, senha, () -> {
                        try {
                            new TelaDadosComplementares().start(new Stage());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });

                }

                stage.close();
            });
        } else {
            statusLabel.setText("Erro ao registrar.");
        }
    }
}
