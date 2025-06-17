package com.mycompany.mavenproject3.usuario.service;

import com.mycompany.mavenproject3.supabase.SupabaseService;

public class SupabaseAdminClient {

    public SupabaseAdminClient() {
    }

    public boolean alternarAdmin(String userId) {
        try {
            String payload = "{\"uid\": \"" + userId + "\"}";
            SupabaseService.post("/rest/v1/rpc/alternar_admin", payload, true);
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao alternar admin: " + e.getMessage());
            return false;
        }
    }
}
