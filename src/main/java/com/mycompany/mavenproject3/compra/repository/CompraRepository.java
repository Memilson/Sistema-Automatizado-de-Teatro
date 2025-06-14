package com.mycompany.mavenproject3.compra.repository;
import com.mycompany.mavenproject3.supabase.SupabaseService;
import org.json.JSONObject;

import java.math.BigDecimal;
public class CompraRepository {
    public static boolean salvarVenda(String pecaId, String sessaoId, String usuarioId, String poltronaId, String preco) {
        try {
            JSONObject json = new JSONObject();
            json.put("peca_id", pecaId);
            json.put("sessao_id", sessaoId);
            json.put("usuario_id", usuarioId);
            json.put("poltrona_modelo_id", poltronaId);
            json.put("preco", new BigDecimal(preco));
            json.put("data_compra", java.time.LocalDateTime.now().toString());
            String assinaturaId = SupabaseService.buscarAssinaturaId(usuarioId);
            if (assinaturaId != null) {
                json.put("assinatura_id", assinaturaId);}
            String resposta = SupabaseService.post("/rest/v1/venda", json.toString(), true);
            return resposta != null && !resposta.contains("error");
        } catch (Exception e) {
            System.err.println("Erro ao salvar venda: " + e.getMessage());

            return false;}
    }
    public static double buscarDescontoAssinatura(String assinaturaId) {
        try {
            String filtro = "?id=eq." + assinaturaId + "&select=desconto_percentual";
            String resposta = SupabaseService.get("/rest/v1/assinaturas" + filtro, true);

            if (resposta != null && resposta.startsWith("[")) {
                org.json.JSONArray array = new org.json.JSONArray(resposta);
                if (!array.isEmpty()) {
                    JSONObject obj = array.getJSONObject(0);
                    return obj.optDouble("desconto_percentual", 0.0);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar desconto da assinatura: " + e.getMessage());
        }
        return 0.0;
    }
}

