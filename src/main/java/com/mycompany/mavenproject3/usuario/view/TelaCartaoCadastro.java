package com.mycompany.mavenproject3.usuario.view;

import com.mycompany.mavenproject3.usuario.controller.AreaUsuarioController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.mycompany.mavenproject3.view.UIUtils;

public class TelaCartaoCadastro {

    public static void exibir(AreaUsuarioController controller, Stage owner) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);
        stage.setTitle("Cadastrar Cartão");

        PasswordField campoNumeroOculto = new PasswordField();
        TextField campoNumeroVisivel = new TextField();

        TextFormatter<String> numberFormatter = new TextFormatter<>(change ->
                change.getControlNewText().matches("\\d{0,16}") ? change : null);
        campoNumeroOculto.setTextFormatter(numberFormatter);

        campoNumeroVisivel.setManaged(false);
        campoNumeroVisivel.setVisible(false);
        campoNumeroVisivel.textProperty().bindBidirectional(campoNumeroOculto.textProperty());

        Button botaoMostrar = new Button("👁");
        UIUtils.estilizarBotao(botaoMostrar);
        botaoMostrar.setPrefWidth(40);

        botaoMostrar.setOnAction(e -> {
            boolean mostrando = campoNumeroVisivel.isVisible();
            campoNumeroVisivel.setVisible(!mostrando);
            campoNumeroVisivel.setManaged(!mostrando);
            campoNumeroOculto.setVisible(mostrando);
            campoNumeroOculto.setManaged(mostrando);
        });

        TextField campoValidade = new TextField();
        campoValidade.setPromptText("MM/AA");
        campoValidade.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("\\d{0,2}(/\\d{0,2})?") ? change : null));

        TextField campoCVV = new TextField();
        campoCVV.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("\\d{0,3}") ? change : null));

        TextField campoNome = new TextField();

        GridPane painel = new GridPane();
        painel.setHgap(10);
        painel.setVgap(15);
        painel.setPadding(new Insets(30));
        painel.setStyle("-fx-background-color: linear-gradient(to bottom right, #0d0d0d, #1a1a1a);");

        Label titulo = new Label("💳 Cadastro de Cartão");
        titulo.setFont(Font.font("Georgia", 22));
        titulo.setTextFill(Color.web("#d4af37"));
        titulo.setEffect(new DropShadow(4, Color.web("#a6762d")));
        painel.add(titulo, 0, 0, 3, 1);

        painel.add(estilizarLabel("Número do cartão:"), 0, 1);
        painel.add(campoNumeroOculto, 1, 1);
        painel.add(campoNumeroVisivel, 1, 1);
        painel.add(botaoMostrar, 2, 1);

        painel.add(estilizarLabel("Validade (MM/AA):"), 0, 2);
        painel.add(campoValidade, 1, 2);

        painel.add(estilizarLabel("CVV:"), 0, 3);
        painel.add(campoCVV, 1, 3);

        painel.add(estilizarLabel("Nome no cartão:"), 0, 4);
        painel.add(campoNome, 1, 4);

        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");
        UIUtils.estilizarBotao(btnSalvar);
        UIUtils.estilizarBotao(btnCancelar);

        btnSalvar.setOnAction(e -> {
            String numero = campoNumeroOculto.getText();
            String validade = campoValidade.getText();
            String cvv = campoCVV.getText();

            if (!numero.matches("\\d{13,16}")) {
                alerta("Número de cartão inválido. Deve conter entre 13 e 16 dígitos.");
                return;
            }
            if (!validade.matches("(0[1-9]|1[0-2])/\\d{2}")) {
                alerta("Validade inválida. Use o formato MM/AA.");
                return;
            }
            if (!cvv.matches("\\d{3}")) {
                alerta("CVV inválido. Deve conter exatamente 3 dígitos.");
                return;
            }

            controller.marcarCartaoComoVerificado();
            alertaInfo("Cartão salvo com sucesso! Plano liberado.");
            stage.close();
        });

        btnCancelar.setOnAction(e -> stage.close());

        HBox botoes = new HBox(15, btnSalvar, btnCancelar);
        botoes.setAlignment(Pos.CENTER);
        painel.add(botoes, 0, 5, 3, 1);

        Scene scene = new Scene(painel);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private static void alerta(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }

    private static void alertaInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }

    private static Label estilizarLabel(String texto) {
        Label label = new Label(texto);
        label.setFont(Font.font("Georgia", 14));
        label.setTextFill(Color.web("#ffd700"));
        return label;
    }

}
