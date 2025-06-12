package com.mycompany.mavenproject3.registro.controller;

import com.mycompany.mavenproject3.supabase.SupabaseService;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

        if (SupabaseService.emailJaExiste(email)) {
            statusLabel.setText("Este email já está registrado. Faça login.");
            return;
        }

        boolean sucesso = RegistroController.registrar("", "", email, senha, "", "");
        if (sucesso) {
            Platform.runLater(() -> {
                statusLabel.setText("Email ja utilizado, tente novamente.");
                // Opcional: fechar ou redirecionar após X segundos
            });
        } else {
            statusLabel.setText("Erro ao registrar. Tente novamente.");
        }
    }
}
