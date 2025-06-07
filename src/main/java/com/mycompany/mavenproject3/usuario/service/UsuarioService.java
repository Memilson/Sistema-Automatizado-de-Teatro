package com.mycompany.mavenproject3.usuario.service;

import com.mycompany.mavenproject3.supabase.SupabaseService;
import org.json.JSONObject;

public class UsuarioService {

    public static boolean atualizarPlanoViaRPC(String userId, String novoPlanoId) {
        try {
            System.out.println("🔁 Iniciando atualização via RPC...");
            JSONObject json = new JSONObject();
            json.put("uid", userId);
            json.put("nova_assinatura", novoPlanoId);

            System.out.println("📦 Enviando corpo: " + json.toString(2));

            String resposta = SupabaseService.post(
                    "/rest/v1/rpc/atualizar_assinatura",
                    json.toString(),
                    true
            );

            System.out.println("📤 RPC resposta: " + resposta);
            return resposta == null || !resposta.toLowerCase().contains("error");

        } catch (Exception e) {
            System.err.println("❌ Erro ao atualizar plano via RPC: " + e.getMessage());
            return false;
        }
    }
}
