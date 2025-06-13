package com.mycompany.mavenproject3.usuario.controller;

import com.mycompany.mavenproject3.login.view.TelaLogin;
import com.mycompany.mavenproject3.usuario.dto.UsuarioDashboardDTO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.json.JSONArray;

public class UsuarioViewController {

    private final AreaUsuarioController controller;

    public UsuarioViewController(AreaUsuarioController controller) {
        this.controller = controller;
    }

    public UsuarioDashboardDTO carregarOuEncerrar(Stage stage) {
        String userId = controller.getUserIdLogado();
        if (userId == null) {
            new Alert(Alert.AlertType.ERROR, "Você precisa estar logado.").showAndWait();
            new TelaLogin().start(new Stage());
            stage.close();
            return null;
        }

        UsuarioDashboardDTO dto = controller.carregarDashboard(userId);
        if (dto == null) {
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar dados.").showAndWait();
            stage.close();
        }

        return dto;
    }

    public void cadastrarCartao(Stage stage) {
        TextField campoNumero = new TextField();
        TextField campoValidade = new TextField();
        TextField campoCVV = new TextField();
        TextField campoNome = new TextField();

        GridPane painel = new GridPane();
        painel.setHgap(10);
        painel.setVgap(10);
        painel.setPadding(new Insets(10));

        painel.addRow(0, new Label("Número do cartão:"), campoNumero);
        painel.addRow(1, new Label("Validade (MM/AA):"), campoValidade);
        painel.addRow(2, new Label("CVV:"), campoCVV);
        painel.addRow(3, new Label("Nome no cartão:"), campoNome);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Cadastrar Cartão");
        dialog.getDialogPane().setContent(painel);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                controller.marcarCartaoComoVerificado();
                new Alert(Alert.AlertType.INFORMATION, "Cartão salvo com sucesso! Plano liberado.").showAndWait();
            }
        });
    }

    public void mudarPlano(Stage stage) {
        if (!controller.isCartaoVerificado()) {
            new Alert(Alert.AlertType.WARNING, "Você precisa cadastrar um cartão antes de mudar o plano.").showAndWait();
            return;
        }

        try {
            JSONArray planos = controller.buscarPlanos();

            if (planos.length() == 0) {
                new Alert(Alert.AlertType.INFORMATION, "Nenhum plano disponível.").showAndWait();
                return;
            }

            String[] nomes = controller.extrairNomesPlanos(planos);
            ChoiceDialog<String> dialog = new ChoiceDialog<>(nomes[0], nomes);
            dialog.setTitle("Mudar Plano");
            dialog.setHeaderText("Selecione o novo plano");

            dialog.showAndWait().ifPresent(nomeSelecionado -> {
                String idSelecionado = controller.encontrarIdPlano(planos, nomeSelecionado);
                boolean sucesso = controller.atualizarPlano(controller.getUserIdLogado(), idSelecionado);
                new Alert(Alert.AlertType.INFORMATION, sucesso ? "Plano atualizado com sucesso!" : "Erro ao atualizar plano.").showAndWait();
            });

        } catch (Exception ex) {
            ex.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar os planos.").showAndWait();
        }
    }
}
