package com.mycompany.mavenproject3.compra.helper;

import com.mycompany.mavenproject3.compra.controller.CompraController;
import com.mycompany.mavenproject3.compra.view.TelaCompraFX;
import com.mycompany.mavenproject3.supabase.SupabaseService;
import com.mycompany.mavenproject3.usuario.model.Usuario;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.json.JSONArray;
import org.json.JSONObject;

public class CompraViewHelper {

    public static void carregarPecasFX(TelaCompraFX tela) {
        try {
            String resposta = SupabaseService.get("/rest/v1/pecas?select=id,titulo", true);
            JSONArray json = new JSONArray(resposta);
            ComboBox<String> combo = tela.getComboPeca();
            combo.getItems().clear();
            tela.getMapaPecas().clear();

            for (int i = 0; i < json.length(); i++) {
                JSONObject peca = json.getJSONObject(i);
                String titulo = peca.getString("titulo");
                String id = peca.getString("id");
                tela.getMapaPecas().put(titulo, id);
                combo.getItems().add(titulo);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar peças.").showAndWait();
        }
    }

    public static void carregarSessoesFX(TelaCompraFX tela) {
        ComboBox<String> combo = tela.getComboSessao();
        combo.getItems().clear();
        tela.getMapaSessoes().clear();

        String peca = tela.getComboPeca().getValue();
        if (peca == null) return;

        try {
            String pecaId = tela.getMapaPecas().get(peca);
            String resposta = SupabaseService.get("/rest/v1/sessoes?peca_id=eq." + pecaId + "&select=id,horario", true);
            JSONArray json = new JSONArray(resposta);

            for (int i = 0; i < json.length(); i++) {
                JSONObject s = json.getJSONObject(i);
                String horario = s.getString("horario");
                String id = s.getString("id");
                tela.getMapaSessoes().put(horario, id);
                combo.getItems().add(horario);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar sessões.").showAndWait();
        }
    }

    public static void carregarAssentosFX(TelaCompraFX tela) {
        GridPane painel = tela.getPainelAssentos();
        painel.getChildren().clear();
        tela.getOcupadas().clear();
        tela.getMapaPoltronas().clear();
        tela.getMapaPrecos().clear();
        tela.setPoltronaSelecionadaId(null);
        tela.setPoltronaSelecionadaNome(null);
        tela.setPreco("--");

        String peca = tela.getComboPeca().getValue();
        String horario = tela.getComboSessao().getValue();
        String sessaoId = tela.getMapaSessoes().get(horario);
        if (peca == null || sessaoId == null) return;

        try {
            String reservasJson = SupabaseService.get("/rest/v1/venda?sessao_id=eq." + sessaoId + "&select=poltrona_modelo_id", true);
            JSONArray reservas = new JSONArray(reservasJson);
            for (int i = 0; i < reservas.length(); i++) {
                tela.getOcupadas().add(reservas.getJSONObject(i).getString("poltrona_modelo_id"));
            }

            String poltronasJson = SupabaseService.get("/rest/v1/poltrona_modelo?select=id,fileira,numero,tipo_area,preco_base", true);
            JSONArray poltronas = new JSONArray(poltronasJson);

            int col = 0, row = 0;
            for (int i = 0; i < poltronas.length(); i++) {
                JSONObject poltrona = poltronas.getJSONObject(i);
                String id = poltrona.getString("id");
                String nome = poltrona.getString("fileira") + poltrona.getInt("numero");
                String tipo = poltrona.getString("tipo_area");
                String preco = poltrona.get("preco_base").toString();

                tela.getMapaPoltronas().put(nome, id);
                tela.getMapaPrecos().put(id, preco);

                Button botao = new Button(nome);
                botao.setFont(Font.font("Georgia", 12));
                botao.setPrefWidth(60);

                if (tela.getOcupadas().contains(id)) {
                    botao.setStyle("-fx-background-color: gray; -fx-text-fill: white;");
                    botao.setDisable(true);
                } else {
                    String cor = switch (tipo) {
                        case "Plateia A" -> "#5DADE2";
                        case "Plateia B" -> "#58D68D";
                        case "Frisa"     -> "#F4D03F";
                        case "Camarote"  -> "#AF7AC5";
                        case "Balcão"    -> "#F1948A";
                        default -> "#D5D8DC";
                    };
                    botao.setStyle("-fx-background-color: " + cor + ";");
                    botao.setOnAction(e -> {
                        tela.setPoltronaSelecionadaId(id);
                        tela.setPoltronaSelecionadaNome(nome);
                        tela.setPreco(preco);
                        atualizarSelecaoVisualFX(tela, nome);
                    });
                }

                painel.add(botao, col++, row);
                if (col > 9) { col = 0; row++; }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar poltronas ou reservas.").showAndWait();
            e.printStackTrace();
        }
    }

    public static void atualizarSelecaoVisualFX(TelaCompraFX tela, String nomeSelecionado) {
        for (javafx.scene.Node node : tela.getPainelAssentos().getChildren()) {
            if (node instanceof Button botao) {
                String nome = botao.getText();
                if (nome.equals(nomeSelecionado)) {
                    botao.setStyle(botao.getStyle() + "-fx-border-color: black; -fx-border-width: 2;");
                } else {
                    String id = tela.getMapaPoltronas().get(nome);
                    if (!tela.getOcupadas().contains(id)) {
                        botao.setStyle(botao.getStyle().replace("-fx-border-color: black; -fx-border-width: 2;", ""));
                    }
                }
            }
        }
    }

    public static void confirmarCompraFX(TelaCompraFX tela) {
        String peca = tela.getComboPeca().getValue();
        String horario = tela.getComboSessao().getValue();
        String sessaoId = tela.getMapaSessoes().get(horario);

        if (peca == null || horario == null || tela.getPoltronaSelecionadaId() == null) {
            new Alert(Alert.AlertType.WARNING, "Selecione todos os campos e uma poltrona.").showAndWait();
            return;
        }

        String pecaId = tela.getMapaPecas().get(peca);
        Usuario usuario = tela.getUsuarioLogado();
        String userId = usuario.getId();
        String preco = tela.getMapaPrecos().get(tela.getPoltronaSelecionadaId());

        boolean sucesso = CompraController.realizarCompra(
                pecaId, sessaoId, userId, tela.getPoltronaSelecionadaId(), preco
        );

        if (sucesso) {
            new Alert(Alert.AlertType.INFORMATION, "Compra realizada com sucesso!").showAndWait();
        } else {
            new Alert(Alert.AlertType.ERROR, "Erro ao realizar compra.").showAndWait();
        }
    }
}