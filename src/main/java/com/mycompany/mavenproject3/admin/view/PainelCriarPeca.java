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
        setStyle("-fx-background-color: linear-gradient(to bottom right, #1a1a1a, #000000);");

        Text titulo = new Text("🎭 Criar Nova Peça");
        titulo.setFont(Font.font("Georgia", 24));
        titulo.setFill(Color.GOLD);

        Label labelTitulo = new Label("Título da Peça:");
        TextField campoTitulo = new TextField();
        campoTitulo.setPromptText("Ex: Hamlet");

        Label labelHorario = new Label("Horário da Sessão:");
        TextField campoHorario = new TextField();
        campoHorario.setPromptText("Ex: 20:00");

        Button botaoSalvar = new Button("Salvar Peça");
        botaoSalvar.setOnAction(e -> {
            String tituloTexto = campoTitulo.getText();
            String horarioTexto = campoHorario.getText();
            PecaController.salvarPeca(tituloTexto, horarioTexto, null); // null, pois não há JPanel agora
        });

        VBox formulario = new VBox(10, labelTitulo, campoTitulo, labelHorario, campoHorario, botaoSalvar);
        formulario.setAlignment(Pos.CENTER);
        formulario.setMaxWidth(300);

        getChildren().addAll(titulo, formulario);
    }
}
