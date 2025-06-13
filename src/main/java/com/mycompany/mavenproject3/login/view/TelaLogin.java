package com.mycompany.mavenproject3.login.view;

import com.mycompany.mavenproject3.Main;
import com.mycompany.mavenproject3.dados.view.TelaDadosComplementares;
import com.mycompany.mavenproject3.login.controller.LoginController;
import com.mycompany.mavenproject3.registro.view.TelaRegistro;
import com.mycompany.mavenproject3.supabase.SupabaseService;
import com.mycompany.mavenproject3.usuario.model.Usuario;
import com.mycompany.mavenproject3.usuario.repository.UsuarioRepository;
import com.mycompany.mavenproject3.usuario.repository.UsuarioRepositorySupabase;
import com.mycompany.mavenproject3.usuario.service.UsuarioService;
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

public class TelaLogin extends Application {
    private TextField emailField;
    private PasswordField senhaField;
    private Label statusLabel;

    @Override
    public void start(Stage stage) {
        stage.setTitle("DramaCore Theatre - Login");

        emailField = new TextField();
        senhaField = new PasswordField();
        statusLabel = new Label();
        statusLabel.setTextFill(Color.web("#ffd700"));

        Label emailLabel = new Label("Email:");
        Label senhaLabel = new Label("Senha:");
        emailLabel.setTextFill(Color.web("#ffd700"));
        senhaLabel.setTextFill(Color.web("#ffd700"));

        Text titulo = new Text("🎭 DramaCore Theatre");
        titulo.setFont(Font.font("Georgia", 36));
        titulo.setFill(Color.web("#d4af37"));
        titulo.setEffect(new DropShadow(5, Color.web("#a6762d")));

        Label subtitulo = new Label("Arte. Engrenagens. E Segredos.");
        subtitulo.setFont(Font.font("Georgia", 16));
        subtitulo.setTextFill(Color.web("#aaa"));

        VBox header = new VBox(5, titulo, subtitulo);
        header.setAlignment(Pos.CENTER);

        GridPane formGrid = new GridPane();
        formGrid.setVgap(20);
        formGrid.setHgap(20);
        formGrid.add(emailLabel, 0, 0);
        formGrid.add(emailField, 1, 0);
        formGrid.add(senhaLabel, 0, 1);
        formGrid.add(senhaField, 1, 1);
        formGrid.setAlignment(Pos.CENTER);

        Button loginBtn = new Button("🎫 Entrar");
        Button registrarBtn = new Button("✒️ Registrar-se");
        estilizarBotao(loginBtn);
        estilizarBotao(registrarBtn);

        loginBtn.setOnAction(e -> autenticar(stage));
        registrarBtn.setOnAction(e -> {
            try {
                new TelaRegistro().start(new Stage());
                stage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        HBox buttonBox = new HBox(20, loginBtn, registrarBtn);
        buttonBox.setAlignment(Pos.CENTER);

        VBox content = new VBox(30, header, statusLabel, formGrid, buttonBox);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(60));
        content.setMaxWidth(550);
        content.setStyle("-fx-background-color: rgba(30,30,30,0.92); -fx-background-radius: 20;");
        content.setEffect(new DropShadow(25, Color.web("#d4af37")));

        StackPane root = new StackPane(content);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #0d0d0d, #1a1a1a);");

        Scene scene = new Scene(root, 1280, 720);
        scene.setFill(Color.web("#121212"));

        stage.setScene(scene);
        stage.setMinWidth(960);
        stage.setMinHeight(600);
        stage.centerOnScreen();
        stage.setMaximized(true);
        stage.show();
    }

    private void estilizarBotao(Button btn) {
        btn.setFont(Font.font("Georgia", 16));
        btn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ffcc00, #b8860b);" +
                        " -fx-text-fill: black;" +
                        " -fx-font-weight: bold;" +
                        " -fx-background-radius: 10px;"
        );
        btn.setPrefWidth(160);
        btn.setPrefHeight(45);
    }

    private void autenticar(Stage currentStage) {
        String email = emailField.getText();
        String senha = senhaField.getText();

        String authId = LoginController.login(email, senha);
        if (authId != null) {
            UsuarioRepository repository = new UsuarioRepositorySupabase(new SupabaseService());
            UsuarioService service = new UsuarioService(repository);
            Usuario usuario = service.buscarUsuarioPorId(authId);

            if (usuario == null || !repository.temDadosComplementares(authId)) {
                try {
                    new TelaDadosComplementares().start(new Stage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                Platform.runLater(() -> {
                    Main mainApp = new Main(usuario);
                    try {
                        mainApp.start(new Stage());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            }

            currentStage.close();
        } else {
            statusLabel.setText("Credenciais inválidas.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
