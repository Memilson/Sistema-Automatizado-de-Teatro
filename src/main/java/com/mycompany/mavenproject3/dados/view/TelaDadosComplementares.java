package com.mycompany.mavenproject3.dados.view;

import com.mycompany.mavenproject3.Main;
import com.mycompany.mavenproject3.core.SessaoUsuario;
import com.mycompany.mavenproject3.common.ValidadorCPF;
import com.mycompany.mavenproject3.dados.controller.ValidadorUsuario;
import com.mycompany.mavenproject3.supabase.SupabaseService;
import com.mycompany.mavenproject3.usuario.model.Usuario;
import com.mycompany.mavenproject3.usuario.repository.UsuarioRepositorySupabase;
import com.mycompany.mavenproject3.usuario.service.UsuarioService;
import javafx.application.Application;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TelaDadosComplementares extends Application {
    private TextField nomeField;
    private TextField cpfField;
    private TextField nascimentoField;
    private TextField telefoneField;
    private Label statusLabel;

    @Override
    public void start(Stage stage) {
        stage.setTitle("DramaCore Theatre - Dados Complementares");

        nomeField = new TextField();
        cpfField = new TextField();
        nascimentoField = new TextField();
        telefoneField = new TextField();
        statusLabel = new Label();
        statusLabel.setTextFill(Color.web("#d4af37"));

        Label nomeLabel = new Label("Nome:");
        Label cpfLabel = new Label("CPF:");
        Label nascimentoLabel = new Label("Nascimento:");
        Label telefoneLabel = new Label("Telefone:");

        nomeLabel.setTextFill(Color.web("#e0dcbf"));
        cpfLabel.setTextFill(Color.web("#e0dcbf"));
        nascimentoLabel.setTextFill(Color.web("#e0dcbf"));
        telefoneLabel.setTextFill(Color.web("#e0dcbf"));

        Text titulo = new Text("🎭 DramaCore Theatre");
        titulo.setFont(Font.font("Georgia", 36));
        titulo.setFill(Color.web("#d4af37"));
        titulo.setEffect(new DropShadow(5, Color.web("#a6762d")));

        Label subtitulo = new Label("Complete sua identidade teatral.");
        subtitulo.setFont(Font.font("Georgia", 16));
        subtitulo.setTextFill(Color.web("#aaa"));

        VBox header = new VBox(5, titulo, subtitulo);
        header.setAlignment(Pos.CENTER);

        GridPane formGrid = new GridPane();
        formGrid.setVgap(15);
        formGrid.setHgap(20);
        formGrid.add(nomeLabel, 0, 0);
        formGrid.add(nomeField, 1, 0);
        formGrid.add(cpfLabel, 0, 1);
        formGrid.add(cpfField, 1, 1);
        formGrid.add(nascimentoLabel, 0, 2);
        formGrid.add(nascimentoField, 1, 2);
        formGrid.add(telefoneLabel, 0, 3);
        formGrid.add(telefoneField, 1, 3);
        formGrid.setAlignment(Pos.CENTER);

        Button salvarBtn = new Button("Salvar");
        Button voltarBtn = new Button("↩️ Menu Principal");
        estilizarBotao(salvarBtn);
        estilizarBotao(voltarBtn);

        salvarBtn.setOnAction(e -> salvarDados());
        voltarBtn.setOnAction(e -> {
            Main mainApp = new Main();
            try {
                mainApp.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            stage.close();
        });

        HBox buttonBox = new HBox(20, salvarBtn, voltarBtn);
        buttonBox.setAlignment(Pos.CENTER);

        VBox content = new VBox(30, header, statusLabel, formGrid, buttonBox);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(60));
        content.setMaxWidth(650);
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

    private void salvarDados() {
        String nome = nomeField.getText();
        String cpf = cpfField.getText();
        String telefone = telefoneField.getText();
        String nascimentoStr = nascimentoField.getText();

        if (!ValidadorCPF.validar(cpf)) {
            statusLabel.setText("CPF inválido.");
            return;
        }

        try {
            Date nascimento = new SimpleDateFormat("dd/MM/yyyy").parse(nascimentoStr);
            String nascimentoFormatado = new SimpleDateFormat("yyyy-MM-dd").format(nascimento);

            String userId = SessaoUsuario.getUserId();
            if (userId == null) {
                statusLabel.setText("Usuário não autenticado.");
                return;
            }

            if (ValidadorUsuario.cpfJaExisteExceto(cpf, userId)) {
                statusLabel.setText("Este CPF já está em uso por outro usuário.");
                return;
            }

            boolean sucesso = SupabaseService.salvarDadosComplementares(userId, nome, cpf, nascimentoFormatado, telefone);
            if (sucesso) {
                UsuarioRepositorySupabase repo = new UsuarioRepositorySupabase(new SupabaseService());
                Usuario usuario = new UsuarioService(repo).buscarUsuarioPorId(userId);

                Main mainApp = new Main(usuario);
                mainApp.start(new Stage());

                Stage stage = (Stage) nomeField.getScene().getWindow();
                stage.close();
            } else {
                statusLabel.setText("Erro ao salvar dados.");
            }
        } catch (ParseException | RuntimeException ex) {
            statusLabel.setText("Data inválida.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}