package com.mycompany.mavenproject3.usuario.service;

import com.mycompany.mavenproject3.supabase.SupabaseService;

public class SupabaseAdminClient {

    private final SupabaseService supabase;

    public SupabaseAdminClient(SupabaseService supabase) {
        this.supabase = supabase;
    }

    public boolean alternarAdmin(String userId) {
        try {
            String payload = "{\"uid\": \"" + userId + "\"}";
            supabase.post("/rest/v1/rpc/alternar_admin", payload, true);
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao alternar admin: " + e.getMessage());
            return false;
        }
    }
}
