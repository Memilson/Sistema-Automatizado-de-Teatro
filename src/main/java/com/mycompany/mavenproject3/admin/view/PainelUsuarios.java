package com.mycompany.mavenproject3.admin.view;

import com.mycompany.mavenproject3.admin.controller.UsuarioAdminControllerFX;
import com.mycompany.mavenproject3.usuario.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PainelUsuarios extends VBox {
    private final TableView<Usuario> tabela = new TableView<>();
    private final ObservableList<Usuario> dadosUsuarios = FXCollections.observableArrayList();
    public PainelUsuarios() {
        setSpacing(15);
        setPadding(new Insets(20));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: linear-gradient(to bottom right, #1a1a1a, #000000);");
        Text titulo = new Text("👥 Gerenciamento de Usuários");
        titulo.setFont(Font.font("Georgia", 22));
        titulo.setFill(Color.GOLD);
        configurarTabela();
        Button atualizar = new Button("Atualizar Lista");
        Button alternarAdmin = new Button("⚖️ Alternar Admin");
        atualizar.setOnAction(e -> UsuarioAdminControllerFX.carregarUsuarios(tabela));
        alternarAdmin.setOnAction(e -> UsuarioAdminControllerFX.alternarAdminStatus(tabela));
        HBox botoes = new HBox(20, atualizar, alternarAdmin);
        botoes.setAlignment(Pos.CENTER);
        getChildren().addAll(titulo, tabela, botoes);
        UsuarioAdminControllerFX.configurarColunas(tabela);
        UsuarioAdminControllerFX.carregarUsuarios(tabela);}
    private void configurarTabela() {
        tabela.setItems(dadosUsuarios);
        tabela.setPrefHeight(450);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
}
