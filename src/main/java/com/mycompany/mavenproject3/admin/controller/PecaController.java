package com.mycompany.mavenproject3.admin.controller;

import com.mycompany.mavenproject3.supabase.SupabaseService;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.json.JSONObject;

import java.util.UUID;

public class PecaController {

    public static void salvarPeca(String titulo, String horario, Object parent) {
        if (titulo == null || titulo.isEmpty() || horario == null || horario.isEmpty()) {
            mostrarAlerta(AlertType.WARNING, "Preencha todos os campos!");
            return;
        }

        try {
            UUID pecaId = UUID.randomUUID();
            JSONObject novaPeca = new JSONObject();
            novaPeca.put("id", pecaId.toString());
            novaPeca.put("titulo", titulo);

            SupabaseService.post("/rest/v1/pecas", novaPeca.toString(), true);

            JSONObject novaSessao = new JSONObject();
            novaSessao.put("id", UUID.randomUUID().toString());
            novaSessao.put("horario", horario);
            novaSessao.put("peca_id", pecaId.toString());

            SupabaseService.post("/rest/v1/sessoes", novaSessao.toString(), true);

            mostrarAlerta(AlertType.INFORMATION, "Peça e sessão criadas com sucesso!");
        } catch (Exception ex) {
            ex.printStackTrace();
            mostrarAlerta(AlertType.ERROR, "Erro ao criar peça/sessão.");
        }
    }

    private static void mostrarAlerta(AlertType tipo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle("DramaCore Theatre");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
