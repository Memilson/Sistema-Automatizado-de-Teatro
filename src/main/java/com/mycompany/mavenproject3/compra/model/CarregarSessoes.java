package com.mycompany.mavenproject3.compra.model;

import com.mycompany.mavenproject3.compra.view.TelaCompraFX;
import com.mycompany.mavenproject3.supabase.SupabaseService;
import javafx.scene.control.Alert;
import org.json.JSONArray;
import org.json.JSONObject;

public class CarregarSessoes {
    public static void carregarSessoesFX(TelaCompraFX tela) {
        try {
            String pecaSelecionada = tela.getComboPeca().getValue();
            if (pecaSelecionada == null) return;

            String pecaId = tela.getMapaPecas().get(pecaSelecionada);
            String resposta = SupabaseService.get("/rest/v1/sessoes?peca_id=eq." + pecaId + "&select=id,horario", true);
            JSONArray json = new JSONArray(resposta);

            tela.getComboSessao().getItems().clear();
            tela.getMapaSessoes().clear();

            for (int i = 0; i < json.length(); i++) {
                JSONObject sessao = json.getJSONObject(i);
                String id = sessao.getString("id");
                String horario = sessao.getString("horario");
                tela.getMapaSessoes().put(horario, id);
                tela.getComboSessao().getItems().add(horario);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar sessões.").showAndWait();
        }
    }
    public static void carregarPecasFX(TelaCompraFX tela) {
        try {
            String resposta = SupabaseService.get("/rest/v1/pecas?select=id,titulo", true);
            JSONArray json = new JSONArray(resposta);
            tela.getComboPeca().getItems().clear();
            tela.getMapaPecas().clear();

            for (int i = 0; i < json.length(); i++) {
                JSONObject peca = json.getJSONObject(i);
                String id = peca.getString("id");
                String titulo = peca.getString("titulo");
                tela.getMapaPecas().put(titulo, id);
                tela.getComboPeca().getItems().add(titulo);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar peças.").showAndWait();
        }
    }
}

