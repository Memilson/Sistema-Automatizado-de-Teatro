package com.mycompany.mavenproject3;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AppFX {

    public void start(Stage stage) {
        // Título
        Text title = new Text("\u2699\uFE0F DramaCode");
        title.setFont(Font.font("Courier New", 30));
        title.setFill(Color.web("#d4af37"));

        Label subTitle = new Label("Engrenando arte e tecnologia desde 2025");
        subTitle.setTextFill(Color.web("#aaa"));

        // Campos de login
        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField senhaField = new PasswordField();
        senhaField.setPromptText("Senha");

        Label statusLabel = new Label("");
        statusLabel.setTextFill(Color.web("#f0e6d2"));

        // Botão de login
        Button loginBtn = new Button("Entrar");
        loginBtn.setStyle(
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #d4af37, #a6762d);" +
                        " -fx-text-fill: black;" +
                        " -fx-font-weight: bold;" +
                        " -fx-background-radius: 6px;"
        );
        loginBtn.setOnAction(e -> {
            // Aqui entra a lógica de autenticação
            String email = emailField.getText();
            String senha = senhaField.getText();
            statusLabel.setText("Autenticando " + email + "...");
        });

        VBox layout = new VBox(12, title, subTitle, emailField, senhaField, loginBtn, statusLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));
        layout.setStyle("-fx-background-color: #2c2c2c; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        layout.setEffect(new DropShadow(20, Color.web("#d4af37")));

        Scene scene = new Scene(layout, 400, 400);
        scene.setFill(Color.web("#1b1b1b"));

        stage.setTitle("Login - DramaCode");
        stage.setScene(scene);
        stage.initStyle(StageStyle.DECORATED);
        stage.show();
    }
}
