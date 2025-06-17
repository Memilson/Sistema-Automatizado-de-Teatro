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
import com.mycompany.mavenproject3.view.UIUtils;

public class TelaVerificacaoEmail extends Application {
    private final String email;
    private final String senha;

    private Label statusLabel;

    public TelaVerificacaoEmail(String email, String senha) {
        this.email = email;
        this.senha = senha;
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
        UIUtils.estilizarBotao(verificarBtn);

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
            statusLabel.setText("Email verificado! Faça login.");
            Platform.runLater(stage::close);
        } else {
            statusLabel.setText("Ainda não confirmado.");
        }
    }


    public static void abrir(String email, String senha) {
        Platform.runLater(() -> {
            try {
                new TelaVerificacaoEmail(email, senha).start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
