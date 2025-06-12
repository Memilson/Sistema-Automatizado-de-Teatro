package com.mycompany.mavenproject3.dados.view;

import com.mycompany.mavenproject3.login.controller.LoginController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TelaVerificacaoEmail extends Application {
    private final String email;
    private final String senha;
    private final Runnable aoConfirmar;

    private Label statusLabel;

    public TelaVerificacaoEmail(String email, String senha, Runnable aoConfirmar) {
        this.email = email;
        this.senha = senha;
        this.aoConfirmar = aoConfirmar;
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("DramaCore Theatre - Verificação de Email");

        Text titulo = new Text("🎭 DramaCore Theatre");
        titulo.setFont(Font.font("Georgia", 36));
        titulo.setFill(Color.web("#d4af37"));
        titulo.setEffect(new DropShadow(5, Color.web("#a6762d")));

        Label subtitulo = new Label("Verifique seu email para continuar.");
        subtitulo.setFont(Font.font("Georgia", 16));
        subtitulo.setTextFill(Color.web("#aaa"));

        VBox header = new VBox(5, titulo, subtitulo);
        header.setAlignment(Pos.CENTER);

        Label emailInfo = new Label("Enviamos um email para: " + email);
        emailInfo.setTextFill(Color.web("#e0dcbf"));

        statusLabel = new Label("");
        statusLabel.setTextFill(Color.web("#d4af37"));

        Button verificarBtn = new Button("🔍 Verificar Email");
        estilizarBotao(verificarBtn);

        verificarBtn.setOnAction(e -> verificar(stage));

        VBox content = new VBox(20, header, emailInfo, statusLabel, verificarBtn);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(60));
        content.setStyle("-fx-background-color: rgba(30,30,30,0.94); -fx-background-radius: 20;");
        content.setEffect(new DropShadow(25, Color.web("#d4af37")));

        StackPane root = new StackPane(content);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #0d0d0d, #1a1a1a);");

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void verificar(Stage stage) {
        String authId = LoginController.login(email, senha);
        if (authId != null) {
            statusLabel.setText("Email verificado!");
            Platform.runLater(() -> {
                stage.close();
                if (aoConfirmar != null) {
                    aoConfirmar.run();
                }
            });
        } else {
            statusLabel.setText("Ainda não confirmado.");
        }
    }

    private void estilizarBotao(Button btn) {
        btn.setFont(Font.font("Georgia", 16));
        btn.setStyle(
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #d4af37, #a6762d);" +
                        "-fx-text-fill: black;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 10px;"
        );
        btn.setPrefWidth(200);
        btn.setPrefHeight(45);
    }

    public static void abrir(String email, String senha, Runnable aoConfirmar) {
        Platform.runLater(() -> {
            try {
                new TelaVerificacaoEmail(email, senha, aoConfirmar).start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
