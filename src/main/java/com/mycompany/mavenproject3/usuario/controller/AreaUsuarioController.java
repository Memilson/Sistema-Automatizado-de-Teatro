package com.mycompany.mavenproject3.usuario.controller;

import com.mycompany.mavenproject3.core.SessaoUsuario;
import com.mycompany.mavenproject3.usuario.controller.UsuarioController;
import com.mycompany.mavenproject3.usuario.dto.UsuarioDashboardDTO;
import org.json.JSONArray;
import org.json.JSONObject;

public class AreaUsuarioController {

    private final UsuarioController controller;
    private boolean cartaoVerificado = false;

    public AreaUsuarioController(UsuarioController controller) {
        this.controller = controller;
    }

    public String getUserIdLogado() {
        return SessaoUsuario.getUserId();
    }

    public UsuarioDashboardDTO carregarDashboard(String userId) {
        return controller.carregarDashboardUsuario(userId);
    }

    public JSONArray buscarPlanos() throws Exception {
        String json = controller.getSupabase().get("/rest/v1/assinaturas?select=id,nome", true);
        return new JSONArray(json);
    }

    public boolean atualizarPlano(String userId, String idPlano) {
        JSONObject payload = new JSONObject();
        payload.put("uid", userId);
        payload.put("nova_assinatura", idPlano);

        return controller.getAssinaturaClient().atualizarAssinaturaViaRPC(userId, idPlano);
    }

    public boolean isCartaoVerificado() {
        return cartaoVerificado;
    }

    public void marcarCartaoComoVerificado() {
        this.cartaoVerificado = true;
    }

    public String[] extrairNomesPlanos(JSONArray planos) {
        String[] nomes = new String[planos.length()];
        for (int i = 0; i < planos.length(); i++) {
            nomes[i] = planos.getJSONObject(i).getString("nome");
        }
        return nomes;
    }
    public String encontrarIdPlano(JSONArray planos, String nomePlanoSelecionado) {
        for (int i = 0; i < planos.length(); i++) {
            JSONObject plano = planos.getJSONObject(i);
            if (plano.getString("nome").equals(nomePlanoSelecionado)) {
                return plano.getString("id");
            }
        }
        return null;
    }
}
