package com.mycompany.mavenproject3;

import com.mycompany.mavenproject3.admin.view.TelaAdmin;
import com.mycompany.mavenproject3.compra.view.TelaCompraFX;
import com.mycompany.mavenproject3.core.SessaoUsuario;
import com.mycompany.mavenproject3.usuario.controller.AreaUsuarioController;
import com.mycompany.mavenproject3.usuario.controller.UsuarioController;
import com.mycompany.mavenproject3.usuario.factory.UsuarioControllerFactory;
import com.mycompany.mavenproject3.usuario.model.Usuario;
import com.mycompany.mavenproject3.usuario.view.TelaAreaUsuario;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    private Usuario usuarioLogado;

    public Main() {}

    public Main(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    @Override
    public void start(Stage stage) {
        if (usuarioLogado == null) {
            System.err.println("Usuário não logado.");
            return;
        }

        stage.setTitle("DramaCore Theatre - Menu Principal");

        Text titulo = new Text("🎭 DramaCore Theatre");
        titulo.setFont(Font.font("Georgia", 36));
        titulo.setFill(Color.web("#d4af37"));
        titulo.setEffect(new DropShadow(5, Color.web("#a6762d")));

        Text subtitulo = new Text("Bem-vindo, " + usuarioLogado.getNome());
        subtitulo.setFont(Font.font("Georgia", 18));
        subtitulo.setFill(Color.WHITE);

        VBox header = new VBox(5, titulo, subtitulo);
        header.setAlignment(Pos.CENTER);

        Button comprarBtn = new Button("🛒 Comprar Ingressos");
        Button usuarioBtn = new Button("👤 Área do Usuário");
        estilizarBotao(comprarBtn);
        estilizarBotao(usuarioBtn);

        comprarBtn.setOnAction(e -> {
            new TelaCompraFX(usuarioLogado).start(new Stage());
            stage.close();
        });

        usuarioBtn.setOnAction(e -> {
            SessaoUsuario.iniciar(usuarioLogado.getId());
            UsuarioController usuarioController = UsuarioControllerFactory.criar();
            AreaUsuarioController areaController = new AreaUsuarioController(usuarioController);
            TelaAreaUsuario tela = new TelaAreaUsuario(areaController);
            tela.start(new Stage());
            stage.close();
        });

        VBox botoes = new VBox(20, comprarBtn, usuarioBtn);
        botoes.setAlignment(Pos.CENTER);

        if (usuarioLogado.isAdmin()) {
            Button adminBtn = new Button("🛠️ Painel Admin");
            estilizarBotao(adminBtn);
            adminBtn.setOnAction(e -> {
                TelaAdmin adminTela = new TelaAdmin(usuarioLogado);
                adminTela.start(new Stage());
            });
            botoes.getChildren().add(adminBtn);
        }

        VBox content = new VBox(40, header, botoes);
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
        stage.setMaximized(true);  // sempre maximiza
        stage.show();
    }

    private void estilizarBotao(Button btn) {
        btn.setFont(Font.font("Georgia", 16));
        btn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ffcc00, #b8860b);" +
                        " -fx-text-fill: black;" +
                        " -fx-background-radius: 10px;"
        );
        btn.setPrefWidth(250);
        btn.setPrefHeight(50);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
