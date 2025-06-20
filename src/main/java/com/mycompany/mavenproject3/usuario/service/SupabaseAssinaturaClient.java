package com.mycompany.mavenproject3.usuario.service;

import com.mycompany.mavenproject3.supabase.SupabaseService;
import org.json.JSONObject;

public class SupabaseAssinaturaClient {

    private final SupabaseService supabase;

    public SupabaseAssinaturaClient(SupabaseService supabase) {
        this.supabase = supabase;
    }

    public boolean atualizarAssinaturaViaRPC(String userId, String novaAssinaturaId) {
        try {
            JSONObject json = new JSONObject();
            json.put("uid", userId);
            json.put("nova_assinatura", novaAssinaturaId);

            String resposta = supabase.post(
                    "/rest/v1/rpc/atualizar_assinatura",
                    json.toString(),
                    true
            );

            return resposta == null || !resposta.toLowerCase().contains("error");
        } catch (Exception e) {
            System.err.println("Erro ao atualizar assinatura via RPC: " + e.getMessage());
            return false;
        }
    }
}
