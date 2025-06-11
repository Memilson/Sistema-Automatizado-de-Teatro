package com.mycompany.mavenproject3.dados.controller;

import com.mycompany.mavenproject3.supabase.SupabaseService;
import org.json.JSONArray;

public class ValidadorUsuario {

    public static boolean cpfJaExiste(String cpf) {
        try {
            String filtro = "?cpf=eq." + cpf;
            String json = SupabaseService.get("/rest/v1/usuarios" + filtro, true);
            return json != null && new JSONArray(json).length() > 0;
        } catch (Exception e) {
            System.err.println("Erro ao verificar CPF duplicado: " + e.getMessage());
            return true;
        }
    }

    public static boolean cpfJaExisteExceto(String cpf, String usuarioId) {
        try {
            String filtro = "?cpf=eq." + cpf + "&id=neq." + usuarioId;
            String json = SupabaseService.get("/rest/v1/usuarios" + filtro, true);
            return json != null && new JSONArray(json).length() > 0;
        } catch (Exception e) {
            return true; // assume que deu ruim e bloqueia por segurança
        }
    }
}
