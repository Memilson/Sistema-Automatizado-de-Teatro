package com.mycompany.mavenproject3.compra.loader;

import com.mycompany.mavenproject3.compra.view.TelaCompraFX;
import com.mycompany.mavenproject3.supabase.SupabaseService;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.mycompany.mavenproject3.compra.helper.SelecaoVisualHelper.atualizarSelecaoVisualFX;

public class AssentoLoader {

    private static final int LIMITE_POLTRONAS = 5;

    public static void carregarAssentosFX(TelaCompraFX tela) {
        // Limpa tudo
        limparEstadoAnterior(tela);

        // Recupera peça/sessão selecionada
        String peca = tela.getComboPeca().getValue();
        String horario = tela.getComboSessao().getValue();
        if (peca == null || horario == null) return;

        String sessaoId = tela.getMapaSessoes().get(horario);
        String pecaId = tela.getMapaPecas().get(peca);
        if (sessaoId == null || pecaId == null) return;

        try {
            // Carrega reservas (ocupadas)
            String reservasJson = SupabaseService.get(
                    "/rest/v1/venda?and=(sessao_id.eq." + sessaoId + ",peca_id.eq." + pecaId + ")&select=poltrona_modelo_id",
                    true
            );
            JSONArray reservas = new JSONArray(reservasJson);
            for (int i = 0; i < reservas.length(); i++) {
                tela.getOcupadas().add(reservas.getJSONObject(i).getString("poltrona_modelo_id"));
            }

            // Carrega todas as poltronas
            String poltronasJson = SupabaseService.get(
                    "/rest/v1/poltrona_modelo?select=id,fileira,numero,tipo_area,preco_base",
                    true
            );
            JSONArray poltronas = new JSONArray(poltronasJson);

            Map<String, Integer> colSetor = new HashMap<>();
            Map<String, Integer> rowSetor = new HashMap<>();

            for (int i = 0; i < poltronas.length(); i++) {
                JSONObject poltrona = poltronas.getJSONObject(i);
                String id = poltrona.getString("id");
                String tipo = poltrona.getString("tipo_area");
                int numero = poltrona.getInt("numero");
                String preco = poltrona.get("preco_base").toString();

                String nomeVisivel = tipo.toUpperCase() + " - " + numero;

                tela.getMapaPoltronas().put(nomeVisivel, id);
                tela.getMapaPrecos().put(id, preco);

                Button botao = new Button(nomeVisivel);
                botao.setFont(Font.font("Georgia", 12));
                botao.setPrefSize(95, 38);

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
                        if (tela.getSelecionadas().contains(id)) {
                            tela.getSelecionadas().remove(id);
                        } else {
                            if (tela.getSelecionadas().size() >= LIMITE_POLTRONAS) {
                                new Alert(Alert.AlertType.WARNING, "Limite de 5 poltronas por compra.").showAndWait();
                                return;
                            }
                            tela.getSelecionadas().add(id);
                        }
                        atualizarSelecaoVisualFX(tela);
                    });
                }

                GridPane destino = definirDestino(tipo, numero, tela);

                String key = tipo + destino.hashCode();
                int col = colSetor.getOrDefault(key, 0);
                int row = rowSetor.getOrDefault(key, 0);

                destino.add(botao, col, row);
                col++;
                if (col > 9) {
                    col = 0;
                    row++;
                }

                colSetor.put(key, col);
                rowSetor.put(key, row);
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar poltronas ou reservas.").showAndWait();
            e.printStackTrace();
        }
    }

    private static void limparEstadoAnterior(TelaCompraFX tela) {
        tela.getPlateiaA().getChildren().clear();
        tela.getPlateiaB().getChildren().clear();
        tela.getFrisasEsquerda().getChildren().clear();
        tela.getFrisasDireita().getChildren().clear();
        tela.getCamarotes().getChildren().clear();
        tela.getBalcaoNobre().getChildren().clear();
        tela.getOcupadas().clear();
        tela.getMapaPoltronas().clear();
        tela.getMapaPrecos().clear();
        tela.getSelecionadas().clear();
        tela.setPoltronaSelecionadaId(null);
        tela.setPoltronaSelecionadaNome(null);
        tela.setPreco("--");
    }

    private static GridPane definirDestino(String tipo, int numero, TelaCompraFX tela) {
        return switch (tipo) {
            case "Plateia A" -> tela.getPlateiaA();
            case "Plateia B" -> tela.getPlateiaB();
            case "Frisa" -> (numero % 2 != 0) ? tela.getFrisasEsquerda() : tela.getFrisasDireita();
            case "Camarote" -> tela.getCamarotes();
            case "Balcão" -> tela.getBalcaoNobre();
            default -> tela.getPainelAssentos();
        };
    }
}
