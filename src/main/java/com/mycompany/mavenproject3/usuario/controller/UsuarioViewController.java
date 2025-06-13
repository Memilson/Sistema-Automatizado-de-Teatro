package com.mycompany.mavenproject3.usuario.controller;

import com.mycompany.mavenproject3.login.view.TelaLogin;
import com.mycompany.mavenproject3.usuario.dto.UsuarioDashboardDTO;
import javafx.geometry.Insets;
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

        return dto;}
    public void mudarPlano(Stage stage) {
        if (!controller.isCartaoVerificado()) {
            new Alert(Alert.AlertType.WARNING, "Você precisa cadastrar um cartão antes de mudar o plano.").showAndWait();
            return;}
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
