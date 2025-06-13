package com.mycompany.mavenproject3.admin.view;
import com.mycompany.mavenproject3.usuario.model.Usuario;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
public class TelaAdmin {
    private final Usuario usuarioLogado;
    public TelaAdmin(Usuario usuario) {this.usuarioLogado = usuario;}
    public void start(Stage stage) {
        if (!usuarioLogado.isAdmin()) {
            new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR, "Acesso negado.").showAndWait();
            return;}
        stage.setTitle("Painel Administrativo - DramaCore Theatre");
        TabPane tabPane = new TabPane();
        Tab tabRelatorios = new Tab("📊 Relatórios", new PainelRelatorios());
        Tab tabCriarPeca = new Tab("🎭 Criar Peça", new PainelCriarPeca());
        Tab tabUsuarios = new Tab("👥 Usuários", new PainelUsuarios());
        tabRelatorios.setClosable(false);
        tabCriarPeca.setClosable(false);
        tabUsuarios.setClosable(false);
        tabPane.getTabs().addAll(tabRelatorios, tabCriarPeca, tabUsuarios);
        VBox layout = new VBox(tabPane);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #0d0d0d, #1a1a1a);");
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 960, 600, Color.web("#121212"));
        stage.setScene(scene);
        stage.show();}}