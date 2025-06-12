package com.mycompany.mavenproject3.registro.view;

import com.mycompany.mavenproject3.Main;
import com.mycompany.mavenproject3.core.SessaoUsuario;
import com.mycompany.mavenproject3.dados.view.TelaDadosComplementares;
import com.mycompany.mavenproject3.dados.view.TelaVerificacaoEmail;
import com.mycompany.mavenproject3.login.controller.LoginController;
import com.mycompany.mavenproject3.login.view.TelaLogin;
import com.mycompany.mavenproject3.supabase.SupabaseService;
import com.mycompany.mavenproject3.usuario.model.Usuario;
import com.mycompany.mavenproject3.usuario.repository.UsuarioRepository;
import com.mycompany.mavenproject3.usuario.repository.UsuarioRepositorySupabase;
import com.mycompany.mavenproject3.usuario.service.UsuarioService;
import com.mycompany.mavenproject3.registro.controller.RegistroController;
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

public class TelaRegistro extends Application {
    private TextField emailField;
    private PasswordField senhaField;
    private PasswordField confirmarSenhaField;
    private Label statusLabel;

    @Override
    public void start(Stage stage) {
        stage.setTitle("DramaCore Theatre - Registro");

        emailField = new TextField();
        senhaField = new PasswordField();
        confirmarSenhaField = new PasswordField();
        statusLabel = new Label();
        statusLabel.setTextFill(Color.web("#d4af37"));

        Label emailLabel = new Label("Email:");
        Label senhaLabel = new Label("Senha:");
        Label confirmarLabel = new Label("Confirmar Senha:");
        emailLabel.setTextFill(Color.web("#e0dcbf"));
        senhaLabel.setTextFill(Color.web("#e0dcbf"));
        confirmarLabel.setTextFill(Color.web("#e0dcbf"));

        // Cabeçalho
        Text titulo = new Text("🎭 DramaCore Theatre");
        titulo.setFont(Font.font("Georgia", 36));
        titulo.setFill(Color.web("#d4af37"));
        titulo.setEffect(new DropShadow(5, Color.web("#a6762d")));

        Label subtitulo = new Label("Junte-se ao espetáculo.");
        subtitulo.setFont(Font.font("Georgia", 16));
        subtitulo.setTextFill(Color.web("#aaa"));

        VBox header = new VBox(5, titulo, subtitulo);
        header.setAlignment(Pos.CENTER);

        GridPane formGrid = new GridPane();
        formGrid.setVgap(15);
        formGrid.setHgap(20);
        formGrid.add(emailLabel, 0, 0);
        formGrid.add(emailField, 1, 0);
        formGrid.add(senhaLabel, 0, 1);
        formGrid.add(senhaField, 1, 1);
        formGrid.add(confirmarLabel, 0, 2);
        formGrid.add(confirmarSenhaField, 1, 2);
        formGrid.setAlignment(Pos.CENTER);

        Button registrarBtn = new Button("✒️ Registrar");
        Button voltarBtn = new Button("↩️ Voltar");
        estilizarBotao(registrarBtn);
        estilizarBotao(voltarBtn);

        registrarBtn.setOnAction(e -> registrarUsuario(stage));
        voltarBtn.setOnAction(e -> {
            new TelaLogin().start(new Stage());
            stage.close();
        });

        HBox buttonBox = new HBox(20, registrarBtn, voltarBtn);
        buttonBox.setAlignment(Pos.CENTER);

        VBox content = new VBox(30, header, statusLabel, formGrid, buttonBox);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(60));
        content.setMaxWidth(600);
        content.setStyle("-fx-background-color: rgba(30,30,30,0.94); -fx-background-radius: 20;");
        content.setEffect(new DropShadow(25, Color.web("#d4af37")));

        StackPane root = new StackPane(content);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #0d0d0d, #1a1a1a);");

        Scene scene = new Scene(root, 1280, 720);
        scene.setFill(Color.web("#121212"));

        stage.setScene(scene);
        stage.setMinWidth(960);
        stage.setMinHeight(600);
        stage.centerOnScreen();
        stage.show();
    }

    private void estilizarBotao(Button btn) {
        btn.setFont(Font.font("Georgia", 16));
        btn.setStyle(
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #d4af37, #a6762d);" +
                        " -fx-text-fill: black;" +
                        " -fx-font-weight: bold;" +
                        " -fx-background-radius: 10px;"
        );
        btn.setPrefWidth(160);
        btn.setPrefHeight(45);
    }

    private void registrarUsuario(Stage stage) {
        String email = emailField.getText();
        String senha = senhaField.getText();
        String senhaConfirmacao = confirmarSenhaField.getText();

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
                    TelaVerificacaoEmail.abrir(email, senha);
                }

                stage.close();
            });
        } else {
            statusLabel.setText("Erro ao registrar.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
