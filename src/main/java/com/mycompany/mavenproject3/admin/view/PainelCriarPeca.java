package com.mycompany.mavenproject3.admin.view;
import com.mycompany.mavenproject3.admin.controller.PecaController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
public class PainelCriarPeca extends VBox {
    public PainelCriarPeca() {
        setSpacing(20);
        setPadding(new Insets(25));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: linear-gradient(to bottom right, #0d0d0d, #1a1a1a);");
        Text titulo = new Text("🎭 Criar Nova Peça");
        titulo.setFont(Font.font("Georgia", 24));
        titulo.setFill(Color.web("#d4af37"));
        titulo.setEffect(new javafx.scene.effect.DropShadow(3, Color.web("#a6762d")));
        Label labelTitulo = new Label("Título da Peça:");
        estilizarLabel(labelTitulo);
        TextField campoTitulo = new TextField();
        campoTitulo.setPromptText("Ex: Hamlet");
        Label labelHorario = new Label("Horário da Sessão:");
        estilizarLabel(labelHorario);
        TextField campoHorario = new TextField();
        campoHorario.setPromptText("Ex: 20:00");
        Button botaoSalvar = new Button("Salvar Peça");
        estilizarBotao(botaoSalvar);
        botaoSalvar.setOnAction(e -> {
            String tituloTexto = campoTitulo.getText();
            String horarioTexto = campoHorario.getText();
            PecaController.salvarPeca(tituloTexto, horarioTexto, null);});
        VBox formulario = new VBox(10, labelTitulo, campoTitulo, labelHorario, campoHorario, botaoSalvar);
        formulario.setAlignment(Pos.CENTER);
        formulario.setMaxWidth(300);
        getChildren().addAll(titulo, formulario);}
    private void estilizarLabel(Label label) {
        label.setFont(Font.font("Georgia", 14));
        label.setTextFill(Color.GOLD);}
    private void estilizarBotao(Button btn) {
        btn.setFont(Font.font("Georgia", 14));
        btn.setStyle("-fx-background-color: linear-gradient(to bottom, #ffcc00, #b8860b);" +
                " -fx-text-fill: black; -fx-background-radius: 10px;");
        btn.setPrefWidth(200);
        btn.setPrefHeight(40);}}
